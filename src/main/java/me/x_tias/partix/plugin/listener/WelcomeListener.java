package me.x_tias.partix.plugin.listener;

import com.destroystokyo.paper.event.player.PlayerHandshakeEvent;
import me.x_tias.partix.database.Databases;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.sidebar.Sidebar;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Message;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WelcomeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.joinMessage(Component.empty());

        player.getActivePotionEffects().clear();
        player.setFoodLevel(20);
        player.setCollidable(false);

        Athlete athlete = AthleteManager.create(player);

        player.setGameMode(GameMode.ADVENTURE);

        Databases.create(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.quitMessage(Component.empty());
        Athlete athlete = AthleteManager.remove(player);
        athlete.getPlace().quit(athlete);
    }

    @EventHandler
    public void onTest(PlayerLoginEvent e) {
        if (e.getResult().equals(PlayerLoginEvent.Result.KICK_WHITELIST)) {
            e.kickMessage(
                    Component.text("""
                            §6§lYou are not whitelisted

                            §fYou can request whitelist via our §ddiscord§e!
                            
                            
                            §7Partix Store: §eComing Soon
                            §7Partix Discord: §ehttps://discord.gg/vzbTKWCQv6
                            """)
            );
            return;
        }
        if (e.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED)) {
            e.kickMessage(
                    Component.text("""
                            §6§lYou are blacklisted

                            §fYou can appeal your blacklist on our §ddiscord§f!
                            
                            
                            §7Partix Store: §eComing Soon
                            §7Partix Discord: §ehttps://discord.gg/vzbTKWCQv6
                            """)
            );
            return;
        }
        if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
            if (e.getPlayer().hasPermission("rank.vip")) {
                e.allow();
                return;
            } else {
                e.kickMessage(
                        Component.text("""
                                §6§lThe server is full (50/50)

                                §fOur §a§lVIP §r§frank allows you to bypass this limit!
                                
                                
                                §7Partix Store: §Coming Soon
                                §7Partix Discord: §ehttps://discord.gg/vzbTKWCQv6
                                """)
                );
            }
        }
    }
}
