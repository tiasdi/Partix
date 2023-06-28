package me.x_tias.partix.server.specific;

import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.server.Place;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Lobby extends Place {

    @Override
    public void join(Athlete... athletes) {
        super.join(athletes);
        for (Athlete athlete : athletes) {
            giveItems(athlete.getPlayer());
        }
    }

    public abstract void giveItems(Player player);


}
