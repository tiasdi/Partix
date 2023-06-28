package me.x_tias.partix.plugin.athlete;

import me.x_tias.partix.database.PlayerDb;
import me.x_tias.partix.mini.factories.Hub;
import me.x_tias.partix.plugin.cosmetics.*;
import me.x_tias.partix.server.Place;
import me.x_tias.partix.server.rank.Ranks;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Message;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.OptionalLong;

public class Athlete {

    public Athlete(Player p) {
        player = p;
        party = -1;
        updateCosmetics();
        updateRank();
        renderType = PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.RENDER) == 1 ? RenderType.SLIME : RenderType.PARTICLE;
        Hub.hub.join(this);
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlace(Place p) {
        place = p;
    }

    public Place getPlace() {
        return place;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int p) {
        party = p;
    }

    public void setSpectator(boolean b) {
        player.setGameMode(GameMode.ADVENTURE);
        if (player.isOnline()) {
            if (b) {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 255, true, false));
            } else {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
    }

    public boolean isSpectating() {
        return player.getAllowFlight() && player.hasPotionEffect(PotionEffectType.INVISIBILITY);
    }


    private final Player player;
    private int party;
    private Place place;

    private RenderType renderType;

    public void setRenderType(RenderType t) {
        renderType = t;
        PlayerDb.set(player.getUniqueId(), PlayerDb.Stat.RENDER, t.db);
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public Component getName() {
        if (player.hasPermission("rank.admin")) {
            return Text.gradient("ADMIN "+player.getName(), TextColor.fromHexString("#c24528"),TextColor.fromHexString("#c8682a") ,false).append(Component.text(" ", Colour.adminText()));
        }
        if (player.hasPermission("rank.mod")) {
            return Text.gradient("MOD "+player.getName(), TextColor.fromHexString("#1478cc"),TextColor.fromHexString("#47b0b4") ,false).append(Component.text(" ", Colour.adminText()));
        }
        if (player.hasPermission("rank.media")) {
            return Text.gradient("MEDIA "+player.getName(), TextColor.fromHexString("#8f4af1"),TextColor.fromHexString("#99a0ff") ,false).append(Component.text(" ", Colour.premiumText()));
        }
        if (player.hasPermission("rank.proplus")) {
            return Text.gradient("PRO+ "+player.getName(), TextColor.fromHexString("#c17d00"),TextColor.fromHexString("#fff55a") ,false).append(Component.text(" ", Colour.premiumText()));
        }
        if (player.hasPermission("rank.pro")) {
            return Text.gradient("PRO "+player.getName(), TextColor.fromHexString("#da9728"),TextColor.fromHexString("#edeb47") ,false).append(Component.text(" ", Colour.premiumText()));
        }
        if (player.hasPermission("rank.vip")) {
            return Text.gradient("VIP "+player.getName(), TextColor.fromHexString("#7ecd3b"),TextColor.fromHexString("#dad957") ,false).append(Component.text(" ", Colour.premiumText()));
        }
        return Component.text(player.getName()).color(TextColor.color(0xbebebe)).append(Component.text(" ", Colour.text()));
    }

    public String getRank() {
        if (player.hasPermission("rank.admin")) {
            return "§4Admin";
        }
        if (player.hasPermission("rank.mod")) {
            return "§9Mod";
        }
        if (player.hasPermission("rank.media")) {
            return "§5Media";
        }
        if (player.hasPermission("rank.proplus")) {
            return "§6P§eR§6O§e+";
        }
        if (player.hasPermission("rank.pro")) {
            return "§ePRO";
        }
        if (player.hasPermission("rank.vip")) {
            return "§aVIP";
        }
        return "§8None";
    }

    private CosmeticParticle explosion;
    private CosmeticParticle trail;
    private CosmeticBlocks border;
    private CosmeticSound winSong;

    public void setExplosion(int index) {
        explosion = Cosmetics.explosions.get(index);
        PlayerDb.set(player.getUniqueId(), PlayerDb.Stat.EXPLOSION,index);
    }

    public void setTrail(int index) {
        trail = Cosmetics.trails.get(index);
        PlayerDb.set(player.getUniqueId(), PlayerDb.Stat.TRAIL,index);
    }

    public void setBorder(int index) {
        border = Cosmetics.borders.get(index);
        PlayerDb.set(player.getUniqueId(), PlayerDb.Stat.BORDER,index);
    }

    public void setWinSong(int index) {
        winSong = Cosmetics.winSongs.get(index);
        PlayerDb.set(player.getUniqueId(), PlayerDb.Stat.WINSONG,index);
    }

    public CosmeticBlocks getBorder() {
        return border;
    }

    public CosmeticParticle getExplosion() {
        return explosion;
    }

    public CosmeticParticle getTrail() {
        return trail;
    }

    public CosmeticSound getWinSong() {
        return winSong;
    }

    public void updateCosmetics() {
        explosion = Cosmetics.explosions.get(PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.EXPLOSION));
        trail = Cosmetics.trails.get(PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.TRAIL));
        border = Cosmetics.borders.get(PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.BORDER));
        winSong = Cosmetics.winSongs.get(PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.WINSONG));

    }

    public void updateRank() {
        Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
        player.playerListName(getName());
        if (player.hasPermission("rank.admin")) {
            Ranks.getAdmin().addPlayer(player);
            Objects.requireNonNull(player.getScoreboard().getTeam(Ranks.getAdmin().getName())).addPlayer(player);
            return;
        }
        if (player.hasPermission("rank.mod")) {
            Objects.requireNonNull(player.getScoreboard().getTeam(Ranks.getMod().getName())).addPlayer(player);
            return;
        }
        if (player.hasPermission("rank.media")) {
            Objects.requireNonNull(player.getScoreboard().getTeam(Ranks.getMedia().getName())).addPlayer(player);
            return;
        }
        if (player.hasPermission("rank.pro")) {
            Objects.requireNonNull(player.getScoreboard().getTeam(Ranks.getPro().getName())).addPlayer(player);
            return;
        }
        if (player.hasPermission("rank.vip")) {
            Objects.requireNonNull(player.getScoreboard().getTeam(Ranks.getVip().getName())).addPlayer(player);
            return;
        }
        Objects.requireNonNull(player.getScoreboard().getTeam(Ranks.getDefault().getName())).addPlayer(player);
        return;
    }

    public void giveCoins(int amount, boolean multiply) {
        int amt = amount;
        if (multiply) {
            if (player.hasPermission("rank.pro")) {
                amt = amount * 5;
            } else if (player.hasPermission("rank.vip")) {
                amt = amount * 2;
            }
        }
        PlayerDb.add(player.getUniqueId(), PlayerDb.Stat.COINS, amt);
        player.sendMessage(Message.receiveCoins(amt));
    }




}
