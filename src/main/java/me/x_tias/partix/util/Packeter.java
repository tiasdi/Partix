package me.x_tias.partix.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class Packeter {

    public static PacketContainer spawnEntity(int entityId, Location location, EntityType type) {

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);

        // Crucial data, do not remove
        packet.getIntegers().write(0, entityId);
        packet.getUUIDs().write(0, UUID.randomUUID());
        packet.getEntityTypeModifier().write(0, type);

        packet.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        return packet;
    }

    public static PacketContainer removeEntity(int entityId) {
        final PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getModifier().write(0, new IntArrayList(new int[]{entityId}));
        return packet;
    }

    public static PacketContainer moveEntity(int entityId, Location dest) {
        final PacketContainer packet = new PacketContainer(PacketType.Play.Server.REL_ENTITY_MOVE);

        packet.getIntegers().write(0,entityId);
        packet.getDoubles().write(0, dest.getX()).write(1, dest.getY()).write(2, dest.getZ());

        return packet;
    }


}
