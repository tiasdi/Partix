package me.x_tias.partix.mini.basketball;

import me.x_tias.partix.database.BasketballDb;
import me.x_tias.partix.database.SeasonDb;
import me.x_tias.partix.mini.game.GoalGame;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallFactory;
import me.x_tias.partix.plugin.ball.BallType;
import me.x_tias.partix.plugin.ball.types.Basketball;
import me.x_tias.partix.plugin.settings.*;
import me.x_tias.partix.plugin.sidebar.Sidebar;
import me.x_tias.partix.plugin.team.BaseTeam;
import me.x_tias.partix.util.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.*;

public class BasketballGame extends GoalGame {

    private final List<UUID> joinedHome = new ArrayList<>();
    private final List<UUID> joinedAway = new ArrayList<>();
    private final HashMap<UUID, Integer> points = new HashMap<>();
    private final HashMap<UUID, Integer> threes = new HashMap<>();


    public BasketballGame(Settings settings, Location location, double xDistance, double yDistance, double xLength, double zWidth, double yHeight) {
        setup(settings, location, xDistance, yDistance, xLength, zWidth, yHeight);
    }

    @Override
    public void resetStats() {
        joinedHome.clear();
        joinedAway.clear();
        points.clear();
        threes.clear();
    }

    @Override
    public void setPregame() {
        World world = getCenter().getWorld();
        BallFactory.create(getHomeSpawn().clone().add(0.0,0.0,-3.0), getBallType(), this);
        BallFactory.create(getHomeSpawn().clone().add(0.0,0.0,-1.5), getBallType(), this);
        BallFactory.create(getHomeSpawn().clone().add(0.0,0.0,1.5), getBallType(), this);
        BallFactory.create(getHomeSpawn().clone().add(0.0,0.0,3.0), getBallType(), this);
        BallFactory.create(getAwaySpawn().clone().add(0.0,0.0,-3.0), getBallType(), this);
        BallFactory.create(getAwaySpawn().clone().add(0.0,0.0,-1.5), getBallType(), this);
        BallFactory.create(getAwaySpawn().clone().add(0.0,0.0,1.5), getBallType(), this);
        BallFactory.create(getAwaySpawn().clone().add(0.0,0.0,3.0), getBallType(), this);
        Block h = getHomeNet().clone().getCenter().toLocation(world).getBlock();
        h.getLocation().clone().getBlock().setType(Material.AIR);
        h.getLocation().clone().subtract(0.0,1.0,0.0).getBlock().setType(Material.AIR);
        Block a = getAwayNet().clone().getCenter().toLocation(world).getBlock();
        a.getLocation().clone().getBlock().setType(Material.AIR);
        a.getLocation().clone().subtract(0.0,1.0,0.0).getBlock().setType(Material.AIR);
    }

    @Override
    public void setFaceoff() {
        World world = getCenter().getWorld();
        removeBalls();
        Location h = getHomeNet().clone().getCenter().toLocation(world);
        h.getBlock().setType(Material.AIR);
        h.subtract(0.0,1.0,0.0).getBlock().setType(Material.BARRIER);
        Location a = getAwayNet().clone().getCenter().toLocation(world);
        a.getBlock().setType(Material.AIR);
        a.subtract(0.0,1.0,0.0).getBlock().setType(Material.BARRIER);
    }

    @Override
    public void dropBall() {
        Location spawn = getCenter().add(0.0,1.5+(Math.random()/1.5),0.0);
        Ball ball = setBall(BallFactory.create(spawn, BallType.BASKETBALL,this));
        ball.setVelocity(0.0,0.1+(Math.random()/3),new Random().nextBoolean() ? Math.max(0.05+((0.05 + Math.random())/25),0.05)/3 : Math.min(-0.05+((-0.5 - Math.random())/25),-0.05)/3);
    }

