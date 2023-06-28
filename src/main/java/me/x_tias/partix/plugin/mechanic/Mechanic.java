package me.x_tias.partix.plugin.mechanic;

import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.types.Basketball;
import me.x_tias.partix.plugin.ball.types.Puck;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Mechanic {

    public static boolean dropItem(Player player, Ball ball, boolean inBlock) {
        if (ball instanceof Basketball basketball) {
            if (!((Entity) player).isOnGround() || !inBlock) {
                return basketball.dunk(player);
            } else {
                basketball.error(player);
                return true;
            }
        }
        if (ball instanceof Puck puck) {
            return puck.sideStep(player);
        }
        return false;
    }

    public static boolean swapItem(Player player, Ball ball, boolean inBlock) {
        if (ball instanceof Basketball basketball) {
            return basketball.crossover(player);
        }
        if (ball instanceof Puck puck) {
            return puck.changeHand(player);
        }
        return false;
    }

    public static void punch(Player player, Ball ball) {
        if (ball instanceof Basketball basketball) {
            basketball.takeBall(player);
            return;
        }
        if (ball instanceof Puck puck) {
            puck.leftClick(player);
            return;
        }
    }

    public static void attack(Player attacker, Player damaged, Ball ball) {
        if (ball instanceof Basketball basketball) {
            return;
        }
    }

    public static void leftClick(Player player, Ball ball, boolean inBlock) {
        if (ball instanceof Basketball basketball) {
            if (!inBlock) {
                basketball.pass(player);
            } else {
                basketball.error(player);
            }
        }
        if (ball instanceof Puck puck) {
            puck.pass(player);
            return;
        }
    }

    public static void rightClick(Player player, Ball ball, boolean inBlock) {
        if (ball instanceof Basketball basketball) {
            if (!inBlock) {
                basketball.throwBall(player);
            } else {
                basketball.error(player);
            }
            return;
        }
        if (ball instanceof Puck puck) {
            puck.shoot(player);
            return;
        }
    }

    public static boolean collides(Player player, Ball ball) {
        if (ball instanceof Basketball basketball) {
            return basketball.collides(player);
        }
        if (ball instanceof Puck puck) {
            return puck.collides(player);
        }
        return false;
    }



}
