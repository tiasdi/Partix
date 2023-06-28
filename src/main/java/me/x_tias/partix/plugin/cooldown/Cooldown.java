package me.x_tias.partix.plugin.cooldown;

import me.x_tias.partix.Partix;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {

    public static void setup() {
        map = new HashMap<>();
        Partix plugin = Partix.getInstance();
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<UUID, Integer> clone = new HashMap<>(map);
                for (UUID uuid : clone.keySet()) {
                    int next = clone.get(uuid) - 1;
                    if (next < 1) {
                        map.remove(uuid);
                    } else {
                        map.put(uuid,next);
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin,1,1);
    }

    private static HashMap<UUID, Integer> map;

    public static boolean isRestricted(Player player) {
        UUID uuid = player.getUniqueId();
        if (map.containsKey(uuid)) {
            return true;
        } else {
            map.put(uuid,2);
            return false;
        }
    }

    public static boolean isRestricted(UUID uuid) {
        if (map.containsKey(uuid)) {
            return true;
        } else {
            map.put(uuid,2);
            return false;
        }
    }

    public static int getRestriction(UUID uuid) {
        return map.getOrDefault(uuid,-1);
    }

    public static void setRestricted(UUID uuid, int ticks) {
        map.put(uuid,ticks);
    }

}
