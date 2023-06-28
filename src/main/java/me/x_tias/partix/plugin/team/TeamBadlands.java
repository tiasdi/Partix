package me.x_tias.partix.plugin.team;

import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class TeamBadlands extends BaseTeam {

    public TeamBadlands() {
        // color integers
        int main = 0x941c20;
        int light = 0xdb8732;
        int dark = 0x820909;
        // text colors
        firstColor = TextColor.color(main);
        secondColor = TextColor.color(light);
        thirdColor = TextColor.color(dark);
        // text
        abrv = Text.gradient("BAD",secondColor,firstColor,true);
        name = Text.gradient("Badlands",secondColor,firstColor,true);
        // armor
        chest = Items.armor(Material.LEATHER_CHESTPLATE,main,"Jersey","§r§7Your teams jersey");
        away = Items.armor(Material.LEATHER_LEGGINGS,light,"Jersey","§r§7Your teams away jersey");
        pants = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams pants");
        boots = Items.armor(Material.LEATHER_BOOTS,dark,"Boots","§r§7Your teams boots");
        // inventory item
        block = Items.create(Material.RED_TERRACOTTA,name,"§r§7Red clay, rugged wilderness.");

    }

}
