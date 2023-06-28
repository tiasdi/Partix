package me.x_tias.partix.plugin.cosmetics;

import me.x_tias.partix.Partix;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class CosmeticBlocks extends CosmeticHolder {

    public CosmeticBlocks(String permission, Material gui, String name, String description, CosmeticRarity rarity, Material... set) {
        this.permission = permission;
        this.gui = gui;
        this.rarity = rarity;
        this.materials = set;
        this.desc = Component.text(description).color(Colour.premiumText());
        this.name = name;
    }

    public Material[] get() {
        return materials.clone();
    }

    public Material[] materials;
    private final Component desc;
    private final Material gui;

    public ItemStack getGUIItem() {
        ItemStack itemStack = new ItemStack(gui);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(rarity.getTitle()+" "+name));
        itemMeta.lore(List.of(
                Component.text("§r§8Cosmetic").color(Colour.border()),
                Component.text("   "),
                desc,
                Component.text("§r§ePrice: §6"+rarity.getCost()+" Coins")
        ));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
