package me.x_tias.partix.plugin.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class QualityListener implements Listener {

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onAnyDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEditInventory(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                e.setCancelled(true);
            }
        }
    }


}
