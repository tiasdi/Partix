package me.x_tias.partix.plugin.ball.event;

import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BallHitBlockEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private final Ball ball;
    private final BallType ballType;
    private final Block block;
    private final BlockFace blockFace;
    private final Location hitLocation;

    public BallHitBlockEvent(Ball ball, BallType ballType, Block block, BlockFace blockFace, Location hitLocation) {
        this.ball = ball;
        this.ballType = ballType;
        this.block = block;
        this.hitLocation = hitLocation;
        this.blockFace = blockFace;
        this.isCancelled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public Block getBlock() {
        return block;
    }

    public BallType getBallType() {
        return ballType;
    }

    public Ball getBall() {
        return ball;
    }

    public Location getHitLocation() {
        return hitLocation;
    }
}
