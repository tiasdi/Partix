package me.x_tias.partix.plugin.cosmetics;

import me.x_tias.partix.Partix;
import me.x_tias.partix.util.Colour;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class CosmeticSound extends CosmeticHolder {

    public CosmeticSound(String permission, Material gui, String name, CosmeticRarity rarity, Sound sound) {
        this.permission = permission;
        this.gui = gui;
        this.rarity = rarity;
        this.name = name;
        this.sound = sound;
    }

    public String name;
    public String permission;
    public CosmeticRarity rarity;
    private final Material gui;
    private final Sound sound;

    public ItemStack getGUIItem() {
        ItemStack itemStack = new ItemStack(gui);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(rarity.getTitle()+" "+name));
        itemMeta.lore(List.of(
                Component.text("§r§8Cosmetic").color(Colour.border()),
                Component.text("   "),
                Component.text("§r§7Sound: ").append(Component.text(sound.name())).color(Colour.partix())
        ));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static CosmeticSound empty() {
        return new CosmeticSound("default",Material.BARRIER,"No Win Song",CosmeticRarity.COMMON,null);
    }

}
