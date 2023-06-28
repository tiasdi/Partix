package me.x_tias.partix.plugin.ball.event;

import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import org.bukkit.FluidCollisionMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

public class PressSwapKeyEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private final ItemStack item;
    private final boolean thrownInBlock;

    public PressSwapKeyEvent(Player player, ItemStack item) {
        this.player = player;
        this.item = item;

        boolean isInBlock;
        try {
            RayTraceResult result = player.getWorld().rayTraceBlocks(player.getEyeLocation(),player.getLocation().getDirection(),0.5, FluidCollisionMode.NEVER, false);
            if (result != null) {
                isInBlock = result.getHitBlock() != null;
            } else {
                isInBlock = false;
            }
        } catch (Exception ex) {
            isInBlock = false;
        }
        thrownInBlock = isInBlock;

    }

    public boolean isThrownInBlock() {
        return thrownInBlock;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public Athlete getAthlete() {
        return AthleteManager.get(player.getUniqueId());
    }

}
