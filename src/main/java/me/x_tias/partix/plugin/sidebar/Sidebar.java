package me.x_tias.partix.plugin.sidebar;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Sidebar {

    private static final HashMap<Player, Scoreboard> scoreboards = new HashMap<>();

    public static void set(Player player, Component title, String... lines) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboards.get(player);

        if(scoreboard == null){
            scoreboard = manager.getNewScoreboard();
            scoreboards.put(player, scoreboard);
        } else {
            // Clear existing scores
            for(String entry : scoreboard.getEntries()) {
                scoreboard.resetScores(entry);
            }
        }

        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        if (objective == null) {
            objective = scoreboard.registerNewObjective("sidebar", Criteria.DUMMY, title);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        objective.displayName(title);

        int i = 100;
        for (String s : lines) {
            Score row = objective.getScore(s);
            row.setScore(i);
            i-= 1;
        }

        player.setScoreboard(scoreboard);
    }

    public static void set(List<Player> players, Component title, String... lines) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        for(Player player: players) {
            Scoreboard scoreboard = scoreboards.get(player);

            if(scoreboard == null){
                scoreboard = manager.getNewScoreboard();
                scoreboards.put(player, scoreboard);
            } else {
                // Clear existing scores
                for(String entry : scoreboard.getEntries()) {
                    scoreboard.resetScores(entry);
                }
            }

            Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
            if (objective == null) {
                objective = scoreboard.registerNewObjective("sidebar", Criteria.DUMMY, title);
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            objective.displayName(title);

            int i = 100;
            for (String s : lines) {
                Score row = objective.getScore(s);
                row.setScore(i);
                i-= 1;
            }

            player.setScoreboard(scoreboard);
        }
    }
}
