package me.x_tias.partix.plugin.party;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PartyFactory {

    private static final HashMap<Integer,Party> parties = new HashMap<>();

    public static Party create(Player player) {
        Party party = new Party(player);
        int id = -1;
        for (int i = 0; i < 255; i++) {
            int pid = 10000 + ((int)(Math.random()*89999));
            if (!parties.containsKey(pid) && !parties.containsKey(pid+1) && !parties.containsKey(pid-1)) {
                id = pid;
                break;
            }
        }
        party.id = id;
        parties.put(id, party);
        return party;
    }

    public static Party get(int p) {
        return parties.get(p);
    }

    public static void disband(int p) {
        parties.remove(p);
    }

    public static boolean exists(int id) {
        return parties.containsKey(id);
    }


}
