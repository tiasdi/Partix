package me.x_tias.partix.plugin.team;

import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;

public class TeamSandTemple extends BaseTeam {

    public TeamSandTemple() {
        // color integers
        int main = 0x9e8759;
        int light = 0xf75c28;
        int dark = 0x9e8759;
        // text colors
        firstColor = TextColor.color(main);
        secondColor = TextColor.color(light);
        thirdColor = TextColor.color(dark);
        // text
        abrv = Text.gradient("TEM",secondColor,firstColor,true);
        name = Text.gradient("Sand Temple",secondColor,firstColor,true);
        // armor
        chest = Items.armor(Material.LEATHER_CHESTPLATE,main,"Jersey","§r§7Your teams jersey");
        away = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams away pants");
        pants = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams pants");
        boots = Items.armor(Material.LEATHER_BOOTS,dark,"Boots","§r§7Your teams boots");
        // inventory item
        block = Items.create(Material.ORANGE_WOOL,name,"§r§7Dont touch the pressure plate.");

    }

}
