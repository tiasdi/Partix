package me.x_tias.partix.season;

import me.x_tias.partix.Partix;
import me.x_tias.partix.database.SeasonDb;
import me.x_tias.partix.util.Colour;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class SeasonLeaderboard {

    private static final HashMap<Integer, String> gold = new HashMap<>();
    private static final HashMap<Integer, String> silver = new HashMap<>();


    public static void setup() {
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<Integer, UUID> top = SeasonDb.getTop(SeasonDb.Stat.POINTS, 20);
                for (int i = 1; i < 6; i++) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(top.get(i));
                    String name, points, wl;
                    if (player.hasPlayedBefore() || player.isOnline()) {
                        name = player.getName();
                        int pts = (SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.POINTS));
                        points = (pts - (pts >= 50000 ? 50000 : 0))+"pts";
                        wl = "("+(SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.WINS))+"-"+(SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.LOSSES))+")";
                    } else {
                        name = "None";
                        points = "0pts";
                        wl = "0-0";
                    }
                    if (i == 1) {
                        gold.put(1,"§6✯ §e1. "+name+" §f- §e"+points+" §7"+wl+" §6✯");
                    } else if (i == 2) {
                        gold.put(2,"§7⋅ §f2. "+name+" §7- §f"+points+" §7"+wl+" §7⋅");
                    } else {
                        gold.put(i,"§4∨ §7"+i+". "+name+" §8- §7"+points+" §8"+wl+" §4∨");
                    }
                }
                for (int i = 1; i < 11; i++) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(top.get(5+i));
                    String name, points, wl;
                    if (player.hasPlayedBefore() || player.isOnline()) {
                        name = player.getName();
                        points = (SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.POINTS))+"pts";
                        wl = "("+(SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.WINS))+"-"+(SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.LOSSES))+")";
                    } else {
                        name = "None";
                        points = "0pts";
                        wl = "(0-0)";
                    }
                    if (i < 4) {
                        silver.put(i,"§2∧ §f"+i+". "+name+" §7- §f"+points+" §7"+wl+" §2∧");
                    } else {
                        silver.put(i,"§7⋅ §7"+i+". "+name+" §8- §7"+points+" §8"+wl+" §7⋅");
                    }
                }
            }
        }.runTaskTimer(Partix.getInstance(),1,600);
    }

    public static HashMap<Integer, String> getGold() {
        return gold;
    }

    public static HashMap<Integer, String> getSilver() {
        return silver;
    }

}
