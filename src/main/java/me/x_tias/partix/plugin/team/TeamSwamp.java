package me.x_tias.partix.plugin.team;

import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class TeamSwamp extends BaseTeam {

    public TeamSwamp() {
        // color integers
        int main = 0x10140f;
        int light = 0x284228;
        int dark = 0x10140f;
        // text colors
        firstColor = TextColor.color(0x719973);
        secondColor = TextColor.color(0x678a69);
        thirdColor = TextColor.color(dark);
        // text
        abrv = Text.gradient("SWP",secondColor,firstColor,true);
        name = Text.gradient("Swamp",secondColor,firstColor,true);
        // armor
        chest = Items.armor(Material.LEATHER_CHESTPLATE,main,"Jersey","§r§7Your teams jersey");
        away = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams away pants");
        pants = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams pants");
        boots = Items.armor(Material.LEATHER_BOOTS,dark,"Boots","§r§7Your teams boots");
        // inventory item
        block = Items.create(Material.GRAY_TERRACOTTA,name,"§r§7Dark water, hidden treasures.");

    }

}
