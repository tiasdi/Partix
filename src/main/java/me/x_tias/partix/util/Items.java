package me.x_tias.partix.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Items {

    public static ItemStack get(Component component, Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(component);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack get(Component component, Material material, int amount) {
        ItemStack itemStack = new ItemStack(material,amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(component);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack get(Component component, Material material, int amount, String... lore) {
        ItemStack itemStack = new ItemStack(material,amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(component);
        meta.lore(Arrays.stream(lore).map(Component::text).toList());
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public static ItemStack create(Material material, Component name, String... lore) {
        ItemStack itemStack = new ItemStack(material,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(Arrays.stream(lore).map(Component::text).toList());
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack armor(Material material, int color, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material,1);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        itemMeta.lore(Arrays.stream(lore).map(Component::text).toList());
        itemMeta.setColor(Color.fromRGB(color));
        itemMeta.displayName(Component.text(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPlayerHead(UUID uuid) {
        // Get an OfflinePlayer object with the specified UUID
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

        if (player.hasPlayedBefore()) {
            // Create a new ItemStack of a player head
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

            // Get the SkullMeta of the ItemStack
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

            // Set the owner of the SkullMeta to the player
            skullMeta.setOwningPlayer(player);

            // Apply the SkullMeta to the ItemStack
            playerHead.setItemMeta(skullMeta);

            // Return the ItemStack
            return playerHead;
        }

        return null;
    }

    public static ItemStack getPlayerHead(UUID uuid, String title, String... lore) {
        // Get an OfflinePlayer object with the specified UUID
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

        if (player.hasPlayedBefore()) {
            // Create a new ItemStack of a player head
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);

            // Get the SkullMeta of the ItemStack
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            skullMeta.displayName(Component.text(title).color(Colour.partix()));
            skullMeta.lore(Arrays.stream(lore).map(Component::text).toList());

            // Set the owner of the SkullMeta to the player
            skullMeta.setOwningPlayer(player);

            // Apply the SkullMeta to the ItemStack
            playerHead.setItemMeta(skullMeta);

            // Return the ItemStack
            return playerHead;
        }

        return null;
    }

}
