package me.x_tias.partix.plugin.cosmetics;

import me.x_tias.partix.Partix;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class CosmeticParticle extends CosmeticHolder{

    public CosmeticParticle(String permission, Material gui, String name, CosmeticRarity rarity, ParticleSet... set) {
        this.permission = permission;
        this.gui = gui;
        this.rarity = rarity;
        if (set.length > 0) {
            this.particles = set;
        } else {
            this.particles = null;
        }
        this.name = name;
    }

    public void trail(Location l) {
        if (particles != null) {
            for (int i = 0; i < 3; i++) {
                ParticleSet random = particles[(new Random()).nextInt(particles.length)];
                if (random.getParticle() != null) {
                    if (random.getDustOptions() != null) {
                        l.getWorld().spawnParticle(random.getParticle(), l, 1, 0.125, 0.125, 0.125, 0, random.getDustOptions(), false);
                    } else {
                        l.getWorld().spawnParticle(random.getParticle(), l, 1, 0.125, 0.125, 0.125, 0, null, false);
                    }
                }
            }
        }
    }

    public void celebrate(Player player) {
        Location l = player.getLocation();
        if (particles != null) {
            for (int i = 0; i < 5; i++) {
                ParticleSet random = particles[(new Random()).nextInt(particles.length)];
                Particle.DustOptions dustOptions = new Particle.DustOptions(random.getDustOptions().getColor(),2f);
                if (random.getParticle() != null) {
                    if (random.getDustOptions() != null) {
                        l.getWorld().spawnParticle(random.getParticle(), l, 30, 1.5, 2.25, 1.5, 0, dustOptions, false);
                    } else {
                        l.getWorld().spawnParticle(random.getParticle(), l, 30, 1.5, 2.25, 1.5, 0, null, false);
                    }
                }
            }
        }
    }

    public void largeExplosion(Location l) {
        if (particles != null) {
            for (int i = 0; i < 7; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(Partix.getInstance(), () -> {
                    for (ParticleSet p : particles) {
                        if (p.getParticle() != null) {
                            double size = 1.0 + (0.85 * finalI) + (((Math.random()) - (Math.random())) / 2);
                            int count = (int) (((1 + size) * 7));
                            if (p.getDustOptions() != null) {
                                Particle.DustOptions dustOptions = new Particle.DustOptions(p.getDustOptions().getColor(),5f);
                                l.getWorld().spawnParticle(p.getParticle(), l, count, size, size, size, 0, dustOptions , false);
                            } else {
                                l.getWorld().spawnParticle(p.getParticle(), l, count, size, size, size, 0, null, false);
                            }
                        }
                    }
                }, Math.max(1,i * 3));
            }
        }
    }

    public void mediumExplosion(Location l) {
        if (particles != null) {
            for (int i = 0; i < 6; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(Partix.getInstance(), () -> {
                    for (ParticleSet p : particles) {
                        if (p.getParticle() != null) {
                            double size = 0.3 + (0.65 * finalI) + (((Math.random()) - (Math.random())) / 2);
                            int count = (int) (((1 + size) * 7));
                            if (p.getDustOptions() != null) {
                                Particle.DustOptions dustOptions = new Particle.DustOptions(p.getDustOptions().getColor(),5f);
                                l.getWorld().spawnParticle(p.getParticle(), l, count, size, size, size, 0, dustOptions , false);
                            } else {
                                l.getWorld().spawnParticle(p.getParticle(), l, count, size, size, size, 0, null, false);
                            }
                        }
                    }
                }, Math.max(1,i * 3));
            }
        }
    }

    public void smallExplosion(Location l) {
        if (particles != null) {
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(Partix.getInstance(), () -> {
                    for (ParticleSet p : particles) {
                        if (p.getParticle() != null) {
                            double size = 0.4 + (0.35 * finalI) + (((Math.random()) - (Math.random())) / 2);
                            int count = (int) (((1 + size) * 7));
                            if (p.getDustOptions() != null) {
                                Particle.DustOptions dustOptions = new Particle.DustOptions(p.getDustOptions().getColor(),5f);
                                l.getWorld().spawnParticle(p.getParticle(), l, count, size, size, size, 0, dustOptions , false);
                            } else {
                                l.getWorld().spawnParticle(p.getParticle(), l, count, size, size, size, 0, null, false);
                            }
                        }
                    }
                }, Math.max(1,i * 3));
            }
        }
    }

    public ParticleSet[] particles;
    private final Material gui;

    public ItemStack getGUIItem() {
        ItemStack itemStack = new ItemStack(gui);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Component colors = Component.empty();
        if (particles != null) {
            for (ParticleSet set : particles) {
                if (set != null && set.getParticle() != null) {
                    if (set.getDustOptions() != null) {
                        colors = colors.append(Component.text("■").color(TextColor.color(set.getDustOptions().getColor().asRGB())));
                    } else {
                        if (set.getParticle().equals(Particle.TOTEM)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0x9dff5d));
                        } else if (set.getParticle().equals(Particle.SOUL_FIRE_FLAME)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0x5dfffa));
                        } else if (set.getParticle().equals(Particle.FLAME)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0xfcb841));
                        } else if (set.getParticle().equals(Particle.ENCHANTMENT_TABLE)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0xFFDDFA));
                        } else if (set.getParticle().equals(Particle.NAUTILUS)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0x294693));
                        } else if (set.getParticle().equals(Particle.NOTE)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0xDAE00));
                        } else if (set.getParticle().equals(Particle.HEART)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0xC32400));
                        } else if (set.getParticle().equals(Particle.SOUL)) {
                            colors = colors.append(Component.text("♦")).color(TextColor.color(0x4B2D07));
                        }
                    }
                }
            }
        }
        itemMeta.displayName(Component.text(rarity.getTitle()+" "+name));
        itemMeta.lore(List.of(
                Component.text("§r§8Cosmetic").color(Colour.border()),
                Component.text("   "),
                Component.text("§r§7Particles (").append(colors).append(Component.text("§7)")),
                Component.text("§r§ePrice: §6"+rarity.getCost()+" Coins")
        ));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
