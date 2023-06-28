package me.x_tias.partix.plugin.team;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

public abstract class BaseTeam {


    ItemStack block;

    public Component abrv;
    public Component name;

    public TextColor firstColor; // main
    public TextColor secondColor; // light
    public TextColor thirdColor; // dark

    public ItemStack chest;
    public ItemStack away;
    public ItemStack pants;
    public ItemStack boots;


}
