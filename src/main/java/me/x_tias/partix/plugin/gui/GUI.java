package me.x_tias.partix.plugin.gui;

import me.x_tias.partix.Partix;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.function.Consumer;

public class GUI implements Listener {

    private final HashMap<Integer, Consumer<Player>> map = new HashMap<>();
    private final Inventory i;

    public void openInventory(Player player) {
        player.openInventory(i);
    }

    private BukkitTask runnable;

    public GUI(String title, int rows, boolean refresh, ItemButton... buttons) {
        Partix pl = Partix.getInstance();
        pl.getServer().getPluginManager().registerEvents(this, pl);

        i = Bukkit.createInventory(null,rows*9, Component.text(title));
        for (ItemButton button : buttons) {
            if (button != null) {
                map.put(button.slot(), button.runnable());
                i.setItem(button.slot(), button.item());
            }
        }

        Partix partix = Partix.getInstance();

        if (refresh) {
            ItemButton[] finalButtons = buttons.clone();
            runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    i.clear();
                    for (ItemButton button : finalButtons) {
                        if (button != null) {
                            map.put(button.slot(), button.runnable());
                            i.setItem(button.slot(), button.item());
                        }
                    }
                }
            }.runTaskTimer(partix, 1, 60);
        }

    }

    @EventHandler
    public void onEdit(InventoryClickEvent e) {
        if (e.getInventory().equals(i)) {
            if (e.getWhoClicked() instanceof Player player) {
                int slot = e.getSlot();
                if (map.containsKey(slot)) {
                    map.get(slot).accept(player);
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(i)) {

            if (runnable != null) {
                runnable.cancel();
                runnable = null;
            }

            HandlerList.unregisterAll(this);
        }
    }

}
