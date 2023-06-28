package me.x_tias.partix.plugin.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {


    @EventHandler
    public void onSendMessage(AsyncChatEvent e) {
        Player player = e.getPlayer();
        Athlete athlete = AthleteManager.get(player.getUniqueId());
        athlete.getPlace().sendMessage(Message.sendMessage(athlete,e.message()));
        e.setCancelled(true);
    }


}
