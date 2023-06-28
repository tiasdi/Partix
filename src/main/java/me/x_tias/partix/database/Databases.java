package me.x_tias.partix.database;

import me.x_tias.partix.season.Season;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class Databases {

    public static void setup() {
        try {

            BasketballDb.setup();
            SeasonDb.setup();
            PlayerDb.setup();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void create(Player player){
        UUID uuid = player.getUniqueId();

        BasketballDb.create(uuid);
        SeasonDb.create(uuid);
        PlayerDb.create(uuid, player.getName());
    }

    public static String getUrl() {
        return "jdbc:mysql://104.128.55.120:3306/";
    }

    public static String getName() {
        return "s19268_stats";
    }

    public static String getUsername() {
        return "u19268_hjMT3phCYp";
    }

    public static String getPassword() {
        return "M5MP=xI8f9i.r85^87T3l=JQ";
    }


}
