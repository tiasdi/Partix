package me.x_tias.partix.plugin.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public record ItemButton(int slot, ItemStack item, Consumer<Player> runnable) {


}
