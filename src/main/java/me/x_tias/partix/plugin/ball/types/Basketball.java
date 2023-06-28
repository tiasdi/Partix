package me.x_tias.partix.plugin.ball.types;

import me.x_tias.partix.mini.basketball.BasketballGame;
import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallFactory;
import me.x_tias.partix.plugin.ball.BallType;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Position;
import me.x_tias.partix.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Basketball extends Ball {
    public Basketball(Location location, BasketballGame game) {
        super(location, BallType.BASKETBALL, 0.4,0.2, 0.2, 0.015,0.025, 0.35, 0.01,0.265, false, false, 2.0, Color.fromRGB(0xE47041), Color.BLACK);
        this.delay = 0;
        this.handYaw = 50;
        this.handModifier = 5;
        this.threeEligible = false;
        this.accuracy = 0;
        this.best = 6;
        this.accuracyWait = 0;
        this.game = game;
    }

    private final BasketballGame game;

    @Override
    public Component getControls(Player player) {
        String leftClick = "Pass";
        String rightClick = "Shoot";
        String dropItem = canDunk(player) ? "Dunk!" : "Layup";
        String swapHand = "Crossover";
        Component lc = Component.text("[", Colour.blackBorder()).append(Component.keybind("key.attack", Colour.border()).append(Component.text("]",Colour.blackBorder())).append(Component.text(" "+leftClick+", ",Colour.darkBorder())));
        Component rc = Component.text("[",Colour.blackBorder()).append(Component.keybind("key.use", Colour.border()).append(Component.text("]",Colour.blackBorder())).append(Component.text(" "+rightClick+", ",Colour.darkBorder())));
        Component di = Component.text("[",Colour.blackBorder()).append(Component.keybind("key.drop", Colour.border()).append(Component.text("]",Colour.blackBorder())).append(Component.text(" "+dropItem+", ",Colour.darkBorder())));
        Component sh = Component.text("[",Colour.blackBorder()).append(Component.keybind("key.swapOffhand", Colour.border()).append(Component.text("]",Colour.blackBorder())).append(Component.text(" "+swapHand+", ",Colour.darkBorder())));
        return lc.append(rc).append(di).append(sh);
    }

    public int delay;
    private int handYaw;
    private int handModifier;
    private boolean threeEligible;
    private int accuracy;
    private int best;
    private int accuracyWait;

    public boolean isThreeEligible() {
        return threeEligible;
    }


    public void throwBall(Player player) {
        if (getCurrentDamager() != null) {
            if (getCurrentDamager() == player) {
                executeThrow(player);
            }
        }
    }

    public Location getTargetHoop(Player player) {
        if (player.getLocation().clone().add(player.getLocation().getDirection().multiply(1.0)).getX() < game.getCenter().getX()) {
            return game.getAwayNet().clone().getCenter().toLocation(player.getWorld()).clone();
        } else {
            return game.getHomeNet().clone().getCenter().toLocation(player.getWorld()).clone();
        }
    }

    private void executeThrow(Player player) {
        float pitch = Math.min(145f,Math.max(90f,90f + Math.abs(player.getLocation().getPitch())))-90f;
        setLocation(player.getEyeLocation().add(player.getLocation().getDirection().multiply(1.425)));
        Location th = player.getLocation().clone();
        int acc = Math.abs(accuracy - best);
        float yaw = player.getLocation().getYaw();
        if (acc == 0) {
            yaw = player.getLocation().getYaw();
        } else if (acc <= 1) {
            yaw += (new Random().nextBoolean() ? 1 : -1);
        } else if (acc <= 2) {
            yaw += (new Random().nextBoolean() ? 2 : -2);
        } else if (acc <= 4) {
            yaw += (new Random().nextBoolean() ? 4 : -4);
        } else {
            yaw += (new Random().nextBoolean() ? 7 : -7);
        }
        th.setYaw(yaw);


        Vector vector = th.getDirection().normalize().multiply(0.33 + ((1.0-(pitch/45))/2.25));

        setVelocity(player,vector,0.35);

        // three pointer ??
        if (player.getLocation().clone().subtract(0.0,0.5,0.0).getBlock().getType().equals(Material.OAK_PLANKS)) {
            threeEligible = true;
        } else if (player.getLocation().clone().subtract(0.0,1.0,0.0).getBlock().getType().equals(Material.OAK_PLANKS)) {
            threeEligible = true;
        } else threeEligible = player.getLocation().clone().subtract(0.0, 1.25, 0.0).getBlock().getType().equals(Material.OAK_PLANKS);

        delay = 10;
        giveaway();
    }

    public void forceThrow() {
        if (getCurrentDamager() != null) {
            Player player = getCurrentDamager();
            setLocation(player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.45)));
            setVelocity(player, player.getLocation().getDirection().multiply(0.6));
            giveaway();
            threeEligible = false;

            delay = 10;
            giveaway();
        }
    }

    public boolean pass(Player player) {
        if (delay < 1) {
            if (getCurrentDamager() != null) {
                if (getCurrentDamager() == player) {
                    Location location = player.getEyeLocation().clone();
                    location.subtract(0.0,0.5,0.0);
                    setLocation(location.add(player.getLocation().getDirection().multiply(0.45)));
                    setVelocity(player, player.getLocation().getDirection().multiply(0.815), 0.075);
                    giveaway();
                    threeEligible = false;
                    giveaway();
                    delay = 10;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canDunk(Player player) {
        Location location = getTargetHoop(player).clone();
        location.setY(player.getLocation().getY());
        double dis = location.distance(player.getLocation());
        return (player.getLocation().getY() > game.getCenter().getY() + 0.333 &&
                (dis > 0.525 && dis < 1.725));
    }

    public boolean dunk(Player player) {
        if (getCurrentDamager() != null) {
            Location target = getTargetHoop(player);
            if (canDunk(player)) {
                double a;
                int acc = Math.abs(accuracy - best);
                if (acc == 0) {
                    a = 0.05;
                } else if (acc <= 1) {
                    a = (new Random().nextBoolean() ? 0.075 : 0.085);
                } else if (acc <= 2) {
                    a = (new Random().nextBoolean() ? 0.0115 : 0.1385);
                } else if (acc <= 3) {
                    a = (new Random().nextBoolean() ? 0.58 : 0.65);
                } else {
                    a = (new Random().nextBoolean() ? 0.833 : 0.866);
                }
                if (getCurrentDamager().equals(player)) {
                    Location slam = target.clone();
                    Location location = player.getLocation().clone();
                    location.setPitch(0);
                    slam.subtract(target.clone().subtract(player.getLocation()).multiply(0.33));
                    Vector fly = player.getLocation().getDirection().multiply(-1.08);
                    fly.setY(0.78);
                    player.setVelocity(fly);
                    setLocation(target.clone().add(0.0, 0.85, 0.0));
                    setVelocity(player, player.getLocation().getDirection().normalize().multiply(player.getLocation().distance(getTargetHoop(player)) < 6.75 ? a : 0.0105), -0.2);

                    threeEligible = false;
                    giveaway();
                    delay = 15;
                    return true;
                }
            } else {
                if (getCurrentDamager().equals(player)) {
                    double a;
                    int acc = Math.abs(accuracy - best);
                    if (acc == 0) {
                        a = 0.25;
                    } else if (acc <= 1) {
                        a = (new Random().nextBoolean() ? 0.255 : 0.26);
                    } else if (acc <= 2) {
                        a = (new Random().nextBoolean() ? 0.265 : 0.27);
                    } else if (acc <= 3) {
                        a = (new Random().nextBoolean() ? 0.55 : 0.60);
                    } else {
                        a = (new Random().nextBoolean() ? 0.833 : 0.866);
                    }
                    setLocation(player.getEyeLocation().add(player.getLocation().getDirection().multiply(1.425)));
                    setVelocity(player, player.getLocation().getDirection().normalize().multiply(player.getLocation().distance(getTargetHoop(player)) < 6.75 ? a : 0.0105), 0.335);
                    threeEligible = false;
                    giveaway();
                    delay = 15;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean crossover(Player player) {
        if (getCurrentDamager() != null) {
            if (getCurrentDamager().isOnGround()) {
                if (getCurrentDamager().equals(player)) {
                    int nextHand = handYaw + handModifier;
                    if (!(nextHand < 49 && nextHand > -49)) {
                        if (handModifier == 0) {
                            handModifier = 5;
                            player.setVelocity(Position.stabilize(player, 70, 0.0).getDirection().multiply(0.75));
                            delay = 10;
                        } else if (handModifier == 5) {
                            handModifier = -5;
                            delay = 10;
                            player.setVelocity(Position.stabilize(player, -70, 0.0).getDirection().multiply(0.75));
                        } else if (handModifier == -5) {
                            delay = 10;
                            player.setVelocity(Position.stabilize(player, 70, 0.0).getDirection().multiply(0.75));
                            handModifier = 5;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void giveaway() {
        removeCurrentDamager();
    }

    public void takeBall(Player player) {
        if (delay < 1 && getStealDelay() < 1) {
            if ((getLocation().getY() > player.getEyeLocation().getY() - 0.1) && getVelocity().getY() < 0.0)   {
                if (getCurrentDamager() == null) {
                    error(player);
                    return;
                }
            }
            steal(player);
        } else {
            error(player);
        }
    }

    public void error(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 100f, 1f);
    }

    private void steal(Player player) {
        if (!BallFactory.hasBall(player)) {
            if (getCurrentDamager() == null) {
                setDamager(player);
                delay = 10;
                accuracy = 0;
                threeEligible = false;
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 100f, 1.2f);
                player.getInventory().setHeldItemSlot(0);
                return;
            }
            if (getCurrentDamager() != player) {
                getCurrentDamager().playSound(getCurrentDamager().getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 100f, 0.8f);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 100f, 1.2f);
                setDamager(player);
                accuracy = 0;
                player.getInventory().setHeldItemSlot(0);
                delay = 10;
            }
            threeEligible = false;
        }
    }



    public boolean collides(Player player) {
        if (BallFactory.hasBall(player)) {
            return true;
        }
        if (getLastDamager() != null) {
            if ((delay < 1 || getLastDamager() != null)) {
                setStealDelay(0);
                if (getLastDamager() != player) {
                    setDamager(player);
                    delay = 10;
                    return true;
                }
                if (delay < 1) {
                    setDamager(player);
                    delay = 10;
                    return true;
                }
            } else if (getLastDamager() != player) {
                setStealDelay(0);
                setDamager(player);
                delay = 10;
                return true;
            }
        }
        return true;
    }

    private void detectTravel() {
        if (delay < 1) {
            if (!((LivingEntity) getCurrentDamager()).isOnGround()) {
                if (getCurrentDamager().getVelocity().getY() < -0.19 && delay < 1) {
                    getCurrentDamager().sendTitlePart(TitlePart.TITLE,Component.text(" "));
                    getCurrentDamager().sendTitlePart(TitlePart.SUBTITLE,Component.text("Travel!").style(Style.style(Colour.deny(),TextDecoration.BOLD)));
                    forceThrow();
                }
            }
        }
    }

    private void runDelay() {
        if (delay > 0) {
            delay -= 1;
        }
    }

    private void modifyHand() {
        int nextHand = handYaw + handModifier;
        if (nextHand < 50 && nextHand > -50) {
            handYaw = nextHand;
        }
    }

    private void postModify() {
        detectTravel();
        modifyHand();
    }

    private void displayAccuracy() {
        if (getCurrentDamager() != null) {
            Component a = Component.empty();

            boolean canDunk = canDunk(getCurrentDamager());

            for (int i = 0; i < 11; i++) {
                int distance = Math.abs(accuracy - best);
                String s = accuracy == i ? (canDunk ? "|" : ",") : (canDunk ? ";" : ".");
                int cDistance = Math.abs(i - best);
                TextColor cd;
                if (cDistance == 0) {
                    cd = Colour.allow();
                } else if (cDistance <= 2) {
                    cd = Colour.partix();
                } else {
                    cd = Colour.deny();
                }
                if (distance == 0) {
                    TextColor c = accuracy == i ? TextColor.color(0x95FF6E) : cd;
                    a = a.append(Component.text(s).color(c));
                } else if (distance <= 2) {
                    TextColor c = accuracy == i ? TextColor.color(0xFFF69A) : cd;
                    a = a.append(Component.text(s).color(c));
                } else {
                    TextColor c = accuracy == i ? TextColor.color(0xFFB6AC) : cd;
                    a = a.append(Component.text(s).color(c));
                }
            }
            getCurrentDamager().sendTitlePart(TitlePart.TITLE, Component.text("   "));
            getCurrentDamager().sendTitlePart(TitlePart.SUBTITLE, a);
            getCurrentDamager().sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ofMillis(0),Duration.ofMillis(100),Duration.ofMillis(350)));
        }
    }

    private void nextAccuracy() {
        List<Player> home = game.getHomePlayers();
        List<Player> away = game.getAwayPlayers();
        List<Player> defenders = home.contains(getCurrentDamager()) ? away : home;
        List<Player> onBall = getLocation().clone().add(getCurrentDamager().getLocation().getDirection()).getNearbyPlayers(2.5).stream().filter(defenders::contains).toList();
        onBall.forEach(player -> {
            player.sendTitlePart(TitlePart.TITLE, Component.text("   "));
            player.sendTitlePart(TitlePart.SUBTITLE, Component.text(".,.,.").color(Colour.deny()));
            player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ofMillis(0),Duration.ofMillis(100),Duration.ofMillis(350)));
        });
        int opp = onBall.size();
        accuracyWait += opp > 0 ? 2 : 1;
        if (accuracyWait > 1) {
            accuracyWait = 0;
            accuracy += 1;
            if (accuracy > 10) {
                accuracy = 0;
                best = 6; // new Random().nextBoolean() ? 6 : new Random().nextBoolean() ? 5 : 7;
            }
        }
    }

    @Override
    public void modify() {
        if (getCurrentDamager() != null) {
            nextAccuracy();
            displayAccuracy();
            Player poss = getCurrentDamager();
            Location l = Position.stabilize(poss,handYaw,0.75);
            l.setY(poss.getEyeLocation().getY()-0.75);

            double bounceSpeed = (0.1+((Math.abs(poss.getVelocity().getX())+Math.abs(poss.getVelocity().getZ()))*2))*-1;

            // dribble height max
            if (getLocation().getY() > poss.getEyeLocation().getY() - 0.75) {
                if (getLocation().getY() > poss.getEyeLocation().getY() + 0.75) {
                    setLocation(l);
                }
                setVertical(bounceSpeed);
            }

            // make sure ball still moving
            if (getSpeed() < 0.075) {
                setLocation(l);
                setVertical(bounceSpeed);
            }


            setHorizontal(l);
            postModify();
        }
        runDelay();
    }


}
