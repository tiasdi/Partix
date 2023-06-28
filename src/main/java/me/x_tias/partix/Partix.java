package me.x_tias.partix;

import co.aikar.commands.PaperCommandManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.x_tias.partix.command.ParticleCommand;
import me.x_tias.partix.command.PartixCommand;
import me.x_tias.partix.command.PartyCommand;
import me.x_tias.partix.database.Databases;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.ball.BallFactory;
import me.x_tias.partix.plugin.cooldown.Cooldown;
import me.x_tias.partix.plugin.cosmetics.Cosmetics;
import me.x_tias.partix.plugin.cosmetics.ItemShop;
import me.x_tias.partix.plugin.listener.*;
import me.x_tias.partix.season.AllTimeLeaderboard;
import me.x_tias.partix.season.Season;
import me.x_tias.partix.season.SeasonLeaderboard;
import me.x_tias.partix.season.SeasonPlaceholder;
import me.x_tias.partix.server.PlaceLoader;
import me.x_tias.partix.server.rank.Ranks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.UUID;

public final class Partix extends JavaPlugin implements Listener {

    private static Partix plugin;
    private static Scoreboard scoreboard;
    private static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        database();
        factories();
        listeners();
        placeholders();
        cosmetics();
        commands();
        ranks();
    }

    private void ranks() {
        Ranks.setup(Bukkit.getScoreboardManager().getMainScoreboard());
    }



    private void database() {
        Databases.setup();
        Season.setup();
    }

    private void cosmetics() {
        Cosmetics.setup();
        ItemShop.setup();
    }

    private void factories() {
        Cooldown.setup();
        BallFactory.setup();
        PlaceLoader.setup();
    }

    private void listeners() {
        plugin.getServer().getPluginManager().registerEvents(new EventListener(),this);
        plugin.getServer().getPluginManager().registerEvents(new ActionListener(),this);
        plugin.getServer().getPluginManager().registerEvents(new WelcomeListener(),this);
        plugin.getServer().getPluginManager().registerEvents(new QualityListener(),this);
        plugin.getServer().getPluginManager().registerEvents(new ChatListener(),this);
        plugin.getServer().getPluginManager().registerEvents(new CosmeticListener(),this);
    }

    private void commands() {
        PaperCommandManager cm = new PaperCommandManager(this);
        cm.registerCommand(new PartixCommand());
        cm.registerCommand(new PartyCommand());
        cm.registerCommand(new ParticleCommand());
    }

    private void placeholders() {
        SeasonLeaderboard.setup();
        AllTimeLeaderboard.setup();
        new SeasonPlaceholder().register();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plugin = null;
    }

    public static Player getPlayer(String name) {
        return plugin.getServer().getPlayer(name);
    }

    public static Player getPlayer(UUID uuid) {
        return plugin.getServer().getPlayer(uuid);
    }

    public static List<? extends Player> getOnlinePlayers() {
        return plugin.getServer().getOnlinePlayers().stream().toList();
    }

    public static Partix getInstance() {
        return plugin;
    }

    public static ProtocolManager getProtocolManager() { return protocolManager; }



}
