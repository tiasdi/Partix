package me.x_tias.partix.mini.game;

import me.x_tias.partix.mini.factories.Hub;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallFactory;
import me.x_tias.partix.plugin.ball.BallType;
import me.x_tias.partix.plugin.cooldown.Cooldown;
import me.x_tias.partix.plugin.gui.GUI;
import me.x_tias.partix.plugin.gui.ItemButton;
import me.x_tias.partix.plugin.settings.GameEffectType;
import me.x_tias.partix.plugin.settings.GameType;
import me.x_tias.partix.plugin.settings.Settings;
import me.x_tias.partix.plugin.settings.WinType;
import me.x_tias.partix.plugin.team.BaseTeam;
import me.x_tias.partix.plugin.team.TeamDesert;
import me.x_tias.partix.plugin.team.TeamGUI;
import me.x_tias.partix.plugin.team.TeamPlains;
import me.x_tias.partix.server.specific.Game;
import me.x_tias.partix.util.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GoalGame extends Game {

    private Location center;
    private BoundingBox homeNet;
    private BoundingBox awayNet;
    public BaseTeam home;
    public BaseTeam away;
    private Ball ball;
    private State state;

    private int gameSeconds;
    private int countSeconds;
    public int homeScore;
    public int awayScore;
    public Settings settings;
    public int period;
    public int totalPeriods;

    private List<Player> homeTeam;
    private List<Player> awayTeam;

    private Location homeSpawn;
    private Location awaySpawn;

    private static final int WALL_HEIGHT = 21;
    private static final int WALL_X_DISTANCE = 30;
    private static final int WALL_Z_DISTANCE = 26;



    public void setup(Settings settings, Location location, double xDistance, double yDistance, double xLength, double zWidth, double yHeight) {
        center = location.clone().toCenterLocation();
        this.settings = settings;
        Location m = center.clone();
        homeNet = new BoundingBox(m.getX()+(xDistance*1)-xLength,m.getY()+yDistance,m.getZ()+(zWidth*1),m.getX()+(xDistance*1)+xLength,m.getY()+yHeight+yDistance,m.getZ()+(zWidth*-1));
        awayNet = new BoundingBox(m.getX()+(xDistance*-1)-(xLength*-1),m.getY()+yDistance,m.getZ()+(zWidth*1),m.getX()+(xDistance*-1)+(xLength*-1),m.getY()+yHeight+yDistance,m.getZ()+(zWidth*-1));
        homeSpawn = homeNet.getCenter().toLocation(center.getWorld()).subtract(3.5,-1.0,0.0);
        awaySpawn = awayNet.getCenter().toLocation(center.getWorld()).add(3.5,-1.0,0.0);
        home = new TeamPlains();
        away = new TeamDesert();
        homeTeam = new ArrayList<>();
        awayTeam = new ArrayList<>();
        this.state = State.PREGAME;
        reset();
        updateArmor();
    }


    public void reset() {
        if (settings.winType.timed) {
            setTime(settings.winType.amount*60, 0);
        }
        period = 1;
        totalPeriods = settings.periods;
        countSeconds = 0;
        homeScore = 0;
        awayScore = 0;
        removeBalls();
        resetStats();
    }

    public void backdrops(Material[] home, Material[] away) {
        GameUtil.createWall(center, new Material[]{Material.BLACK_CONCRETE}, new Material[]{Material.BLACK_CONCRETE}, WALL_X_DISTANCE, WALL_HEIGHT, WALL_Z_DISTANCE);
        GameUtil.createWall(center, home, away, WALL_X_DISTANCE, WALL_HEIGHT, WALL_Z_DISTANCE);
    }

    public abstract void resetStats();

    public void setTime(int seconds, int ticks) {
        gameSeconds = (seconds * 20) + ticks;
    }


    public void startCountdown(State s, int seconds) {
        if (s.equals(State.FACEOFF) && !state.equals(State.FACEOFF)) {
            setFaceoff();
        } else if (s.equals(State.PREGAME)) {
            removeBalls();
            setPregame();
        }
        countSeconds = seconds * 20;
        state = s;
    }

    public int getTime() {
        return (int)Math.ceil((double)gameSeconds/20);
    }

    public String getGameTime() {
        int t = (int)((double)gameSeconds/20); // 1
        int minutes = t / 60;
        int seconds = t % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public Location getHomeSpawn() {
        return homeSpawn.clone();
    }

    public Location getAwaySpawn() {

        return awaySpawn.clone();
    }

    public BoundingBox getHomeNet() {
        return homeNet;
    }

    public BoundingBox getAwayNet() {
        return awayNet;
    }

    public Location getBench() {
        return center.clone().add(0.0,0.0,-20.0);
    }

    public int getTimeTicks() {
        return gameSeconds;
    }

    public int getCountSeconds() {
        return (int)Math.ceil((double)countSeconds/20);
    }

    public String getPeriodString() {
        boolean ot = state.equals(State.OVERTIME);
        if (totalPeriods == 1) {
            return ot ? "Overtime "+(period-1) : "Regulation";
        } else if (totalPeriods == 2) {
            return ot ? "Overtime "+(period-1) : period == 1 ? "1st Half" : "2nd Half";
        } else if (totalPeriods == 3) {
            return ot ? "Overtime "+(period-1) : period == 1 ? "1st Period" : period == 2 ? "2nd Period" : "3rd Period";
        } else if (totalPeriods == 4) {
            return ot ? "Overtime "+(period-1) : period == 1 ? "1st Quarter" : period == 2 ? "2nd Quarter" : period == 3 ? "3rd Quarter" : "4th Quarter";
        } else {
            return ot ? "Overtime "+(period-1) : "Regulation ("+period+"/"+totalPeriods+")" ;
        }
    }

    public String getNth(int i) {
        if (i % 10 == 1) {
            return i+"st";
        }
        if (i % 10 == 2) {
            return i+"nd";
        }
        if (i % 10 == 3) {
            return i+"rd";
        }
        return i+"th";
    }

    public String getShortPeriodString() {
        if (state.equals(State.PREGAME) || state.equals(State.FINAL)) {
            return "  §7- ";
        } else if (state.equals(State.OVERTIME) || period > totalPeriods) {
            if (settings.suddenDeath) {
                return "§cSudden Death";
            } else {
                return "§cOT-" + (period-settings.periods);
            }
        } else if (totalPeriods == 1) {
            return getNth(period)+" Per §7("+period+"/"+totalPeriods+")";
        } else if (totalPeriods == 2) {
            return getNth(period)+" Hlf §7("+period+"/"+totalPeriods+")";
        } else if (totalPeriods == 3) {
            return getNth(period)+" Per §7("+period+"/"+totalPeriods+")";
        } else if (totalPeriods == 4) {
            return getNth(period)+" Qtr §7("+period+"/"+totalPeriods+")";
        } else {
            return getNth(period)+" Per §7("+period+"/"+totalPeriods+")";
        }
    }


    public State getState() {
        if (state == null) {
            state = State.PREGAME;
        }
        return state;
    }

    public Location getCenter() {
        return center.clone();
    }

    public Ball getBall() {
        return ball;
    }

    public Ball setBall(Ball b) {
        ball = b;
        return ball;
    }

    public List<Player> getHomePlayers() {
        List<Player> copy = new ArrayList<>(homeTeam);
        homeTeam = copy.stream().filter(p -> p.isOnline() && getPlayers().contains(p) && AthleteManager.get(p.getUniqueId()).getPlace().equals(this)).collect(Collectors.toList());
        return this.homeTeam;
    }

    public void addHomePlayer(Player player) {
        homeTeam.add(player);
    }

    public void removeHomePlayer(Player player) {
        homeTeam.remove(player);
    }

    public void removeAwayPlayer(Player player) {
        awayTeam.remove(player);
    }

    public List<Player> getAwayPlayers() {
        List<Player> copy = new ArrayList<>(awayTeam);
        this.awayTeam = copy.stream().filter(p -> p.isOnline() && getPlayers().contains(p) && AthleteManager.get(p.getUniqueId()).getPlace().equals(this)).collect(Collectors.toList());
        return this.awayTeam;
    }

    public void addAwayPlayer(Player player) {
        awayTeam.add(player);
    }

    public void addTime(int ticks) {
        gameSeconds = gameSeconds + ticks;
    }

    private int c;

    @Override
    public void onTick() {
        if (ball != null) {
            goalDetection();
            stoppageDetection();
        }
        if (state.equals(State.REGULATION) || state.equals(State.OVERTIME)) {
            runClock();
        } else {
            runCountdown();
        }

        c++;
        if (c > 9) {
            updateDisplay();
            c = 0;
        }
    }

    public void removeBalls() {
        if (ball != null) {
            ball.remove();
            ball = null;
        }
        BallFactory.removeBalls(center,40);
        ball = null;
    }

    public abstract void stoppageDetection();

    private void runClock() {
        if (settings.winType.timed) {
            if (gameSeconds % 20 == 0 && gameSeconds < 105 && gameSeconds > 15) {
                playSound(Sound.BLOCK_NOTE_BLOCK_BIT,SoundCategory.MASTER,1f,1f);
            }
            gameSeconds -= 1;
            if (gameSeconds < 0) {
                endPeriod();
            }
        }
    }

    private void runCountdown() {
        if (countSeconds > 0) {
            countSeconds -= 1;
            if (state.equals(State.FACEOFF)) {
                if (countSeconds % 20 == 0 && countSeconds > 5 && countSeconds < 105) {
                    removeBalls();
                    sendTitle(Component.text("Commencing in ",Colour.bold()).append(Component.text((int)Math.ceil((double)countSeconds/20),Colour.title())));
                }
            }
            if (countSeconds < 1) {
                endCountdown();
            }
        }
    }

    private void endCountdown() {
        removeBalls();
        if (state.equals(State.PREGAME)) {
            startCountdown(State.FACEOFF,settings.waitType.low);
            WinType winType = settings.winType;
            if (winType.timed) {
                setTime(winType.amount * 20, 0);
            }
        } else if (state.equals(State.FACEOFF)) {
            dropBall();
            if (period > totalPeriods) {
                state = State.OVERTIME;
            } else {
                state = State.REGULATION;
            }
        } else if (state.equals(State.FINAL)) {
            if (settings.gameType.equals(GameType.AUTOMATIC)) {
                kickAll();
                state = State.PREGAME;
                startCountdown(State.PREGAME,-1);
            } else {
                reset();
                startCountdown(State.PREGAME,-1);
            }
        }
    }

    public abstract void setPregame();

    public abstract void dropBall();

    private void endPeriod() {
        if (periodIsComplete(gameSeconds)) {
            removeBalls();
            boolean next = false;
            if (period >= totalPeriods) {
                if (homeScore > awayScore) {
                    gameOver(Team.HOME);
                } else if (awayScore > homeScore) {
                    gameOver(Team.AWAY);
                } else {
                    next = true;
                }
            } else {
                next = true;
            }
            if (next) {
                sendTitle(Component.text("End of "+getPeriodString(), Colour.deny()));
                period += 1;
                if (period > totalPeriods) {
                    state = State.OVERTIME;
                    setTime(90,0);
                } else {
                    state = State.REGULATION;
                    if (settings.winType.timed) {
                        setTime(settings.winType.amount * 60, 0);
                    }
                }
                startCountdown(State.FACEOFF, settings.waitType.med);
            }
        }
    }

    public abstract void setFaceoff();

    public abstract boolean periodIsComplete(int ticksRemaining);

    public abstract void gameOver(Team winner);

    private void goalDetection() {
        if (ball != null && (state.equals(State.REGULATION) || state.equals(State.OVERTIME))) {
            if (homeNet.contains(ball.getLocation().toVector())) {
                goal(Team.AWAY);
            } else if (awayNet.contains(ball.getLocation().toVector())) {
                goal(Team.HOME);
            }
        }
    }

    public abstract void updateDisplay();

    public abstract void goal(Team team);

    public abstract void joinTeam(Player player, Team team);

    public enum Team {
        HOME, AWAY, SPECTATOR
    }

    public enum State {
        PREGAME, FACEOFF, STOPPAGE, REGULATION, OVERTIME, FINAL,
    }

    public abstract BallType getBallType();

    @Override
    public void clickItem(Player player, ItemStack itemStack) {
        if (itemStack.getType().equals(Material.GRAY_DYE)) {
            if (BallFactory.getNearby(player.getLocation(),5).size() > 0) {
                player.sendMessage(Message.cantDoThisNow());
                return;
            }
            new GUI("Change Team", 3,
                    false, new ItemButton(10, Items.get(Component.text("Home Team").color(Colour.partix()), Material.BLACK_WOOL), p -> {
                        if (settings.teamLock) {
                            player.sendMessage(Message.disabled());
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                        } else {
                            joinTeam(p, Team.HOME);
                        }
                    }),
                    new ItemButton(12,Items.get(Component.text("Away Team").color(Colour.partix()), Material.WHITE_WOOL), p -> {
                        if (settings.teamLock) {
                            player.sendMessage(Message.disabled());
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                        } else {
                            joinTeam(p, Team.AWAY);
                        }
                    }),
                    new ItemButton(14,Items.get(Component.text("Spectators").color(Colour.partix()), Material.ENDER_EYE), p -> {
                        if (settings.teamLock) {
                            player.sendMessage(Message.disabled());
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                        } else {
                            joinTeam(p, Team.SPECTATOR);
                        }
                    }),
                    new ItemButton(16,Items.get(Component.text("Leave Game").color(Colour.deny()), Material.IRON_DOOR), p -> {
                        Hub.hub.join(AthleteManager.get(player.getUniqueId()));
                    })).openInventory(player);
        } else if (itemStack.getType().equals(Material.OAK_STAIRS)) {
            if (!(BallFactory.getNearby(player.getLocation(), 3.0).size() > 0) && !AthleteManager.get(player.getUniqueId()).isSpectating()) {
                Team team = getHomePlayers().contains(player) ? Team.HOME : getAwayPlayers().contains(player) ? Team.AWAY : Team.SPECTATOR;
                if (isInBench(player)) {
                    // leave bench
                    if (isTeamAvailable(team)) {
                        leaveBench(player);
                    } else {
                        player.sendMessage(Message.fullTeam());
                    }
                } else {
                    // go into bench
                    enterBench(player);
                }
            } else {
                player.sendMessage(Message.cantDoThisNow());
            }
        } else if (itemStack.getType().equals(Material.CHEST)) {
            if (settings.gameType.equals(GameType.MANUAL)) {
                new GUI("Game Settings", 6,
                        false, new ItemButton(11, Items.get(Component.text("Reset Game").color(Colour.partix()), Material.RED_CONCRETE_POWDER), p -> {
                            if (canEditGame(player)) {
                                reset();
                                startCountdown(State.PREGAME,-1);
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(12, Items.get(Component.text("Pregame").color(Colour.partix()), Material.BLUE_CONCRETE_POWDER), p -> {
                            if (canEditGame(player)) {
                                reset();
                                startCountdown(State.PREGAME,-1);
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(14, Items.get(Component.text("Start Play").color(Colour.partix()), Material.YELLOW_CONCRETE), p -> {
                            if (canEditGame(player)) {
                                startCountdown(State.FACEOFF,5);
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(15, Items.get(Component.text("Stop Play").color(Colour.partix()), Material.ORANGE_CONCRETE), p -> {
                            if (canEditGame(player)) {
                                startCountdown(State.STOPPAGE,-5);
                                removeBalls();
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(25, Items.get(Component.text("Change Home Team").color(Colour.partix()), Material.NETHERITE_CHESTPLATE), p -> {
                            if (canEditGame(player)) {
                                TeamGUI.get("Home", baseTeam -> {
                                    home = baseTeam;
                                    sendMessage(Message.settingChange("Home Team", Text.serialize(home.name)));
                                    updateArmor();
                                    updateDisplay();
                                }).openInventory(player);
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(26, Items.get(Component.text("Change Away Team").color(Colour.partix()), Material.IRON_CHESTPLATE), p -> {
                            if (canEditGame(player)) {
                                TeamGUI.get("Away", baseTeam -> {
                                    away = baseTeam;
                                    sendMessage(Message.settingChange("Away Team", Text.serialize(away.name)));
                                    updateArmor();
                                    updateDisplay();
                                }).openInventory(player);
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(27, Items.get(Component.text("Team Joining: Enabled").color(Colour.partix()), Material.OAK_DOOR), p -> {
                            if (canEditGame(player)) {
                                settings.teamLock = false;
                                sendMessage(Message.settingChange("Team joining", "unlocked"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(28, Items.get(Component.text("Team Joining: Disabled").color(Colour.partix()), Material.IRON_DOOR), p -> {
                            if (canEditGame(player)) {
                                settings.teamLock = true;
                                sendMessage(Message.settingChange("Team joining", "locked"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(30, Items.get(Component.text("OT: Sudden Death").color(Colour.partix()), Material.RED_DYE), p -> {
                            if (canEditGame(player)) {
                                settings.suddenDeath = true;
                                sendMessage(Message.settingChange("Overtime", "sudden death"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(31, Items.get(Component.text("OT: 90s Timed").color(Colour.partix()), Material.CLOCK), p -> {
                            if (canEditGame(player)) {
                                settings.suddenDeath = false;
                                sendMessage(Message.settingChange("Overtime", "timed"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(33, Items.get(Component.text("Effect: None").color(Colour.partix()), Material.BARRIER), p -> {
                            if (canEditGame(player)) {
                                settings.gameEffect = GameEffectType.NONE;
                                removePotionEffects(PotionEffectType.SPEED,PotionEffectType.JUMP);
                                sendMessage(Message.settingChange("Effect", "none"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(34, Items.get(Component.text("Effect: Speed").color(Colour.partix()), Material.MAGMA_CREAM), p -> {
                            if (canEditGame(player)) {
                                settings.gameEffect = GameEffectType.SPEED_2;
                                removePotionEffects(PotionEffectType.SPEED,PotionEffectType.JUMP);
                                addPotionEffects(settings.gameEffect.effect);
                                sendMessage(Message.settingChange("Effect", "speed"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(35, Items.get(Component.text("Effect: Jump Boost").color(Colour.partix()), Material.SLIME_BALL), p -> {
                            if (canEditGame(player)) {
                                settings.gameEffect = GameEffectType.JUMP_2;
                                removePotionEffects(PotionEffectType.SPEED,PotionEffectType.JUMP);
                                addPotionEffects(settings.gameEffect.effect);
                                sendMessage(Message.settingChange("Effect", "jump"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(36, Items.get(Component.text("Teams: 1v1").color(Colour.partix()), Material.PLAYER_HEAD), p -> {
                            if (canEditGame(player)) {
                                settings.playersPerTeam = 1;
                                sendMessage(Message.settingChange("Players per team", "1"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(37, Items.get(Component.text("Teams: 2v2").color(Colour.partix()), Material.PLAYER_HEAD), p -> {
                            if (canEditGame(player)) {
                                settings.playersPerTeam = 2;
                                sendMessage(Message.settingChange("Players per team", "2"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(38, Items.get(Component.text("Teams: 3v3").color(Colour.partix()), Material.PLAYER_HEAD), p -> {
                            if (canEditGame(player)) {
                                settings.playersPerTeam = 3;
                                sendMessage(Message.settingChange("Players per team", "3"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(39, Items.get(Component.text("Teams: 4v4").color(Colour.partix()), Material.PLAYER_HEAD), p -> {
                            if (canEditGame(player)) {
                                settings.playersPerTeam = 4;
                                sendMessage(Message.settingChange("Players per team", "4"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(41, Items.get(Component.text("Sections: 1 Match").color(Colour.partix()), Material.WHITE_CANDLE), p -> {
                            if (canEditGame(player)) {
                                settings.periods = 1;
                                totalPeriods = 1;
                                sendMessage(Message.settingChange("Sections", "1 Match"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(42, Items.get(Component.text("Sections: 2 Halves").color(Colour.partix()), Material.WHITE_CANDLE), p -> {
                            if (canEditGame(player)) {
                                settings.periods = 2;
                                totalPeriods = 2;
                                sendMessage(Message.settingChange("Sections", "2 Halves"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(43, Items.get(Component.text("Sections: 3 Periods").color(Colour.partix()), Material.WHITE_CANDLE), p -> {
                            if (canEditGame(player)) {
                                settings.periods = 3;
                                totalPeriods = 3;
                                sendMessage(Message.settingChange("Sections", "3 Periods"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(44, Items.get(Component.text("Sections: 4 Quarters").color(Colour.partix()), Material.WHITE_CANDLE), p -> {
                            if (canEditGame(player)) {
                                settings.periods = 4;
                                totalPeriods = 4;
                                sendMessage(Message.settingChange("Sections", "4 Quarters"));
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(45, Items.get(Component.text("Scoring: 2 Minutes").color(Colour.partix()), Material.LIME_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "2 Minute increments"));
                                settings.winType = WinType.TIME_2;
                                if (getTime() > 2 * 60) {
                                    setTime(2 * 60, 0);
                                }
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(46, Items.get(Component.text("Scoring: 3 Minutes").color(Colour.partix()), Material.LIME_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "3 Minute increments"));
                                settings.winType = WinType.TIME_3;
                                if (getTime() > 3 * 60) {
                                    setTime(3 * 60, 0);
                                }
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(47, Items.get(Component.text("Scoring: 5 Minutes").color(Colour.partix()), Material.LIME_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "5 Minute increments"));
                                settings.winType = WinType.TIME_5;
                                if (getTime() > 5 * 60) {
                                    setTime(5 * 60, 0);
                                }
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(48, Items.get(Component.text("Scoring: First to 1").color(Colour.partix()), Material.PINK_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "first to 1"));
                                settings.winType = WinType.GOALS_1;
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(49, Items.get(Component.text("Scoring: First to 3").color(Colour.partix()), Material.PINK_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "first to 3"));
                                settings.winType = WinType.GOALS_3;
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(50, Items.get(Component.text("Scoring: First to 5").color(Colour.partix()), Material.PINK_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "first to 5"));
                                settings.winType = WinType.GOALS_5;
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(51, Items.get(Component.text("Scoring: First to 10").color(Colour.partix()), Material.PINK_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "first to 10"));
                                settings.winType = WinType.GOALS_10;
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(52, Items.get(Component.text("Scoring: First to 15").color(Colour.partix()), Material.PINK_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "first to 15"));
                                settings.winType = WinType.GOALS_15;
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        }),
                        new ItemButton(53, Items.get(Component.text("Scoring: First to 21").color(Colour.partix()), Material.PINK_DYE), p -> {
                            if (canEditGame(player)) {
                                sendMessage(Message.settingChange("Scoring type", "first to 21"));
                                settings.winType = WinType.GOALS_21;
                            } else {
                                player.sendMessage(Message.onlyOwner());
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
                            }
                        })
                        ).openInventory(player);
            } else {
                player.sendMessage(Message.disabled());
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, SoundCategory.MASTER, 1f, 1f);
            }
        }

    }

    public void enterBench(Player player) {
        player.teleport(getBench());
        Cooldown.setRestricted(player.getUniqueId(),20);
    }

    public void leaveBench(Player player) {
        Location location = player.getLocation().clone();
        location.setZ(center.getZ()-12.5);
        player.teleport(location);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 2,true,false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 1,true, false));
        Cooldown.setRestricted(player.getUniqueId(),10);
    }

    public boolean isInBench(Player player) {
        return player.getLocation().getZ() < center.getZ()-14.5;
    }



    public boolean isTeamAvailable(Team team) {
        int playersOnFloor = (new ArrayList<>(team.equals(Team.HOME) ? getHomePlayers() : getAwayPlayers())).stream().filter(p -> p.getLocation().getZ() > center.getZ()-15).toList().size();
        return playersOnFloor < settings.playersPerTeam;
    }

    public void updateArmor() {
        homeTeam.forEach(player -> {
            player.getInventory().setChestplate(home.chest);
            player.getInventory().setLeggings(home.pants);
            player.getInventory().setBoots(home.boots);
        });
        awayTeam.forEach(player -> {
            player.getInventory().setChestplate(Items.armor(Material.LEATHER_CHESTPLATE,0xffffff,"Jersey", "Your teams away jersey"));
            player.getInventory().setLeggings(away.away);
            player.getInventory().setBoots(away.boots);
        });
    }

    public abstract boolean canEditGame(Player player);




}
