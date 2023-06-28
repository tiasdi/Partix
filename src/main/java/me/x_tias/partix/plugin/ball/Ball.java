package me.x_tias.partix.plugin.ball;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.x_tias.partix.Partix;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.athlete.RenderType;
import me.x_tias.partix.plugin.ball.event.BallHitBlockEvent;
import me.x_tias.partix.plugin.ball.event.BallHitEntityEvent;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Packeter;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public abstract class Ball {

    public final boolean debug = true;
    public final boolean slime = false;
    public int packetId;
    private Slime entity;

    private Color primary;
    private Color secondary;
    private final UUID uuid;
    private Location location;
    private final World world;
    private int repulseDelay;
    private int stealDelay;
    private final double stealBallDistance;
    private Vector velocity = new Vector(0.0, 0.1, 0.0);
    private final double friction; // affects how much the ball slides
    private final double weight; // affects how high the ball bounces off the ground
    private final double gravity; // affects the ball dropping
    private final double repulsion; // affects how hard the ball bounces off of a wall
    private final double balance; // y velocity needed to start rolling
    private final boolean maintainBounceY; // maintain current y velocity when bouncing
    private final boolean maintainBounceX; // whether full horizontal velocity is affected when bouncing off wall
    private final Vector dimensions;
    private Player lastDamager;
    private Player currentDamager;
    private final double hitbox;
    private final BallType ballType;

    public Ball(Location location, BallType ballType, double hitbox, double width, double height, double friction, double gravity, double repulsion, double balance, double weight, boolean maintainBounceY, boolean maintainBounceX, double stealBallDistance, Color primary, Color secondary) {
        this.location = location;
        this.hitbox = hitbox;
        this.world = location.getWorld();
        this.friction = friction;
        this.weight = weight;
        this.repulsion = repulsion;
        this.stealBallDistance = stealBallDistance;
        this.gravity = gravity;
        this.primary = primary;
        this.uuid = UUID.randomUUID();
        this.secondary = secondary;
        this.balance = balance;
        this.packetId = -1;
        this.ballType = ballType;
        this.repulseDelay = 0;
        this.stealDelay = 5;
        this.maintainBounceY = maintainBounceY;
        this.maintainBounceX = maintainBounceX;
        this.lastDamager = null;
        this.currentDamager = null;
        this.dimensions = new Vector(width / 2, height, width / 2);
        if (slime) {
            summonEntity();
        }
    }

    private void summonEntity() {
        entity = (Slime) world.spawnEntity(location, EntityType.SLIME);
        entity.setGravity(false);
        entity.setAI(false);
        entity.setCollidable(false);
        entity.setSize(1);
        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.playEffect(EntityEffect.HURT);
    }

    private void removeEntity() {
        entity.remove();
        entity = null;
    }


    public double getStealBallDistance() {
        return stealBallDistance;
    }

    public double getFriction() {
        return friction;
    }

    public double getGravity() {
        return gravity;
    }

    public double getBalance() {
        return balance;
    }

    public BallType getBallType() {
        return ballType;
    }

    public void changeColors(Color p, Color s) {
        primary = p;
        secondary = s;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Player getLastDamager() {
        return lastDamager;
    }

    public int getRepulseDelay() { return repulseDelay;}

    public void setVelocity(double x, double y, double z) {
        velocity.setX(x);
        velocity.setY(y);
        velocity.setZ(z);
    }

    public void setVelocity(Player player, double x, double y, double z) {
        lastDamager = player;
        setCurrentDamager(player);
        velocity.setX(x);
        velocity.setY(y);
        velocity.setZ(z);
    }

    private void setCurrentDamager(Player player) {
        if (stealDelay < 1) {
            stealDelay = 10;
            if (currentDamager != null) {
                currentDamager.getInventory().setItem(1, null);
            }
            currentDamager = player;
            player.getInventory().setHeldItemSlot(0);
            player.getInventory().setItem(0, Items.get(Component.text("play.partix.net").color(Colour.partix()), Material.POLISHED_BLACKSTONE_BUTTON));
        }
    }

    public double getHitboxSize() {
        return hitbox;
    }

    public void setVelocity(Vector vector) {
        velocity = vector;
    }

    public void setVelocity(Player player, Vector vector) {
        lastDamager = player;
        setCurrentDamager(player);
        velocity = vector;
    }

    public void setHorizontal(Vector vector) {
        velocity.setX(vector.getX());
        velocity.setZ(vector.getZ());
    }

    public void setHorizontal(Location l) {
        location.setX(l.getX());
        location.setZ(l.getZ());
    }

    public void setHorizontal(Player player, Vector vector) {
        lastDamager = player;
        setCurrentDamager(player);
        velocity.setX(vector.getX());
        velocity.setZ(vector.getZ());
    }


    public void setVertical(Vector vector) {
        velocity.setY(vector.getY());
    }

    public void setVertical(double v) {
        velocity.setY(v);
    }

    public Player getCurrentDamager() {
        return currentDamager;
    }

    public void setVertical(Player player, Vector vector) {
        lastDamager = player;
        setCurrentDamager(player);
        velocity.setY(vector.getY());
    }

    public void setVelocity(Vector horizontal, Vector vertical) {
        setHorizontal(horizontal);
        setVertical(vertical);
    }

    public void setVelocity(Player player, Vector horizontal, Vector vertical) {
        lastDamager = player;
        setCurrentDamager(player);
        setHorizontal(horizontal);
        setVertical(vertical);
    }

    public void setVelocity(Vector horizontal, double vertical) {
        setHorizontal(horizontal);
        velocity.setY(vertical);
    }

    public void setVelocity(Player player, Vector horizontal, double vertical) {
        lastDamager = player;
        setCurrentDamager(player);
        setHorizontal(horizontal);
        velocity.setY(vertical);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getSpeed() {
        return Math.abs(velocity.getX()) + Math.abs(velocity.getY()) + Math.abs(velocity.getZ());
    }

    public int getStealDelay() {
        return stealDelay;
    }

    public void setStealDelay(int n) {
        stealDelay = n;
    }

    public Vector getDimensions() {
        return dimensions;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void setDamager(Player player) {
        if (lastDamager != null) {
            lastDamager.getInventory().setItem(0,null);
        }
        lastDamager = player;
        setCurrentDamager(player);
    }

    public void removeCurrentDamager() {
        if (currentDamager != null) {
            currentDamager.getInventory().setItem(0, null);
        }
        currentDamager = null;
    }

    private void onTick() {
        if (repulseDelay > 0) {
            repulseDelay -= 1;
        }
        if (stealDelay > 0) {
            stealDelay -= 1;
        }
    }

    public void move() {
        onTick();
        Location previous = location.clone();
        physics();
        Location next = location.clone().add(velocity);
        if (previous.distance(next) > 0.0) {
            RayTraceResult result = world.rayTrace(location, velocity, (previous.distance(next)) + dimensions.getX(), FluidCollisionMode.ALWAYS, false, 0.05+dimensions.getX(), Objects::nonNull);
            if (result == null) {
                // hits nothing
                endPhysics(next);
                return;
            }
            if (repulseDelay < 1) {
                if (result.getHitEntity() != null) {
                    // hits entity
                    if (!(result.getHitEntity() instanceof Player player && (AthleteManager.get(player.getUniqueId()).isSpectating()))) {
                        boolean cont = hitEntity(result.getHitPosition().toLocation(world), result.getHitEntity());
                        if (cont) {
                            endPhysics(next);
                        } else {
                            spawn();
                        }
                        return;
                    }
                }
            }

            if (result.getHitBlock() != null && result.getHitBlockFace() != null) {
                // hits block
                if (!(result.getHitBlock().getType().equals(Material.LIGHT))) {
                    hitBlock(result.getHitPosition().toLocation(world), result.getHitBlock(), result.getHitBlockFace());
                    spawn();
                    return;
                } else {
                    endPhysics(next);
                    return;
                }
            }
        }
        endPhysics(next);
        // remove if far away
        if (location.getNearbyPlayers(250).size() == 0) {
            remove();
        }
    }


    private void endPhysics(Location next) {
        location = next;
        spawn();
    }

    private boolean hitEntity(Location hitLocation, Entity entity) {

        if (currentDamager == null || entity != currentDamager) {
            if (entity instanceof Player player) {
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    return true;
                }
            }
            BallHitEntityEvent event = new BallHitEntityEvent(this, ballType, entity, hitLocation);
            Partix.getInstance().getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                Location main = entity.getLocation().clone();
                main.setPitch(0f);
                main.setYaw(main.getYaw() + (float)((Math.random() * 7)-(Math.random() * 7)));
                velocity = main.getDirection().multiply(1.0);
                repulseDelay = 5;
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void hitBlock(Location hitLocation, Block block, BlockFace blockFace) {
        BallHitBlockEvent event = new BallHitBlockEvent(this,ballType,block,blockFace,hitLocation);
        Partix.getInstance().getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            Vector normal = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
            location = hitLocation.add(0.0, dimensions.getY(), 0.0);
            velocity = velocity.subtract(normal.multiply(2 * velocity.dot(normal)));

            BoundingBox box = block.getBoundingBox();

            if (box.contains(location.toVector())) {
                if (normal.getX() != 0) {
                    location.setX(normal.getX() > 0 ? box.getMinX() - dimensions.getX() / 2 : box.getMaxX() + dimensions.getX() / 2);
                }
                if (normal.getY() != 0) {
                    location.setY(normal.getY() > 0 ? box.getMinY() - dimensions.getY() / 2 : box.getMaxY() + dimensions.getY() / 2);
                }
                if (normal.getZ() != 0) {
                    location.setZ(normal.getZ() > 0 ? box.getMinZ() - dimensions.getZ() / 2 : box.getMaxZ() + dimensions.getZ() / 2);
                }
            }

            if (blockFace.equals(BlockFace.UP) && !block.getType().equals(Material.BARRIER)) {
                bounce();
            } else {
                repulse(blockFace, block.getType());
            }
        }
    }

    private void bounce() {
        double y = velocity.getY();
        y -= (y * weight);
        if (y < balance) {
            velocity.setY(0.0);
        } else {
            velocity.setY(y);
        }
    }

    private void repulse(BlockFace blockFace, Material material) {
        double rep;
        double y;
        if (material.equals(Material.BARRIER)) {
            y = -0.075;
            rep = Math.abs(repulsion + Math.random());
            repulseDelay = 12;
        } else {
            y = -0.075;
            rep = Math.abs(repulsion);
            repulseDelay = 5;
        }
        switch (blockFace) {
            case NORTH, NORTH_EAST, NORTH_NORTH_EAST, NORTH_NORTH_WEST, NORTH_WEST, SOUTH, SOUTH_EAST, SOUTH_WEST, SOUTH_SOUTH_EAST, SOUTH_SOUTH_WEST -> {
                double z = velocity.getZ();
                z -= (z * (1 - rep));
                velocity.setZ(z);
                if (maintainBounceX) {
                    double x = velocity.getX();
                    x -= (x * (1 - rep));
                    velocity.setX(x);
                }
                if (!maintainBounceY) {
                    velocity.setY(y);
                }
                return;
            }
            case WEST, WEST_NORTH_WEST, WEST_SOUTH_WEST, EAST, EAST_NORTH_EAST, EAST_SOUTH_EAST -> {
                double x = velocity.getX();
                x -= (x * (1 - repulsion));
                velocity.setX(x);
                if (maintainBounceX) {
                    double z = velocity.getZ();
                    z -= (z * (1 - repulsion));
                    velocity.setZ(z);
                }
                if (!maintainBounceY) {
                    velocity.setY(y);
                }
                return;
            }
            default -> {
                velocity.setY(-0.15);
                velocity.setX(velocity.getX() * -1);
                velocity.setZ(velocity.getZ() * -1);
                return;
            }
        }


    }

    private void physics() {
        if (isRolling()) {
            rolling();
            fall();
        } else {
            moving();
            gravity();
        }
        fixVelocity();
    }

    private void rolling() {
        double x = velocity.getX();
        double z = velocity.getZ();
        x -= (x * friction);
        z -= (z * friction);
        velocity.setX(x);
        velocity.setY(0);
        velocity.setZ(z);
    }

    private void moving() {
        double x = velocity.getX();
        double z = velocity.getZ();
        x -= (x * friction);
        z -= (z * friction);
        velocity.setX(Math.min(0.8,x));
        velocity.setZ(Math.min(0.8,z));
    }


    public void sendDebug(String message) {
        if (debug) {
            location.getNearbyPlayers(15).forEach(player -> player.sendMessage("[DEBUG] " + message));
        }
    }

    private void gravity() {
        double y = velocity.getY();
        if (Math.abs(y) < 0.05) {
            fall();
        }
        if (Math.abs(y) > 0.01) {
            if (y > 0) {
                if (y < 0.018) {
                    y = -0.018;
                } else {
                    y -= (y * gravity) + 0.01;
                }
            } else {
                y += (y * gravity) - 0.01;
                y = Math.max(y, -0.75);
            }
            velocity.setY(y);
        }
    }

    private void fall() {
        if (getBlockBelow().getType().equals(Material.AIR) || getBlockBelow().getType().equals(Material.LIGHT)) {
            velocity.setY(Math.min(Math.abs(velocity.getY())*-1,-0.025));
        }
    }

    private void fixVelocity() {
        if (Math.abs(velocity.getX()) < 0.0075) {
            velocity.setX(0);
        }
        if (Math.abs(velocity.getY()) < 0.0075) {
            velocity.setY(0);
        }
        if (Math.abs(velocity.getZ()) < 0.0075) {
            velocity.setZ(0);
        }
    }

    public Block getBlockBelow() {
        return location.clone().subtract(0.0,dimensions.getY(),0.0).getBlock();
    }


    public boolean isRolling() {
        return ((Math.abs(velocity.getX())+(Math.abs(velocity.getY())) > 0 && Math.abs(velocity.getY()) < 0.05)) && !getBlockBelow().getType().equals(Material.AIR) && !getBlockBelow().getType().equals(Material.LIGHT);
    }


    public void remove() {
        BallFactory.remove(this);
        if (slime) {
            removeEntity();
        }
    }

    private void spawn() {
        modify();
        if (getCurrentDamager() != null) {
            getCurrentDamager().sendActionBar(getControls(getCurrentDamager()));
        }

        PacketContainer remove = null;

        if (packetId > 0) {
            remove = Packeter.removeEntity(packetId);
        }

        packetId = 100000 + new Random().nextInt(899999);
        PacketContainer spawn = Packeter.spawnEntity(packetId,location,EntityType.SLIME);

        for (Player player : location.getNearbyPlayers(100)) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            if (athlete.getRenderType().equals(RenderType.SLIME)) {
                if (remove != null) {
                    sendPacket(player, remove);
                }
                sendPacket(player, spawn);
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), 1+ (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10)) * 20))/30, dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(primary, Color.GRAY, 0.5f));
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), 1+ (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10))) * 10)/30, dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(secondary, Color.GRAY, 0.5f));
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), 1+(int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10))) * 20)/30, dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(primary, Color.GRAY, 0.5f));
            } else if (athlete.getRenderType().equals(RenderType.REMOVE_SLIME)) {
                if (remove != null) {
                    sendPacket(player,remove);
                }
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), 1+ (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10)) * 20))/10, dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(primary, Color.GRAY, 0.5f));
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), 1+ (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10))) * 10)/10, dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(secondary, Color.GRAY, 0.5f));
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), 1+(int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10))) * 20)/10, dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(primary, Color.GRAY, 0.5f));
            } else {
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10)) * 20)), dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(primary, Color.GRAY, 0.5f));
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10))) * 10), dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(secondary, Color.GRAY, 0.5f));
                player.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() + (dimensions.getY() / 2), location.getZ(), (int) (1 + (((dimensions.getX() * 10) * (dimensions.getY() * 10))) * 20), dimensions.getX(), dimensions.getY() / 2, dimensions.getZ(), 0, new Particle.DustTransition(primary, Color.GRAY, 0.5f));
            }
        }

    }


    private void sendPacket(Player player, PacketContainer packet) {
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }

    public abstract void modify();

    public abstract Component getControls(Player player);



}
