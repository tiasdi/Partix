package me.x_tias.partix.plugin.ball;

import me.x_tias.partix.Partix;
import me.x_tias.partix.mini.basketball.BasketballGame;
import me.x_tias.partix.plugin.ball.types.Basketball;
import me.x_tias.partix.plugin.ball.types.Golfball;
import me.x_tias.partix.plugin.ball.types.Puck;
import me.x_tias.partix.server.Place;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Items;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.*;

public class BallFactory {

    public static void setup() {
        ballList = new ArrayList<>();
        new BukkitRunnable() {
            @Override
            public void run() {
                new ArrayList<>(ballList).forEach(Ball::move);
            }
        }.runTaskTimer(Partix.getInstance(),1,1);
    }

    public static Ball create(Location location, BallType ballType, Place place) {
        switch (ballType) {
            case PUCK -> {
                Ball ball = new Puck(location);
                ballList.add(ball);
                return ball;
            }
            case BASKETBALL -> {
                if (place instanceof BasketballGame game) {
                    Ball ball = new Basketball(location, game);
                    ballList.add(ball);
                    return ball;
                }
                return null;
            }
            case GOLFBALL -> {
                Ball ball = new Golfball(location);
                ballList.add(ball);
                return ball;
            }
        }
        return new Puck(location);
    }

    public static boolean hasBall(Player player) {
        for (Ball ball : getNearby(player.getLocation(),3.0)) {
            if (ball.getCurrentDamager() != null) {
                if (ball.getCurrentDamager().equals(player)) {
                    player.getInventory().setItem(0, Items.get(Component.text("play.partix.net").color(Colour.partix()), Material.POLISHED_BLACKSTONE_BUTTON));
                    return true;
                }
            }
        }
        return false;
    }

    private static List<Ball> ballList;

    public static void remove(Ball ball) {
        ballList.remove(ball);
        ball.removeCurrentDamager();
    }

    public static void removeBalls(Location location, double radius) {
        List<Ball> collection = ballList.stream().filter(ball -> ball.getLocation().distance(location) < (radius - (ball.getDimensions().getX()/2))).toList();
        if (collection.size() > 0) {
            collection.forEach(Ball::remove);
        }
    }

    public static void removeBalls(BoundingBox box) {
        List<Ball> collection = ballList.stream().filter(ball -> box.contains(ball.getLocation().toVector())).toList();
        if (collection.size() > 0) {
            collection.forEach(Ball::remove);
        }
    }

    public static List<Ball> getNearby(Location location, double radius) {
        return ballList.stream().filter(ball -> ball.getLocation().distance(location) < (radius - (ball.getDimensions().getX() / 2))).toList();
    }

    public static Optional<Ball> getNearest(Location location, double max) {
        List<Ball> list = getNearby(location,max);
        double distance = max + 0.5;
        Ball ball = null;
        for (Ball b : list) {
            if (b.getLocation().distance(location) < distance) {
                ball = b;
                distance = b.getLocation().distance(location);
            }
        }
        return ball == null ? Optional.empty() : Optional.of(ball);
    }


}
