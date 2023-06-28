package me.x_tias.partix.season;

import me.x_tias.partix.Partix;
import me.x_tias.partix.database.PlayerDb;
import me.x_tias.partix.database.SeasonDb;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AllTimeLeaderboard {

    private static final HashMap<Integer, String> trophies = new HashMap<>();
    private static final HashMap<Integer, String> goldSeasons = new HashMap<>();


    public static void setup() {
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<Integer, UUID> topTrophies = PlayerDb.getTop(PlayerDb.Stat.CHAMPIONSHIPS, 20);
                for (int i = 1; i < 11; i++) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(topTrophies.get(i));
                    String name, value;
                    if (player.hasPlayedBefore() || player.isOnline()) {
                        name = player.getName();
                        value = String.valueOf(PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.CHAMPIONSHIPS)) + " Trophies";
                    } else {
                        name = "None";
                        value = "0 Trophies";
                    }
                    if (i == 1) {
                        trophies.put(1,"§e1. "+name+" §f- §e"+value);
                    } else if (i == 2) {
                        trophies.put(1,"§f2. "+name+" §7- §f"+value);
                    } else if (i == 3) {
                        trophies.put(1,"§43. "+name+" §7- §4"+value);
                    } else {
                        trophies.put(i,"§e"+i+". "+name+" §7- §7"+value);
                    }
                }

                HashMap<Integer, UUID> topGold = PlayerDb.getTop(PlayerDb.Stat.SEASONS_GOLD, 20);
                for (int i = 1; i < 11; i++) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(topGold.get(i));
                    String name, value;
                    if (player.hasPlayedBefore() || player.isOnline()) {
                        name = player.getName();
                        value = String.valueOf(PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.SEASONS_GOLD)) + " Seasons";
                    } else {
                        name = "None";
                        value = "0";
                    }
                    if (i == 1) {
                        goldSeasons.put(1,"§e1. "+name+" §f- §e"+value);
                    } else if (i == 2) {
                        goldSeasons.put(2,"§f2. "+name+" §7- §f"+value);
                    } else if (i == 3) {
                        goldSeasons.put(3,"§43. "+name+" §7- §4"+value);
                    } else {
                        goldSeasons.put(i,"§e"+i+". "+name+" §7- §7"+value);
                    }
                }
            }
        }.runTaskTimer(Partix.getInstance(),1,600);
    }

    public static HashMap<Integer, String> getTrophies() {
        return trophies;
    }

    public static HashMap<Integer, String> getGoldSeasons() {
        return goldSeasons;
    }

}
