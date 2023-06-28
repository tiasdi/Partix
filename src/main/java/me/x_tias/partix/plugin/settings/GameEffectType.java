package me.x_tias.partix.plugin.settings;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum GameEffectType {

    NONE(null),
    SPEED_1(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,0,true,false)),
    SPEED_2(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,1,true,false)),
    JUMP_1(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,0,true,false)),
    JUMP_2(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,1,true,false));



    private GameEffectType(PotionEffect effect) {
        this.effect = effect;
    }

    public final PotionEffect effect;
}
