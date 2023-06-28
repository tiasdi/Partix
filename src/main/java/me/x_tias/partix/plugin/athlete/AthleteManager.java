package me.x_tias.partix.plugin.athlete;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AthleteManager {
    private static final HashMap<UUID, Athlete> map = new HashMap<>();

    public static Athlete get(UUID uuid) {
        return map.get(uuid);
    }

    public static Athlete create(Player player) {
        Athlete athlete = new Athlete(player);
        map.put(player.getUniqueId(),athlete);
        return athlete;
    }

    public static Athlete remove(Player player) {
        Athlete athlete = map.get(player.getUniqueId());
        map.remove(player.getUniqueId());
        return athlete;
    }

}
