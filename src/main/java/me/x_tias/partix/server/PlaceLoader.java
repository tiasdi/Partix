package me.x_tias.partix.server;

import me.x_tias.partix.Partix;
import me.x_tias.partix.server.specific.Lobby;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceLoader {

    public static List<Place> places = new ArrayList<>();

    public static void setup() {
        new BukkitRunnable() {
            @Override
            public void run() {
                places.forEach(Place::onTick);
            }
        }.runTaskTimer(Partix.getInstance(),1,1);
    }

    public static void create(Place place) {
        if (place instanceof Lobby lobby) {
            if (!places.contains(lobby)) {
                places.add(lobby);
            }
        }
    }

}
