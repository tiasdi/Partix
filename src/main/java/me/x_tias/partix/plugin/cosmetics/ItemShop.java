package me.x_tias.partix.plugin.cosmetics;

import me.x_tias.partix.Partix;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ItemShop implements Runnable {

    private static SimpleDateFormat dateFormat;
    private static Calendar targetTime;

    private static String timeRemaining;

    public static void setup() {
        // Set the target time to 8:00 PM EST
        targetTime = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        targetTime.set(Calendar.HOUR_OF_DAY, 20); // 8:00 PM
        targetTime.set(Calendar.MINUTE, 0); // 0
        targetTime.set(Calendar.SECOND, 0); // 0
        targetTime.set(Calendar.MILLISECOND, 0); // 0

        refreshItems();

        // If we're already past today at 8pm, move to next day
        Calendar currentTime = Calendar.getInstance();
        if (currentTime.after(targetTime)) {
            targetTime.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Calculate the delay in ticks (1 tick = 1/20th of a second)
        long delay = (targetTime.getTimeInMillis() - currentTime.getTimeInMillis()) / 50;

        // Start the countdown task
        Bukkit.getServer().getScheduler().runTaskTimer(Partix.getInstance(), new ItemShop(), delay, 20L*60*60*24); // Run every day

        // Initialize date format
        dateFormat = new SimpleDateFormat("dd'd:'HH'h:'mm'm'");
        dateFormat.setTimeZone(targetTime.getTimeZone());
    }

    private static void refreshItems() {
        defaultBorder = Cosmetics.randomBorder();
        defaultExplosion = Cosmetics.randomExplosion();
        defaultTrail = Cosmetics.randomTrail();
        vipBorder = Cosmetics.randomBorder();
        vipExplosion = Cosmetics.randomExplosion();
        vipTrail = Cosmetics.randomTrail();
        proBorder = Cosmetics.getProRandom();
        proExplosion = Cosmetics.getProRandom();
        proTrail = Cosmetics.getProRandom();
    }


    @Override
    public void run() {
        Calendar currentTime = Calendar.getInstance();
        if (currentTime.after(targetTime)) {
            // The target time has passed, move to next day
            targetTime.add(Calendar.DAY_OF_YEAR, 1); // (1)
            targetTime.set(Calendar.HOUR_OF_DAY, 20); // 8:00 PM
            targetTime.set(Calendar.MINUTE, 0); // 0
            targetTime.set(Calendar.SECOND, 0); // 0
            targetTime.set(Calendar.MILLISECOND, 0); //0
            endDay(); // Trigger end of day
        }

        // Calculate the remaining time
        long remainingMillis = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();
        if (remainingMillis <= 0) {
            // The countdown has reached 0, do something here if needed
            return;
        }

        timeRemaining = dateFormat.format(new Date(remainingMillis));
    }

    private static void endDay() {
        refreshItems();
        Partix.getInstance().getLogger().info("[!!!] Item Shop Reset!");
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

    public static CosmeticHolder defaultTrail;
    public static CosmeticHolder defaultBorder;
    public static CosmeticHolder defaultExplosion;
    public static CosmeticHolder vipTrail;
    public static CosmeticHolder vipBorder;
    public static CosmeticHolder vipExplosion;
    public static CosmeticHolder proTrail;
    public static CosmeticHolder proBorder;
    public static CosmeticHolder proExplosion;


}
