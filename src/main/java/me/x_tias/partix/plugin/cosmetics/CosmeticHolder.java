package me.x_tias.partix.plugin.cosmetics;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class CosmeticHolder {

    public String name;
    public String permission;
    public Material block;
    public CosmeticRarity rarity;
    public abstract ItemStack getGUIItem();

}
