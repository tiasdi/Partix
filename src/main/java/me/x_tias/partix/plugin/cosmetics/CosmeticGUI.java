package me.x_tias.partix.plugin.cosmetics;

import me.x_tias.partix.database.PlayerDb;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.gui.GUI;
import me.x_tias.partix.plugin.gui.ItemButton;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Items;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CosmeticGUI {

    public CosmeticGUI(Player player) {
        GUI gui = new GUI("Team Selection",3,
                false, new ItemButton(10, Items.get(Component.text("Trails").color(Colour.partix()), Material.MELON_SEEDS), p -> {
                    trails(player);
                }),
                new ItemButton(12, Items.get(Component.text("Explosions").color(Colour.partix()), Material.GUNPOWDER), p -> {
                    explosions(player);
                }),
                new ItemButton(14, Items.get(Component.text("Win Songs").color(Colour.partix()), Material.MUSIC_DISC_STAL), p -> {
                    winSongs(player);
                }),
                new ItemButton(16, Items.get(Component.text("Arena Backdrops").color(Colour.partix()), Material.POLISHED_DEEPSLATE_WALL), p -> {
                    arenaBorders(player);
                })
        );
        gui.openInventory(player);
    }

    private void trails(Player player) {
        ItemButton[] buttons = new ItemButton[64];
        int i = 0;
        int x = 0;
        for (CosmeticHolder c : Cosmetics.trails.values()) {
            if (player.hasPermission(c.permission)) {
                int finalX = x;
                ItemStack item = c.getGUIItem().clone();
                if (PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.TRAIL) == x) {
                    ItemMeta meta = item.getItemMeta();
                    item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(meta);
                }
                buttons[i] = new ItemButton(i, c.getGUIItem(), p -> {
                    AthleteManager.get(player.getUniqueId()).setTrail(finalX);
                });
                i++;
            }
            x++;
        }
        new GUI("Select Trail:",6, false, buttons).openInventory(player);
    }

    private void explosions(Player player) {
        ItemButton[] buttons = new ItemButton[64];
        int i = 0;
        int x = 0;
        for (CosmeticHolder c : Cosmetics.explosions.values()) {
            if (player.hasPermission(c.permission)) {
                int finalX = x;
                ItemStack item = c.getGUIItem().clone();
                if (PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.EXPLOSION) == x) {
                    ItemMeta meta = item.getItemMeta();
                    item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(meta);
                }
                buttons[i] = new ItemButton(i, c.getGUIItem(), p -> {
                    AthleteManager.get(player.getUniqueId()).setExplosion(finalX);
                });
                i++;
            }
            x++;
        }
        new GUI("Select Explosion:",6, false, buttons).openInventory(player);

    }

    private void winSongs(Player player) {

    }

    private void arenaBorders(Player player) {
        ItemButton[] buttons = new ItemButton[64];
        int i = 0;
        int x = 0;
        for (CosmeticHolder c : Cosmetics.borders.values()) {
            if (player.hasPermission(c.permission)) {
                int finalX = x;
                ItemStack item = c.getGUIItem().clone();
                if (PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.BORDER) == x) {
                    ItemMeta meta = item.getItemMeta();
                    item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(meta);
                }
                buttons[i] = new ItemButton(i, c.getGUIItem(), p -> {
                    AthleteManager.get(player.getUniqueId()).setBorder(finalX);
                });
                i++;
            }
            x++;
        }
        new GUI("Select Backdrop:",6, false, buttons).openInventory(player);

    }



}
