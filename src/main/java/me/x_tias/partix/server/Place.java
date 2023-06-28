package me.x_tias.partix.server;


import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.party.Party;
import me.x_tias.partix.plugin.party.PartyFactory;
import me.x_tias.partix.server.specific.Game;
import me.x_tias.partix.util.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Place {

    public Place() {
        PlaceLoader.create(this);
    }

    private final BossBar bossBar = Bukkit.createBossBar("Loading..", BarColor.YELLOW, BarStyle.SOLID);
    private List<Athlete> players = new ArrayList<>();

    public abstract void onTick();

    public abstract void onJoin(Athlete... athletes);


    public abstract void onQuit(Athlete... athletes);

    public void join(Athlete... athletes) {
        if (players == null) {
            players = new ArrayList<>();
        }
        for (Athlete athlete : athletes) {
            if (athlete.getPlace() != null) {
                athlete.getPlace().quit(athletes);
            }
            athlete.setPlace(this);
            Player player = athlete.getPlayer();
            sendMessage(Message.joinServer(player));
            bossBar.addPlayer(player);
            players.add(athlete);
            player.stopAllSounds();
            player.getActivePotionEffects().clear();
            player.getScoreboardTags().clear();
            player.getInventory().clear();
        }
        onJoin(athletes);
    }
    public void quit(Athlete... athletes) {
        for (Athlete athlete : athletes) {
            Player player = athlete.getPlayer();
            bossBar.removePlayer(player);
            players.remove(athlete);
            player.getActivePotionEffects().clear();
            athlete.setPlace(null);
            athlete.setSpectator(false);
            if (this instanceof Game) {
                sendMessage(Message.quitServer(player));
            }
        }
        onQuit(athletes);
    }
    public void updateBossBar(String title) {
        bossBar.setTitle(title);
    }

    public void updateBossBar(String title, double progress) {
        bossBar.setTitle(title);
        bossBar.setProgress(progress);
    }

    public List<Athlete> getAthletes() {
        List<Athlete> copy = new ArrayList<>(players);
        players = copy.stream().filter(athlete -> athlete.getPlayer().isOnline()).collect(Collectors.toList());
        return players;
    }

    public List<Player> getPlayers() {
        List<Athlete> copy = new ArrayList<>(players);
        return copy.stream().map(Athlete::getPlayer).collect(Collectors.toList());
    }

    public void sendMessage(Component c) {
        getPlayers().forEach(player -> player.sendMessage(c));
    }

    public void sendTitle(Component c) {
        players.forEach(a -> {
            Player player = a.getPlayer();
            player.sendTitlePart(TitlePart.TITLE,Component.text("  "));
            player.sendTitlePart(TitlePart.SUBTITLE,c);
            player.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ofMillis(350),Duration.ofMillis(1450),Duration.ofMillis(350)));
        });
    }

    public void addPotionEffects(PotionEffect... effects) {
        getPlayers().forEach(player -> {
            for (PotionEffect effect : effects) {
                player.addPotionEffect(effect);
            }
        });
    }

    public void removePotionEffects(PotionEffectType... effectTypes) {
        getPlayers().forEach(player -> {
            for (PotionEffectType effect : effectTypes) {
                player.removePotionEffect(effect);
            }
        });
    }

    public void playSound(Sound sound, SoundCategory category, float volume, float pitch) {
        getPlayers().forEach(player -> player.playSound(player.getLocation(), sound, category, volume, pitch));
    }

    public abstract void clickItem(Player player, ItemStack itemStack);







}
