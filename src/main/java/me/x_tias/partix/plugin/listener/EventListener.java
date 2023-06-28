package me.x_tias.partix.plugin.listener;

import me.x_tias.partix.Partix;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallFactory;
import me.x_tias.partix.plugin.ball.event.*;
import me.x_tias.partix.plugin.cooldown.Cooldown;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Optional;

public class EventListener implements Listener {

    private boolean cantExecute(Player player) {
        return (!player.getGameMode().equals(GameMode.ADVENTURE)) || (AthleteManager.get(player.getUniqueId()).isSpectating());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (Objects.equals(e.getHand(), EquipmentSlot.HAND)) {

            if (Cooldown.isRestricted(player)) {
                return;
            }

            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

                if (!player.getGameMode().equals(GameMode.ADVENTURE)) {
                    return;
                }

                PressRightClickEvent event = new PressRightClickEvent(player,player.getInventory().getItemInMainHand());
                Partix.getInstance().getServer().getPluginManager().callEvent(event);

            } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_AIR)) {

                if (cantExecute(player)) {
                    return;
                }

                final Location start = player.getEyeLocation();
                final Vector direction = player.getLocation().getDirection();
                for (double distance = 0.0; distance < 4.0; distance += 0.1) {
                    Location detect = start.clone().add(direction.clone().multiply(distance));
                    Optional<Player> playerDetection = detect.getNearbyPlayers(1.5).stream().findFirst();
                    Optional<Ball> ballDetection = BallFactory.getNearest(detect,1.5);
                    double pDistance = 10.0;
                    double bDistance = 20.0;
                    if (playerDetection.isPresent()) {
                        Player hit = playerDetection.get();
                        if (hit.getLocation().distance(detect) < 1.0) {
                            pDistance = hit.getLocation().distance(detect);
                        }
                    }
                    if (ballDetection.isPresent()) {
                        Ball ball = ballDetection.get();
                        if (ball.getLocation().distance(detect) < 0.1+(ball.getHitboxSize())) {
                            boolean b;
                            bDistance = ball.getLocation().distance(detect);
                            if (ball.getCurrentDamager() == null) {
                                b = true;
                            } else {
                                b = ball.getCurrentDamager() != player;
                            }
                            if (b) {
                                if (bDistance < pDistance) {
                                    Location pl = player.getLocation();
                                    pl.setY(ball.getLocation().getY());
                                    if (pl.distance(ball.getLocation()) < ball.getStealBallDistance()) {
                                        Partix.getInstance().getServer().getPluginManager().callEvent(
                                                new PlayerHitBallEvent(player, ball, detect.clone())
                                        );
                                    }
                                    return;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    if (pDistance < 3.0) {
                        break;
                    }
                }

                if (e.getAction().isLeftClick()) {
                    Partix.getInstance().getServer().getPluginManager().callEvent(
                            new PressLeftClickEvent(player,player.getInventory().getItemInMainHand())
                    );
                }


            }
        }
    }


    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();

        if (Cooldown.isRestricted(player)) {
            e.setCancelled(true);
            return;
        }

        if (cantExecute(player)) {
            return;
        }

        PressDropKeyEvent event = new PressDropKeyEvent(player,e.getItemDrop().getItemStack());


        Partix.getInstance().getServer().getPluginManager().callEvent(event);

        if (event.isItemKept()) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
            e.getItemDrop().remove();
        }

    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();

        if (Cooldown.isRestricted(player)) {
            e.setCancelled(true);
            return;
        }

        if (cantExecute(player)) {
            return;
        }

        Partix.getInstance().getServer().getPluginManager().callEvent(
                new PressSwapKeyEvent(player,e.getMainHandItem())
        );

        e.setCancelled(true);


    }


}
