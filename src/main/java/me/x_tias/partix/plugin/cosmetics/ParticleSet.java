package me.x_tias.partix.plugin.cosmetics;

import org.bukkit.Color;
import org.bukkit.Particle;

public class ParticleSet {

    public ParticleSet(Particle particle, Color color) {
        this.particle = particle;
        this.dustOptions = new Particle.DustOptions(color,1f);
    }

    public ParticleSet(Particle particle) {
        this.particle = particle;
        this.dustOptions = null;
    }

    private final Particle particle;
    private final Particle.DustOptions dustOptions;

    public Particle getParticle() {
        return particle;
    }

    public Particle.DustOptions getDustOptions() {
        return dustOptions;
    }

    public static ParticleSet of(Particle particle, Color color) {
        return new ParticleSet(particle,color);
    }

    public static ParticleSet of(Particle particle) {
        return new ParticleSet(particle);
    }

    public static ParticleSet empty() {
        return new ParticleSet(null);
    }

}
