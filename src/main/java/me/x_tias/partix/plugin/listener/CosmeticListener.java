package me.x_tias.partix.plugin.listener;

import me.x_tias.partix.mini.lobby.MainLobby;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.ball.BallFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CosmeticListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Athlete athlete = AthleteManager.get(player.getUniqueId());
        if (athlete.getPlace() instanceof MainLobby || BallFactory.hasBall(player)) {
            athlete.getTrail().trail(player.getLocation());
        }
    }

}
