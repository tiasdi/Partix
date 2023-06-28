package me.x_tias.partix.util;

import me.x_tias.partix.plugin.athlete.Athlete;

import java.util.*;

public class Util {

    public static UUID getHighest(HashMap<UUID, Double> map) {
        Map.Entry<UUID, Double> maxEntry = null;

        for (Map.Entry<UUID, Double> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry != null ? maxEntry.getKey() : null;
    }

    public static HashMap<Integer, UUID> getRankedUUIDs(HashMap<UUID, Double> map) {
        // Convert map to list of entries
        List<Map.Entry<UUID, Double>> list = new LinkedList<>(map.entrySet());

        // Sort list by value
        list.sort(new Comparator<Map.Entry<UUID, Double>>() {
            @Override
            public int compare(Map.Entry<UUID, Double> o1, Map.Entry<UUID, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Put list back into a new map
        HashMap<Integer, UUID> sortedMap = new LinkedHashMap<>();
        int rank = 1;
        for (Map.Entry<UUID, Double> entry : list) {
            sortedMap.put(rank++, entry.getKey());
        }
        return sortedMap;
    }

    public static HashMap<Integer, Athlete> getRankedAthletes(HashMap<Athlete, Double> map) {
        // Convert map to list of entries
        List<Map.Entry<Athlete, Double>> list = new LinkedList<>(map.entrySet());

        // Sort list by value
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        // Put list back into a new map
        HashMap<Integer, Athlete> sortedMap = new LinkedHashMap<>();
        int rank = 1;
        for (Map.Entry<Athlete, Double> entry : list) {
            sortedMap.put(rank++, entry.getKey());
        }
        return sortedMap;
    }


}
