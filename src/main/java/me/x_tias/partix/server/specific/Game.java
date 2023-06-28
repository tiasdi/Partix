package me.x_tias.partix.server.specific;

import me.x_tias.partix.mini.factories.Hub;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.server.Place;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public abstract class Game extends Place {

    // fuck if i know (i just like the organization lmfaoo)
    public UUID owner;
    public void kickAll() {
        Iterator<Athlete> playerIterator = new ArrayList<>(getAthletes()).iterator();
        while (playerIterator.hasNext()) {
            Hub.hub.join(playerIterator.next());
        }
    }


}