    @Override
    public boolean periodIsComplete(int ticksRemaining) {
        if (getBall() != null) {
            if (getBall().getCurrentDamager() == null) {
                if (getBall().getLocation().getY() > getCenter().getY()+2.0) {
                    addTime(1);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void gameOver(GoalGame.Team winner) {
        sendTitle(Component.text("The ").color(Colour.partix()).append(winner.equals(Team.HOME) ? home.name : away.name).append(Component.text(" Win!").color(Colour.bold())));
        UUID mvp = calculateMVP();
        if (settings.compType.equals(CompType.RANKED)) {
            if (winner.equals(Team.HOME)) {
                getHomePlayers().forEach(uuid -> BasketballDb.add(uuid.getUniqueId(), BasketballDb.Stat.WINS, 1));
                getAwayPlayers().forEach(uuid -> BasketballDb.add(uuid.getUniqueId(), BasketballDb.Stat.LOSSES, 1));
                getHomePlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.WINS, 1));
                getHomePlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.POINTS, 3 + (uuid.getUniqueId().equals(mvp) ? 1 : 0)));
                getAwayPlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.LOSSES, 1));
                getAwayPlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.POINTS, 1 + (uuid.getUniqueId().equals(mvp) ? 1 : 0)));

                getHomePlayers().forEach(player -> {
                    AthleteManager.get(player.getUniqueId()).giveCoins(10, true);
                });
                getAwayPlayers().forEach(player -> {
                    AthleteManager.get(player.getUniqueId()).giveCoins(5, true);
                });

            } else {
                getAwayPlayers().forEach(uuid -> BasketballDb.add(uuid.getUniqueId(), BasketballDb.Stat.WINS, 1));
                getHomePlayers().forEach(uuid -> BasketballDb.add(uuid.getUniqueId(), BasketballDb.Stat.LOSSES, 1));
                getAwayPlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.WINS, 1));
                getAwayPlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.POINTS, 3 + (uuid.getUniqueId().equals(mvp) ? 1 : 0)));
                getHomePlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.LOSSES, 1));
                getHomePlayers().forEach(uuid -> SeasonDb.add(uuid.getUniqueId(), SeasonDb.Stat.POINTS, 1 + (uuid.getUniqueId().equals(mvp) ? 1 : 0)));

                getHomePlayers().forEach(player -> {
                    AthleteManager.get(player.getUniqueId()).giveCoins(5, true);
                });
                getAwayPlayers().forEach(player -> {
                    AthleteManager.get(player.getUniqueId()).giveCoins(10, true);
                });

            }
            points.forEach((uuid, integer) -> BasketballDb.add(uuid, BasketballDb.Stat.POINTS, integer));
            threes.forEach((uuid, integer) -> BasketballDb.add(uuid, BasketballDb.Stat.THREES, integer));
            BasketballDb.add(mvp, BasketballDb.Stat.MVP,1);
        }
        Player player = Bukkit.getPlayer(mvp);
        if (player != null) {
            if (player.isOnline()) {
                sendMessage(Message.gameOver(player.getName(),homeScore,awayScore));
                player.playSound(player.getLocation(),Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1f, 1f);
            }
        } else {
            sendMessage(Message.gameOver("Unknown",homeScore,awayScore));
        }
        startCountdown(State.FINAL,settings.waitType.med);
    }

    private UUID calculateMVP() {
        HashMap<UUID, Double> mvp = new HashMap<>();
        List<UUID> first = homeScore > awayScore ? new ArrayList<>(getHomePlayers()).stream().map(Entity::getUniqueId).toList() : new ArrayList<>(getAwayPlayers()).stream().map(Entity::getUniqueId).toList();
        List<UUID> second = homeScore > awayScore ? new ArrayList<>(getAwayPlayers()).stream().map(Entity::getUniqueId).toList() : new ArrayList<>(getHomePlayers()).stream().map(Entity::getUniqueId).toList();
        double firstScore = Math.max(homeScore, awayScore);
        double secondScore = Math.min(homeScore, awayScore);
        for (UUID uuid : first) {
            double pct = (((double)Math.max(points.getOrDefault(uuid, 0), 1)) / firstScore) * 50;
            double pts = ((points.getOrDefault(uuid,0)*2) + threes.getOrDefault(uuid, 0));
            double win = Math.abs(firstScore - secondScore);
            double rnd = Math.random() / 10;
            mvp.putIfAbsent(uuid, pct + pts + win + rnd);
        }
        for (UUID uuid : second) {
            double pct = (((double)Math.max(points.getOrDefault(uuid, 0), 1)) / secondScore) * 50;
            double pts = ((points.getOrDefault(uuid,0)*2) + threes.getOrDefault(uuid, 0));
            double win = 0;
            double rnd = Math.random() / 10;
            mvp.putIfAbsent(uuid, pct + pts + win + rnd);
        }
        return Util.getHighest(mvp);
    }

    @Override
    public void updateDisplay() {
        String time;
        if (getState().equals(State.REGULATION) || getState().equals(State.OVERTIME) || getState().equals(State.FACEOFF)) {
            if (getState().equals(State.OVERTIME)) {
                if (settings.suddenDeath) {
                    time = "Time: §e0:00";
                } else {
                    time = "Time: §e" + getGameTime();
                }
            } else  if (settings.winType.timed) {
                time = "Time: §e"+getGameTime();
            } else {
                time = "First to: §e"+settings.winType.amount;
            }
            if (getState().equals(State.FACEOFF)) {
                time = time + " §7("+getCountSeconds()+"s)";
            }

        } else if (getState().equals(State.PREGAME)) {
            time = "§bPregame" + (getCountSeconds() > 0 ? ": §f"+getCountSeconds()+"s" : "");
        } else if (getState().equals(State.FINAL)) {
            time = "§cGame Over" + (getCountSeconds() > 0 ? ": §f"+getCountSeconds()+"s" : "");
        } else {
            time = "§fStoppage";
        }
        Sidebar.set(getPlayers(), Component.text("  play.partix.net  ", Colour.partix(), TextDecoration.BOLD),
                " ",
                "§6§lGame ",
                "  "+time,
                "  §f"+((settings.winType.timed) ? getShortPeriodString() : "  ---"),
                "          ",
                "§6§lScore ",
                "  Home: §e"+homeScore+" §8("+Text.serialize(home.abrv)+")",
                "  Away: §e"+awayScore+" §8("+Text.serialize(away.abrv)+")",
                "    "
        );

        updateBossBar("§r§f§l"+Text.serialize(away.name)+" §7§l"+awayScore+" §r§e@ §7§l"+homeScore+" §r§f§l"+Text.serialize(home.name),Math.min(1.0,Math.max(0.0,(double)getTimeTicks()/((settings.winType.amount*60)*20))));
    }

    @Override
    public void goal(GoalGame.Team team) {
        if (getBall() instanceof Basketball ball) {
            if (ball.getVelocity().getY() < 0.01) {
                BaseTeam t = team.equals(Team.HOME) ? home : away;
                boolean isThree = ball.isThreeEligible();

                if (settings.gameType.equals(GameType.AUTOMATIC)) {
                    // stat
                    List<Player> players = team.equals(Team.HOME) ? getHomePlayers() : getAwayPlayers();
                    if (ball.getLastDamager() != null) {
                        Player player = ball.getLastDamager();
                        if (players.contains(player)) {
                            player.sendMessage(Component.text("Stats > ").color(Colour.title()).append(Component.text("Nice!").color(Colour.allow())));
                            points.put(player.getUniqueId(), points.getOrDefault(player.getUniqueId(), 0) + (isThree ? 3 : 2));
                            if (isThree) {
                                threes.put(player.getUniqueId(), threes.getOrDefault(player.getUniqueId(), 0) + 1);
                            }
                        }
                    }
                }

                // point
                int score = team.equals(Team.HOME) ? homeScore : awayScore;
                if (getState().equals(State.REGULATION) || (!settings.suddenDeath) || ((!settings.winType.timed) && (score + (isThree ? 3 : 2)) >= settings.winType.amount)) {
                    sendTitle(t.name.append(Component.text(isThree ? " ‣ 3 Points!" : " ‣ 2 Points").color(Colour.partix())));
                    Player p = ball.getLastDamager();
                    if (p != null) {
                        AthleteManager.get(p.getUniqueId()).getExplosion().mediumExplosion(ball.getLocation());
                    }
                    if (team.equals(Team.HOME)) {
                        homeScore += isThree ? 3 : 2;
                        Vector v = new Vector(1.25, -1.5, 0.0);
                        getHomePlayers().stream().filter(player -> player.getLocation().getX() < getCenter().getX()).forEach(player -> {
                            player.teleport(player.getLocation().clone().set(getCenter().clone().getX(),player.getLocation().getY(),player.getLocation().getZ()));
                            player.setVelocity(v);
                        });
                        ball.setLocation(getAwaySpawn());
                        ball.setVelocity(0.05, 0.05, 0.0);
                    } else {
                        awayScore += isThree ? 3 : 2;
                        Vector v = new Vector(-1.25, -1.5, 0.0);
                        getAwayPlayers().stream().filter(player -> player.getLocation().getX() > getCenter().getX()).forEach(player -> {
                            player.teleport(player.getLocation().clone().set(getCenter().clone().getX(),player.getLocation().getY(),player.getLocation().getZ()));
                            player.setVelocity(v);
                        });
                        ball.setLocation(getHomeSpawn());
                        ball.setVelocity(-0.05, 0.05, 0.0);
                    }
                } else {
                    if (team.equals(Team.HOME)) {
                        homeScore += isThree ? 3 : 2;
                    } else {
                        awayScore += isThree ? 3 : 2;
                    }
                    gameOver(team);
                    sendTitle(Component.text("The ").color(Colour.partix()).append(t.name).append(Component.text(" Win!").color(Colour.partix())));
                    removeBalls();
                }
            }
        }
    }


    @Override
    public void joinTeam(Player player, Team team) {
        Athlete athlete = AthleteManager.get(player.getUniqueId());
        if (team.equals(Team.HOME)) {
            athlete.setSpectator(false);
            addHomePlayer(player);
            player.getActivePotionEffects().clear();
            if (settings.gameEffect.effect != null) {
                player.addPotionEffect(settings.gameEffect.effect);
            }
            player.getInventory().setChestplate(home.chest);
            player.getInventory().setLeggings(home.pants);
            player.getInventory().setBoots(home.boots);
            player.getInventory().setItem(6, Items.get(Component.text("Your Bench").color(Colour.partix()),Material.OAK_STAIRS));
            player.getInventory().setItem(7, Items.get(Component.text("Game Settings").color(Colour.partix()),Material.CHEST));
            player.getInventory().setItem(8, Items.get(Component.text("Change Team/Leave Game").color(Colour.partix()),Material.GRAY_DYE));
            if (getHomePlayers().size() < settings.playersPerTeam) {
                player.teleport(getHomeSpawn());
            } else {
                enterBench(player);
            }
            player.sendMessage(Message.joinTeam("home"));
            joinedHome.add(player.getUniqueId());
            removeAwayPlayer(player);
        } else if (team.equals(Team.AWAY)) {
            athlete.setSpectator(false);
            addAwayPlayer(player);
            player.getActivePotionEffects().clear();
            if (settings.gameEffect.effect != null) {
                player.addPotionEffect(settings.gameEffect.effect);
            }
            player.getInventory().setChestplate(Items.armor(Material.LEATHER_CHESTPLATE,0xffffff,"Jersey", "Your teams away jersey"));
            player.getInventory().setLeggings(away.away);
            player.getInventory().setBoots(away.boots);
            player.getInventory().setItem(6, Items.get(Component.text("Your Bench").color(Colour.partix()),Material.OAK_STAIRS));
            player.getInventory().setItem(7, Items.get(Component.text("Game Settings").color(Colour.partix()),Material.CHEST));
            player.getInventory().setItem(8, Items.get(Component.text("Change Team").color(Colour.partix()),Material.GRAY_DYE));
            if (getAwayPlayers().size() < settings.playersPerTeam) {
                player.teleport(getAwaySpawn());
            } else {
                enterBench(player);
            }
            player.sendMessage(Message.joinTeam("away"));
            joinedAway.add(player.getUniqueId());
            removeHomePlayer(player);
        } else {
            player.getActivePotionEffects().clear();
            athlete.setSpectator(true);
            player.getInventory().clear();
            player.getInventory().setItem(7, Items.get(Component.text("Game Settings").color(Colour.partix()),Material.CHEST));
            player.getInventory().setItem(8, Items.get(Component.text("Change Team").color(Colour.partix()),Material.GRAY_DYE));
            player.teleport(getCenter().add(0.0,2.0,0.0));
            player.sendMessage(Message.joinTeam("spectators"));
        }
    }

    @Override
    public BallType getBallType() {
        return BallType.BASKETBALL;
    }

    @Override
    public boolean canEditGame(Player player) {
        if (player.hasPermission("rank.admin")) {
            return true;
        }
        if (owner != null) {
            return (owner == player.getUniqueId());
        }
        return false;
    }

    @Override
    public void onJoin(Athlete... athletes) {
        if (settings.compType.equals(CompType.CASUAL)) {
            for (Athlete athlete : athletes) {
                joinTeam(athlete.getPlayer(),Team.SPECTATOR);
            }
        }
    }

    @Override
    public void onQuit(Athlete... athletes) {

    }

    @Override
    public void stoppageDetection() {
        Vector v = getBall().getVelocity();
        double c = Math.abs(v.getX()) + Math.abs(v.getY()) + Math.abs(v.getZ());
        double x = getBall().getLocation().getX();
        if (c < 0.001 || ((x > getHomeNet().getCenterX()+1.55 || x < getAwayNet().getCenterX()-1.55) && getBall().getCurrentDamager() == null)) {
            removeBalls();
            sendTitle(Component.text("Dead Ball").style(Style.style(Colour.deny(),TextDecoration.BOLD)));
            startCountdown(State.FACEOFF,10);
        }
    }


    public void start() {
        reset();
        startCountdown(State.FACEOFF, 10);
    }
}
