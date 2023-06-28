package me.x_tias.partix.plugin.team;

import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class TeamFortress extends BaseTeam {

    public TeamFortress() {
        // color integers
        int main = 0x4d0418;
        int light = 0x666666;
        int dark = 0x4d0418;
        // text colors
        firstColor = TextColor.color(main);
        secondColor = TextColor.color(light);
        thirdColor = TextColor.color(dark);
        // text
        abrv = Text.gradient("NET",secondColor,firstColor,true);
        name = Text.gradient("Nether Fortress",secondColor,firstColor,true);
        // armor
        chest = Items.armor(Material.LEATHER_CHESTPLATE,main,"Jersey","§r§7Your teams jersey");
        away = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams away pants");
        pants = Items.armor(Material.LEATHER_LEGGINGS,light,"Pants","§r§7Your teams pants");
        boots = Items.armor(Material.LEATHER_BOOTS,dark,"Boots","§r§7Your teams boots");
        // inventory item
        block = Items.create(Material.NETHER_BRICKS,name,"§r§7A fortress to defend.");

    }

}
