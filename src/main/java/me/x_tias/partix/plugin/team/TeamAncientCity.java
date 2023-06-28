package me.x_tias.partix.plugin.team;

import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class TeamAncientCity extends BaseTeam {

    public TeamAncientCity() {
        // color integers
        int main = 0x0a4249;
        int light = 0x9ed5d2;
        int dark = 0x0a4249;
        // text colors
        firstColor = TextColor.color(main);
        secondColor = TextColor.color(light);
        thirdColor = TextColor.color(dark);
        // text
        abrv = Text.gradient("ANC",secondColor,firstColor,true);
        name = Text.gradient("Ancient City",secondColor,firstColor,true);
        // armor
        chest = Items.armor(Material.LEATHER_CHESTPLATE,dark,"Jersey","§r§7Your teams jersey");
        away = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams away pants");
        pants = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams pants");
        boots = Items.armor(Material.LEATHER_BOOTS,dark,"Boots","§r§7Your teams boots");
        // inventory item
        block = Items.create(Material.POLISHED_DEEPSLATE,name,"§r§7Whats that sound.");

    }

}
