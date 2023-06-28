package me.x_tias.partix.plugin.listener;

import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallFactory;
import me.x_tias.partix.plugin.ball.BallType;
import me.x_tias.partix.plugin.ball.event.*;
import me.x_tias.partix.plugin.mechanic.Mechanic;
import me.x_tias.partix.server.specific.Lobby;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class ActionListener implements Listener {

    @EventHandler
    public void onHitBall(PlayerHitBallEvent e) {
        Ball ball = e.getBall();
        Player player = e.getPlayer();


        Mechanic.punch(player,ball);
    }

    @EventHandler
    public void onRightClick(PressRightClickEvent e) {
        Player player = e.getPlayer();
        Athlete athlete = e.getAthlete();

        if (athlete.getPlace() != null) {
            athlete.getPlace().clickItem(player,e.getItemStack());
        }


        for (Ball ball : BallFactory.getNearby(player.getLocation(), 3.5)) {
            if (ball.getCurrentDamager() != null) {
                if (ball.getCurrentDamager().equals(player)) {
                    Mechanic.rightClick(player, ball, e.isThrownInBlock());
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onLeftClick(PressLeftClickEvent e) {
        Player player = e.getPlayer();
        BallFactory.getNearest(player.getLocation(), 4.0).ifPresent(ball -> Mechanic.leftClick(player, ball, e.isThrownInBlock()));
    }


    @EventHandler
    public void onBallHitPlayer(BallHitEntityEvent e) {
        if (e.getEntity() instanceof Player player) {
            e.setCancelled(Mechanic.collides(player,e.getBall()));
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItemEvent(PressDropKeyEvent e) {
        Player player = e.getPlayer();
        for (Ball ball : BallFactory.getNearby(player.getLocation(), 4.0)) {
            if (ball.getCurrentDamager() != null) {
                if (Mechanic.dropItem(player, ball, e.isThrownInBlock())) {
                    e.setKeepItem(false);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerSwapItemEvent(PressSwapKeyEvent e) {
        Player player = e.getPlayer();
        for (Ball ball : BallFactory.getNearby(player.getLocation(), 4.0)) {
            if (ball.getCurrentDamager() != null) {
                if (Mechanic.swapItem(player, ball, e.isThrownInBlock())) {
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onAttackWithBall(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        if (e.getDamager() instanceof Player attacker && e.getEntity() instanceof Player damaged) {
            if (attacker.getLocation().distance(damaged.getLocation()) < 2.5) {
                for (Ball ball : BallFactory.getNearby(attacker.getLocation(), 4.0)) {
                    if (ball.getCurrentDamager() != null) {
                        Mechanic.attack(attacker, damaged, ball);
                    }
                }
            }
        }
    }

    @EventHandler
    public void changeItems(InventoryInteractEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            if (player.getGameMode().equals(GameMode.ADVENTURE)){
                e.setCancelled(true);
            }
        }
    }

}
