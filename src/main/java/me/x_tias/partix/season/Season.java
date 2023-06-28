package me.x_tias.partix.season;

import me.x_tias.partix.Partix;
import me.x_tias.partix.database.PlayerDb;
import me.x_tias.partix.database.SeasonDb;
import me.x_tias.partix.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class Season implements Runnable {
    private static SimpleDateFormat dateFormat;
    private static Calendar targetTime;

    private static String timeRemaining;

    public static void setup() {
        // Set the target time to Sunday 9:00 PM EST
        targetTime = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        targetTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // sunday
        targetTime.set(Calendar.HOUR_OF_DAY, 21); // 9:00 PM 21
        targetTime.set(Calendar.MINUTE, 0); // 0
        targetTime.set(Calendar.SECOND, 0); // 0
        targetTime.set(Calendar.MILLISECOND, 0); // 0

        // If we're already past this Sunday at 9pm, move to next Sunday
        Calendar currentTime = Calendar.getInstance();
        if (currentTime.after(targetTime)) {
            targetTime.add(Calendar.WEEK_OF_YEAR, 1);
        }

        // Calculate the delay in ticks (1 tick = 1/20th of a second)
        long delay = (targetTime.getTimeInMillis() - currentTime.getTimeInMillis()) / 50;

        // Start the countdown task
        Bukkit.getServer().getScheduler().runTaskTimer(Partix.getInstance(), new Season(), delay, 20L); // Run every second (20 ticks)

        // Initialize date format
        dateFormat = new SimpleDateFormat("dd'd:'HH'h:'mm'm'");
        dateFormat.setTimeZone(targetTime.getTimeZone());
    }

    @Override
    public void run() {
        Calendar currentTime = Calendar.getInstance();
        if (currentTime.after(targetTime)) {
            // The target time has passed, move to next Sunday
            targetTime.add(Calendar.WEEK_OF_YEAR, 1); // (1)
            targetTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // SUNDAY
            targetTime.set(Calendar.HOUR_OF_DAY, 21); // 9:00 PM 21
            targetTime.set(Calendar.MINUTE, 0); // 0
            targetTime.set(Calendar.SECOND, 0); // 0
            targetTime.set(Calendar.MILLISECOND, 0); //0
            endWeek(); // Trigger end of week
        }

        // Calculate the remaining time
        long remainingMillis = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();
        if (remainingMillis <= 0) {
            // The countdown has reached 0, do something here if needed
            return;
        }

        timeRemaining = dateFormat.format(new Date(remainingMillis));
    }

    private static void endWeek() {
        HashMap<Integer, UUID> map = new HashMap<>(SeasonDb.getTop(SeasonDb.Stat.POINTS, 15));
        SeasonDb.setAll(SeasonDb.Stat.POINTS,0);
        SeasonDb.setAll(SeasonDb.Stat.WINS,0);
        SeasonDb.setAll(SeasonDb.Stat.LOSSES,0);

        SeasonDb.add(map.get(1),SeasonDb.Stat.POINTS,50000);
        PlayerDb.add(map.get(1),PlayerDb.Stat.CHAMPIONSHIPS,1);
        PlayerDb.add(map.get(1),PlayerDb.Stat.SEASONS_GOLD,1);
        Player player = Partix.getPlayer(map.get(1));
        String name = PlayerDb.getName(map.get(1));
        Partix.getOnlinePlayers().forEach(p -> p.sendMessage(Message.seasonWin(name)));
        if (player != null && player.isOnline()) {
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1f, 1f);
        }
        PlayerDb.add(map.get(2),PlayerDb.Stat.SEASONS_GOLD,1);
        SeasonDb.add(map.get(2),SeasonDb.Stat.POINTS,50000);
        PlayerDb.add(map.get(6),PlayerDb.Stat.SEASONS_GOLD,1);
        SeasonDb.add(map.get(6),SeasonDb.Stat.POINTS,50000);
        player = Partix.getPlayer(map.get(6));
        if (player != null && player.isOnline()) {
            player.sendMessage(Message.seasonPromote());
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1f, 1f);
        }
        PlayerDb.add(map.get(7),PlayerDb.Stat.SEASONS_GOLD,1);
        SeasonDb.add(map.get(7),SeasonDb.Stat.POINTS,50000);
        player = Partix.getPlayer(map.get(7));
        if (player != null && player.isOnline()) {
            player.sendMessage(Message.seasonPromote());
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1f, 1f);
        }
        PlayerDb.add(map.get(8),PlayerDb.Stat.SEASONS_GOLD,1);
        SeasonDb.add(map.get(8),SeasonDb.Stat.POINTS,50000);
        player = Partix.getPlayer(map.get(8));
        if (player != null && player.isOnline()) {
            player.sendMessage(Message.seasonPromote());
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1f, 1f);
        }
        Partix.getInstance().getLogger().info("[!!!] Season Week Reset!");
    }

    public static String getTimeRemaining() {
        Calendar currentTime = Calendar.getInstance();
        long remainingMillis = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();

        if (remainingMillis <= 0) {
            return "Countdown Over";
        } else {
            long seconds = remainingMillis / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            // Calculate remainder for each unit
            seconds = seconds % 60;
            minutes = minutes % 60;
            hours = hours % 24;

            return String.format("%dd:%02dh:%02dm", days, hours, minutes);
        }
    }
}