package me.x_tias.partix.plugin.ball.event;

import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerHitBallEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private final Ball ball;
    private final Location hitLocation;

    public PlayerHitBallEvent(Player player, Ball ball, Location hitLocation) {
        this.ball = ball;
        this.hitLocation = hitLocation;
        this.player = player;
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


    public Ball getBall() {
        return ball;
    }

    public Location getHitLocation() {
        return hitLocation;
    }
}
