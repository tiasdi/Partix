package me.x_tias.partix.season;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.x_tias.partix.database.SeasonDb;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SeasonPlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "season";
    }

    @Override
    public @NotNull String getAuthor() {
        return "x_tias";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean register() {
        return super.register();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

        if (params.startsWith("gold")) {
            int c = Integer.parseInt(params.replaceAll("gold_",""));
            return Objects.requireNonNullElse(SeasonLeaderboard.getGold().get(c),"Not Found..");
        }
        if (params.startsWith("silver")) {
            int c = Integer.parseInt(params.replaceAll("silver_",""));
            return Objects.requireNonNullElse(SeasonLeaderboard.getSilver().get(c),"Not Found..");
        }
        if (params.startsWith("goldseasons")) {
            int c = Integer.parseInt(params.replaceAll("goldseasons_",""));
            return Objects.requireNonNullElse(AllTimeLeaderboard.getGoldSeasons().get(c),"Not Found..");
        }
        if (params.startsWith("trophies")) {
            int c = Integer.parseInt(params.replaceAll("trophies_",""));
            return Objects.requireNonNullElse(AllTimeLeaderboard.getTrophies().get(c),"Not Found..");
        }
        if (params.startsWith("time")) {
            return Season.getTimeRemaining();
        }
        if (params.startsWith("points")) {
            int pts = (SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.POINTS));
            return String.valueOf(pts - (pts >= 50000 ? 50000 : 0));
        }
        if (params.startsWith("division")) {
            int pts = (SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.POINTS));
            return pts >= 50000 ? "§eGold" : "§7Silver";
        }
        if (params.startsWith("record")) {
            return SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.WINS)+"-"+SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.LOSSES);
        }
        if (params.startsWith("wins")) {
            return String.valueOf(SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.WINS));
        }
        if (params.startsWith("losses")) {
            return String.valueOf(SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.LOSSES));
        }



        return "";

    }
}
