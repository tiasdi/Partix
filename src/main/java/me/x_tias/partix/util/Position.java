package me.x_tias.partix.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Position {

    public static Location stabilize(Player player) {
        Location location = player.getLocation().clone();
        location.setPitch(0f);
        return location;
    }

    public static Location stabilize(Player player, double push) {
        Location location = player.getLocation().clone();
        location.setPitch(0f);
        location.add(location.getDirection().multiply(push));
        return location;
    }

    public static Location stabilize(Player player, float addYaw, double push) {
        Location location = player.getLocation().clone();
        location.setPitch(0f);
        location.setYaw(location.getYaw() + addYaw);
        location.add(location.getDirection().multiply(push));
        return location;
    }

}
