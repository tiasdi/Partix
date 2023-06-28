package me.x_tias.partix.plugin.team;

import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class TeamBastian extends BaseTeam {

    public TeamBastian() {
        // color integers
        int main = 0x171717;
        int light = 0xf7f302;
        int dark = 0x171717;
        // text colors
        firstColor = TextColor.color(main);
        secondColor = TextColor.color(light);
        thirdColor = TextColor.color(dark);
        // text
        abrv = Text.gradient("BAS",secondColor,firstColor,true);
        name = Text.gradient("Bastian",secondColor,firstColor,true);
        // armor
        chest = Items.armor(Material.LEATHER_CHESTPLATE,main,"Jersey","§r§7Your teams jersey");
        away = Items.armor(Material.LEATHER_LEGGINGS,0x858581,"Jersey","§r§7Your teams away jersey");
        pants = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams pants");
        boots = Items.armor(Material.LEATHER_BOOTS,dark,"Boots","§r§7Your teams boots");
        // inventory item
        block = Items.create(Material.POLISHED_BASALT,name,"§r§7Gold at the heart.");

    }

}
