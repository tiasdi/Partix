package me.x_tias.partix.plugin.cosmetics;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Cosmetics {

    public static void setup() {
        setupTrails();
        setupBorders();
        setupExplosions();
        setupWinSongs();
    }

    private static void setupTrails() {
        trails = new HashMap<>();
        trails.put(0,new CosmeticParticle("default", Material.BARRIER, "§r§cNo Trail", CosmeticRarity.COMMON, ParticleSet.empty()));
        trails.put(1,new CosmeticParticle("trail.red", Material.RED_WOOL, "§r§fRed Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.RED)));
        trails.put(2,new CosmeticParticle("trail.blue", Material.BLUE_WOOL, "§r§fBlue Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.BLUE)));
        trails.put(3,new CosmeticParticle("trail.yellow", Material.YELLOW_WOOL, "§r§fYellow Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.YELLOW)));
        trails.put(4,new CosmeticParticle("trail.green", Material.GREEN_WOOL, "§r§fGreen Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.GREEN)));
        trails.put(5,new CosmeticParticle("trail.purple", Material.PURPLE_WOOL, "§r§fPurple Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.PURPLE)));
        trails.put(6,new CosmeticParticle("trail.lime", Material.LIME_WOOL, "§r§fLime Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.LIME)));
        trails.put(7,new CosmeticParticle("trail.teal", Material.CYAN_WOOL, "§r§fTeal Trail", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.TEAL)));
        trails.put(8,new CosmeticParticle("trail.infection", Material.RED_GLAZED_TERRACOTTA, "§r§fInfection Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x7a1211)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xacada7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x5a5b55)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x2b3029)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x151611))));
        trails.put(9,new CosmeticParticle("trail.summer_sky", Material.ORANGE_GLAZED_TERRACOTTA, "§r§fSummer Sky Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffaf87)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff8e72)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xed6a5e)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x4ce0b3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x377771))));
        trails.put(10,new CosmeticParticle("trail.fallen_blue", Material.BLUE_CONCRETE_POWDER, "§r§fFallen Blue Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xf6f6f6)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xd9e9f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x55bcdf)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x273b79)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x14212a))));
        trails.put(11,new CosmeticParticle("trail.blood_gold", Material.CRIMSON_STEM, "§r§fBlood Gold Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x660000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x800000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x880303)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xedc967)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xd4af37))));
        trails.put(12,new CosmeticParticle("trail.coffee", Material.STRIPPED_OAK_LOG, "§r§fCoffee Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xa37437)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xba9750)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xd5b57c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfff2da)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfdf8ed))));
        trails.put(13,new CosmeticParticle("trail.twilight", Material.PURPLE_CONCRETE_POWDER, "§r§fTwilight Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x1e093c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x662f89)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x8d5da4)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xccb2d3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffffff))));
        trails.put(14,new CosmeticParticle("trail.steve", Material.WHITE_TERRACOTTA, "§r§fSteve Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfee0cc)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x612900)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x118f7a)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x084a96)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x030303))));
        trails.put(15,new CosmeticParticle("rank.pro", Material.LIGHT_BLUE_CONCRETE, "§r§fWarm Cyan Trail§6 (PRO)", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xebfbfa)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc5f5f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x9fefe7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x79e9de)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x53e3d4))));
        trails.put(16,new CosmeticParticle("trail.eucalyptus", Material.OAK_LEAVES, "§r§fEucalyptus Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xe6f0e7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc0d4c3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xabc5ae)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x547c65)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x0c4524))));
        trails.put(17,new CosmeticParticle("trail.midnight", Material.BLACK_CONCRETE_POWDER, "§r§fMidnight Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x0e0e47)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x181959)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x270d39)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x331547)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x170428))));
        trails.put(18,new CosmeticParticle("trail.orange_soda", Material.ORANGE_CONCRETE_POWDER, "§r§fOrange Soda Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xed3101)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff8036)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff9965)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x38ffb5)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x44bb68))));
        trails.put(19,new CosmeticParticle("trail.evening_flame", Material.PURPLE_GLAZED_TERRACOTTA, "§r§fEvening Flame Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x291f4a)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x352244)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x2b2b4f)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xef9c4e)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffd361))));
        trails.put(20,new CosmeticParticle("trail.mango", Material.STRIPPED_ACACIA_WOOD, "§r§fMango Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfd1502)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfd4f02)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfd9502)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfdbe02)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfdd402))));
        trails.put(21,new CosmeticParticle("trail.kraken", Material.LAPIS_BLOCK, "§r§fDeep Sea Kraken Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000033)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x003333)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x006633)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x009933)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x00cc33))));
        trails.put(22,new CosmeticParticle("trail.dragon_fruit", Material.PINK_GLAZED_TERRACOTTA, "§r§fDragon Fruit Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xcc3399)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xcc9999)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x00FFFF)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x00FFFF))));
        trails.put(23,new CosmeticParticle("trail.orb", Material.EXPERIENCE_BOTTLE, "§r§fOrb Trail", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.TOTEM)));
        trails.put(24,new CosmeticParticle("trail.blue_flame", Material.SOUL_CAMPFIRE, "§r§fBlue Flame Trail", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.SOUL_FIRE_FLAME)));
        trails.put(25,new CosmeticParticle("trail.flame", Material.CAMPFIRE, "§r§fFlame Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.FLAME)));
        trails.put(26,new CosmeticParticle("trail.vice", Material.PINK_GLAZED_TERRACOTTA, "§r§fVice Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x67c7f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x67c7f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xf890e7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xf890e7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffffff))));
        trails.put(27,new CosmeticParticle("trail.camouflage", Material.DARK_OAK_LEAVES, "§r§fCamouflage Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x3b231c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x875730)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xaea398)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x34431c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x6d8455))));
        trails.put(28,new CosmeticParticle("trail.halloween", Material.JACK_O_LANTERN, "§r§fHalloween Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff9a00)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff9a00)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x09ff00)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc900ff))));
        trails.put(29,new CosmeticParticle("trail.christmas", Material.CHEST, "§r§fChristmas Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x127419)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x127419)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc73636)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc73636)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffffff))));
        trails.put(30,new CosmeticParticle("trail.ender", Material.ENDER_CHEST, "§r§fEnder Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xcb59ff)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xaa1bab)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xed8cff)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000000))));
        trails.put(31,new CosmeticParticle("trail.nether", Material.WARPED_STEM, "§r§fNether Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x511515)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x723232)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xac2020)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfe8738)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x119b84))));
        trails.put(32,new CosmeticParticle("trail.raspberry", Material.WARPED_STEM, "§r§fRaspberry Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x8A307F)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x79A7D3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x6883BC))));
        trails.put(33,new CosmeticParticle("trail.pastel", Material.LIME_TERRACOTTA, "§r§fPastel Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xAA96DA)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xC5FAD5)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xFFFFD2))));
        trails.put(34,new CosmeticParticle("rank.pro", Material.LIME_CONCRETE_POWDER, "§r§fNursery Trail§6 (PRO)", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xFFE77A)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x2C5F2D))));
        trails.put(35,new CosmeticParticle("trail.royal", Material.BLUE_CONCRETE, "§r§fRoyal Trail", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x234E70)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xFBF8BE))));
        trails.put(36,new CosmeticParticle("rank.pro", Material.PURPLE_TERRACOTTA , "§r§fMaroon Blues Trail§6 (PRO)", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x408EC6)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x7A2048)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x1E2761))));
        trails.put(37,new CosmeticParticle("trail.maturity", Material.ORANGE_TERRACOTTA , "§r§fMaturity Trail", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xB85042)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xE7E8D1)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xA7BEAE))));
        trails.put(38,new CosmeticParticle("trail.heart", Material.APPLE, "§r§fHeart Trail", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.HEART)));
        trails.put(39,new CosmeticParticle("trail.soul", Material.SOUL_SAND, "§r§fSoul Trail", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.SOUL),ParticleSet.of(Particle.SCULK_SOUL)));
        trails.put(40,new CosmeticParticle("trail.spell", Material.SOUL_SAND, "§r§fSpell Trail", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.ENCHANTMENT_TABLE),ParticleSet.of(Particle.ENCHANTMENT_TABLE),ParticleSet.of(Particle.TOTEM)));
    }

    private static void setupExplosions() {
        explosions = new HashMap<>();
        explosions.put(0,new CosmeticParticle("default", Material.BARRIER, "§r§cNo Explosion", CosmeticRarity.COMMON, ParticleSet.empty()));
        explosions.put(1,new CosmeticParticle("explosion.red", Material.RED_WOOL, "§r§eRed Explosion", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.RED)));
        explosions.put(2,new CosmeticParticle("explosion.blue", Material.BLUE_WOOL, "§r§eBlue Explosion", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.BLUE)));
        explosions.put(3,new CosmeticParticle("explosion.yellow", Material.YELLOW_WOOL, "§r§eYellow Explosion", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.YELLOW)));
        explosions.put(4,new CosmeticParticle("explosion.green", Material.GREEN_WOOL, "§r§eGreen Explosion", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.GREEN)));
        explosions.put(5,new CosmeticParticle("explosion.purple", Material.PURPLE_WOOL, "§r§ePurple Explosion", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.PURPLE)));
        explosions.put(6,new CosmeticParticle("explosion.lime", Material.LIME_WOOL, "§r§eLime Explosion", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.LIME)));
        explosions.put(7,new CosmeticParticle("rank.pro", Material.CYAN_WOOL, "§r§eTeal Explosion§6 (PRO)", CosmeticRarity.COMMON, ParticleSet.of(Particle.REDSTONE, Color.TEAL)));
        explosions.put(8,new CosmeticParticle("explosion.infection", Material.RED_GLAZED_TERRACOTTA, "§r§eInfection Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x7a1211)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xacada7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x5a5b55)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x2b3029)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x151611))));
        explosions.put(9,new CosmeticParticle("explosion.summer_sky", Material.ORANGE_GLAZED_TERRACOTTA, "§r§eSummer Sky Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffaf87)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff8e72)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xed6a5e)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x4ce0b3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x377771))));
        explosions.put(10,new CosmeticParticle("explosion.fallen_blue", Material.BLUE_CONCRETE_POWDER, "§r§eFallen Blue Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xf6f6f6)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xd9e9f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x55bcdf)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x273b79)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x14212a))));
        explosions.put(11,new CosmeticParticle("explosion.blood_gold", Material.CRIMSON_STEM, "§r§eBlood Gold Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x660000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x800000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x880303)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xedc967)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xd4af37))));
        explosions.put(12,new CosmeticParticle("explosion.coffee", Material.STRIPPED_OAK_LOG, "§r§eCoffee Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xa37437)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xba9750)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xd5b57c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfff2da)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfdf8ed))));
        explosions.put(13,new CosmeticParticle("explosion.twilight", Material.PURPLE_CONCRETE_POWDER, "§r§eTwilight Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x1e093c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x662f89)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x8d5da4)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xccb2d3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffffff))));
        explosions.put(14,new CosmeticParticle("explosion.steve", Material.WHITE_TERRACOTTA, "§r§eSteve Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfee0cc)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x612900)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x118f7a)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x084a96)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x030303))));
        explosions.put(15,new CosmeticParticle("rank.pro", Material.LIGHT_BLUE_CONCRETE, "§r§eWarm Cyan Explosion§6 (PRO)", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xebfbfa)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc5f5f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x9fefe7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x79e9de)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x53e3d4))));
        explosions.put(16,new CosmeticParticle("explosion.eucalyptus", Material.OAK_LEAVES, "§r§eEucalyptus Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xe6f0e7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc0d4c3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xabc5ae)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x547c65)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x0c4524))));
        explosions.put(17,new CosmeticParticle("explosion.midnight", Material.BLACK_CONCRETE_POWDER, "§r§eMidnight Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x0e0e47)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x181959)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x270d39)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x331547)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x170428))));
        explosions.put(18,new CosmeticParticle("explosion.orange_soda", Material.ORANGE_CONCRETE_POWDER, "§r§eOrange Soda Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xed3101)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff8036)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff9965)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x38ffb5)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x44bb68))));
        explosions.put(19,new CosmeticParticle("rank.pro", Material.PURPLE_GLAZED_TERRACOTTA, "§r§eEvening Flame Explosion§6 (PRO)", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x291f4a)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x352244)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x2b2b4f)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xef9c4e)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffd361))));
        explosions.put(20,new CosmeticParticle("explosion.mango", Material.STRIPPED_ACACIA_WOOD, "§r§eMango Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfd1502)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfd4f02)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfd9502)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfdbe02)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfdd402))));
        explosions.put(21,new CosmeticParticle("rank.pro", Material.LAPIS_BLOCK, "§r§eDeep Sea Kraken Explosion§6 (PRO)", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000033)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x003333)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x006633)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x009933)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x00cc33))));
        explosions.put(22,new CosmeticParticle("explosion.dragon_fruit", Material.PINK_GLAZED_TERRACOTTA, "§r§eDragon Fruit Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xcc3399)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xcc9999)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x00FFFF)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x00FFFF))));
        explosions.put(23,new CosmeticParticle("explosion.orb", Material.EXPERIENCE_BOTTLE, "§r§eOrb Explosion", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.TOTEM)));
        explosions.put(24,new CosmeticParticle("explosion.blue_flame", Material.SOUL_CAMPFIRE, "§r§eBlue Flame Explosion", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.SOUL_FIRE_FLAME)));
        explosions.put(25,new CosmeticParticle("explosion.flame", Material.CAMPFIRE, "§r§eFlame Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.FLAME)));
        explosions.put(26,new CosmeticParticle("explosion.vice", Material.PINK_GLAZED_TERRACOTTA, "§r§eVice Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x67c7f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x67c7f0)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xf890e7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xf890e7)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffffff))));
        explosions.put(27,new CosmeticParticle("explosion.camouflage", Material.DARK_OAK_LEAVES, "§r§eCamouflage Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x3b231c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x875730)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xaea398)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x34431c)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x6d8455))));
        explosions.put(28,new CosmeticParticle("explosion.halloween", Material.JACK_O_LANTERN, "§r§eHalloween Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff9a00)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xff9a00)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x09ff00)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc900ff))));
        explosions.put(29,new CosmeticParticle("explosion.christmas", Material.CHEST, "§r§eChristmas Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x127419)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x127419)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc73636)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xc73636)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xffffff))));
        explosions.put(30,new CosmeticParticle("explosion.ender", Material.ENDER_CHEST, "§r§eEnder Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000000)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xcb59ff)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xaa1bab)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xed8cff)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x000000))));
        explosions.put(31,new CosmeticParticle("explosion.nether", Material.WARPED_STEM, "§r§eNether Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x511515)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x723232)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xac2020)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xfe8738)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x119b84))));
        explosions.put(32,new CosmeticParticle("explosion.raspberry", Material.WARPED_STEM, "§r§eRaspberry Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x8A307F)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x79A7D3)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x6883BC))));
        explosions.put(33,new CosmeticParticle("explosion.pastel", Material.LIME_TERRACOTTA, "§r§ePastel Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xAA96DA)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xC5FAD5)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xFFFFD2))));
        explosions.put(34,new CosmeticParticle("explosion.nursery", Material.LIME_CONCRETE_POWDER, "§r§eNursery Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xFFE77A)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x2C5F2D))));
        explosions.put(35,new CosmeticParticle("explosion.royal", Material.BLUE_CONCRETE, "§r§eRoyal Explosion", CosmeticRarity.RARE, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x234E70)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xFBF8BE))));
        explosions.put(36,new CosmeticParticle("explosion.maroon_blues", Material.PURPLE_TERRACOTTA , "§r§eMaroon Blues Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x408EC6)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x7A2048)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0x1E2761))));
        explosions.put(37,new CosmeticParticle("explosion.maturity", Material.ORANGE_TERRACOTTA , "§r§eMaturity Explosion", CosmeticRarity.EPIC, ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xB85042)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xE7E8D1)), ParticleSet.of(Particle.REDSTONE, Color.fromRGB(0xA7BEAE))));
        explosions.put(38,new CosmeticParticle("explosion.heart", Material.APPLE, "§r§eHeart Explosion", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.HEART)));
        explosions.put(39,new CosmeticParticle("explosion.soul", Material.SOUL_SAND, "§r§eSoul Explosion", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.SOUL),ParticleSet.of(Particle.SCULK_SOUL)));
        explosions.put(40,new CosmeticParticle("explosion.spell", Material.SOUL_SAND, "§r§eSpell Explosion", CosmeticRarity.LEGENDARY, ParticleSet.of(Particle.ENCHANTMENT_TABLE),ParticleSet.of(Particle.ENCHANTMENT_TABLE),ParticleSet.of(Particle.TOTEM)));

    }

    private static void setupWinSongs() {
        winSongs = new HashMap<>();
        winSongs.put(0,CosmeticSound.empty());
        winSongs.put(1,new CosmeticSound("rank.vip", Material.MUSIC_DISC_STAL, "§dStal §a(VIP)", CosmeticRarity.RARE, Sound.MUSIC_DISC_STAL));
        winSongs.put(2,new CosmeticSound("rank.pro", Material.MUSIC_DISC_MELLOHI, "§dMellohi §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_MELLOHI));
        winSongs.put(3,new CosmeticSound("rank.pro", Material.MUSIC_DISC_OTHERSIDE, "§dOtherside §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_OTHERSIDE));
        winSongs.put(4,new CosmeticSound("rank.pro", Material.MUSIC_DISC_PIGSTEP, "§dPigstep §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_PIGSTEP));
        winSongs.put(5,new CosmeticSound("rank.pro", Material.MUSIC_DISC_CHIRP, "§dChirp §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_CHIRP));
        winSongs.put(6,new CosmeticSound("rank.pro", Material.MUSIC_DISC_WARD, "§dWard §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_WARD));
        winSongs.put(7,new CosmeticSound("rank.pro", Material.MUSIC_DISC_BLOCKS, "§dBlocks §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_BLOCKS));
        winSongs.put(8,new CosmeticSound("rank.pro", Material.MUSIC_DISC_WAIT, "§dWait §6(PRO)", CosmeticRarity.EPIC, Sound.MUSIC_DISC_WAIT));
    }

    private static void setupBorders() {
        borders = new HashMap<>();
        borders.put(0, new CosmeticBlocks("default",Material.BLACK_CONCRETE,"§r§bBlack Backdrop","Black...",CosmeticRarity.COMMON,Material.BLACK_CONCRETE));
        borders.put(1, new CosmeticBlocks("border.red",Material.RED_CONCRETE,"§r§bRed Backdrop","Red...",CosmeticRarity.COMMON,Material.BLACK_CONCRETE));
        borders.put(2, new CosmeticBlocks("border.blue",Material.BLUE_CONCRETE,"§r§bBlue Backdrop","Blue...",CosmeticRarity.COMMON,Material.BLACK_CONCRETE));
        borders.put(3, new CosmeticBlocks("border.yellow",Material.YELLOW_CONCRETE,"§r§bYellow Backdrop","Yellow...",CosmeticRarity.COMMON,Material.BLACK_CONCRETE));
        borders.put(4, new CosmeticBlocks("border.green",Material.GREEN_CONCRETE,"§r§bGreen Backdrop","Greem...",CosmeticRarity.COMMON,Material.BLACK_CONCRETE));
        borders.put(5, new CosmeticBlocks("rank.pro",Material.SPRUCE_WOOD,"§r§bForest Backdrop §6(PRO)","A Dark Sppoooky Forest",CosmeticRarity.RARE,Material.SPRUCE_WOOD,Material.OAK_LEAVES,Material.SPRUCE_LEAVES,Material.BIRCH_LEAVES));
        borders.put(6, new CosmeticBlocks("rank.pro",Material.IRON_ORE,"§r§bShallow Mines Backdrop §6(PRO)","Yearning for the mines..",CosmeticRarity.EPIC,Material.STONE,Material.STONE,Material.STONE,Material.STONE,Material.ANDESITE,Material.ANDESITE,Material.STONE,Material.COBBLESTONE,Material.ANDESITE,Material.IRON_ORE,Material.COAL_ORE,Material.STONE,Material.STONE,Material.COAL_ORE,Material.STONE,Material.STONE,Material.GOLD_ORE,Material.IRON_ORE,Material.ANDESITE));
        borders.put(7, new CosmeticBlocks("border.deep_mines",Material.DIAMOND_ORE,"§r§bDeep Mines Backdrop","Youre gonna be rich!",CosmeticRarity.LEGENDARY,Material.STONE,Material.STONE,Material.ANDESITE,Material.ANDESITE,Material.COBBLESTONE,Material.ANDESITE,Material.GOLD_ORE,Material.DIAMOND_ORE,Material.STONE,Material.STONE,Material.GOLD_ORE,Material.EMERALD_ORE,Material.ANDESITE,Material.GLOWSTONE, Material.MOSSY_COBBLESTONE,Material.REDSTONE_ORE));
        borders.put(8, new CosmeticBlocks("border.amethyst",Material.AMETHYST_BLOCK,"§r§bAmethyst Backdrop","Where are we?",CosmeticRarity.EPIC,Material.AMETHYST_BLOCK,Material.AMETHYST_BLOCK,Material.AMETHYST_BLOCK,Material.CALCITE,Material.SMOOTH_BASALT,Material.SMOKER,Material.AMETHYST_BLOCK));
        borders.put(9, new CosmeticBlocks("border.beginners",Material.OAK_PLANKS,"§r§bBeginners Backdrop","Your first home!",CosmeticRarity.RARE,Material.OAK_PLANKS));
        borders.put(10, new CosmeticBlocks("border.lava",Material.LAVA_BUCKET,"§r§bLava Backdrop","Awfully Bright!",CosmeticRarity.LEGENDARY,Material.LAVA));
        borders.put(11, new CosmeticBlocks("border.nether",Material.NETHERRACK,"§r§bNether Backdrop","Were going to the nether",CosmeticRarity.EPIC,Material.NETHERRACK,Material.NETHERRACK,Material.NETHERRACK,Material.SOUL_SAND,Material.SOUL_SOIL,Material.GLOWSTONE,Material.NETHERRACK,Material.SOUL_SOIL,Material.NETHERRACK,Material.NETHERRACK,Material.NETHERRACK,Material.NETHERRACK,Material.SOUL_SOIL,Material.SOUL_SOIL,Material.SOUL_SAND,Material.NETHERRACK));
        borders.put(12, new CosmeticBlocks("border.nether_fortress",Material.NETHER_BRICKS,"§r§bNether Fortress Backdrop","A Nether Fortress",CosmeticRarity.RARE,Material.NETHER_BRICKS,Material.NETHER_BRICKS,Material.SOUL_SOIL,Material.NETHER_BRICKS,Material.BLACK_CONCRETE,Material.NETHER_BRICKS));
        borders.put(13, new CosmeticBlocks("border.sky",Material.BLUE_CONCRETE,"§r§bSky Backdrop","Its a bird!!",CosmeticRarity.EPIC,Material.LIGHT_BLUE_WOOL,Material.LIGHT_BLUE_WOOL,Material.LIGHT_BLUE_TERRACOTTA,Material.LIGHT_BLUE_CONCRETE,Material.LIGHT_BLUE_CONCRETE,Material.SEA_LANTERN,Material.LIGHT_BLUE_WOOL,Material.LIGHT_BLUE_WOOL,Material.LIGHT_BLUE_CONCRETE));
        borders.put(14, new CosmeticBlocks("border.sea_temple",Material.PRISMARINE_BRICKS,"§r§bOcean Temple Backdrop","Under the Sea",CosmeticRarity.RARE,Material.PRISMARINE_BRICKS,Material.PRISMARINE_BRICKS,Material.PRISMARINE_BRICKS,Material.PRISMARINE_BRICKS,Material.PRISMARINE_BRICKS,Material.DARK_PRISMARINE,Material.SEA_LANTERN,Material.PRISMARINE_BRICKS));
        borders.put(15, new CosmeticBlocks("border.stone_wall",Material.STONE_BRICKS,"§r§bStone Wall Backdrop","Its a Stone Wall",CosmeticRarity.RARE,Material.STONE_BRICKS,Material.STONE_BRICKS,Material.STONE_BRICKS,Material.MOSSY_STONE_BRICKS,Material.MOSSY_STONE_BRICKS,Material.ANDESITE));
        borders.put(16, new CosmeticBlocks("border.modern_blue",Material.TUBE_CORAL_BLOCK,"§r§bModern Blue Backdrop","Mordern Blue and White mix",CosmeticRarity.RARE,Material.TUBE_CORAL_BLOCK,Material.WHITE_CONCRETE_POWDER));
        borders.put(17, new CosmeticBlocks("border.creamcicle",Material.TUBE_CORAL_BLOCK,"§r§bCreamcicle Backdrop","Fun in the sun",CosmeticRarity.RARE,Material.ORANGE_CONCRETE,Material.WHITE_CONCRETE_POWDER));
        borders.put(18, new CosmeticBlocks("border.pillars",Material.QUARTZ_PILLAR,"§r§bPillars Backdrop","Pillars of Heaven",CosmeticRarity.RARE,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.QUARTZ_PILLAR,Material.SEA_LANTERN));
        borders.put(19, new CosmeticBlocks("border.brick",Material.BRICKS,"§r§bBrick Backdrop","Bricks n Light Within",CosmeticRarity.RARE,Material.ORANGE_CONCRETE,Material.BRICKS,Material.BRICKS,Material.BRICKS,Material.BRICKS,Material.BRICKS,Material.BRICKS,Material.GLOWSTONE,Material.BRICKS,Material.BRICKS));
        borders.put(20, new CosmeticBlocks("border.underwater",Material.WATER_BUCKET,"§r§bUnderwater Backdrop","Its like were underwater!",CosmeticRarity.LEGENDARY,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.WATER,Material.SEA_LANTERN,Material.WATER));
    }

    private static final int LEGEND_BALLS = 1;
    private static final int EPIC_BALLS = 2;
    private static final int RARE_BALLS = 3;
    private static final int COMMON_BALLS = 4;


    public static CosmeticParticle randomTrail() {
        List<CosmeticParticle> list = new ArrayList<>();
        for (CosmeticParticle c : new ArrayList<>(trails.values())) {
            if (!c.permission.equalsIgnoreCase("rank.pro") && !c.permission.equalsIgnoreCase("default")) {
                if (c.rarity.equals(CosmeticRarity.COMMON)) {
                    for (int i = 0; i < COMMON_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.RARE)) {
                    for (int i = 0; i < RARE_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.EPIC)) {
                    for (int i = 0; i < EPIC_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.LEGENDARY)) {
                    for (int i = 0; i < LEGEND_BALLS; i++) {
                        list.add(c);
                    }
                }
            }
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public static CosmeticParticle getVipRandom() {
        List<CosmeticParticle> list = new ArrayList<>();
        for (CosmeticParticle c : new ArrayList<>(trails.values())) {
            if (!c.permission.equalsIgnoreCase("rank.pro") && !c.permission.equalsIgnoreCase("default")) {
                if (c.rarity.equals(CosmeticRarity.COMMON)) {
                    for (int i = 0; i < LEGEND_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.RARE)) {
                    for (int i = 0; i < COMMON_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.EPIC)) {
                    for (int i = 0; i < RARE_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.LEGENDARY)) {
                    for (int i = 0; i < EPIC_BALLS; i++) {
                        list.add(c);
                    }
                }
            }
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public static CosmeticParticle getProRandom() {
        List<CosmeticParticle> list = new ArrayList<>();
        for (CosmeticParticle c : new ArrayList<>(trails.values())) {
            if (!c.permission.equalsIgnoreCase("rank.pro") && !c.permission.equalsIgnoreCase("default")) {
                if (c.rarity.equals(CosmeticRarity.COMMON)) {
                    for (int i = 0; i < LEGEND_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.RARE)) {
                    for (int i = 0; i < EPIC_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.EPIC)) {
                    for (int i = 0; i < COMMON_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.LEGENDARY)) {
                    for (int i = 0; i < RARE_BALLS; i++) {
                        list.add(c);
                    }
                }
            }
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public static CosmeticParticle randomExplosion() {
        List<CosmeticParticle> list = new ArrayList<>();
        for (CosmeticParticle c : new ArrayList<>(explosions.values())) {
            if (!c.permission.equalsIgnoreCase("rank.pro")) {
                if (c.rarity.equals(CosmeticRarity.COMMON)) {
                    for (int i = 0; i < COMMON_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.RARE)) {
                    for (int i = 0; i < RARE_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.EPIC)) {
                    for (int i = 0; i < EPIC_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.LEGENDARY)) {
                    for (int i = 0; i < LEGEND_BALLS; i++) {
                        list.add(c);
                    }
                }
            }
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public static CosmeticBlocks randomBorder() {
        List<CosmeticBlocks> list = new ArrayList<>();
        for (CosmeticBlocks c : new ArrayList<>(borders.values())) {
            if (!c.permission.equalsIgnoreCase("rank.pro")) {
                if (c.rarity.equals(CosmeticRarity.COMMON)) {
                    for (int i = 0; i < COMMON_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.RARE)) {
                    for (int i = 0; i < RARE_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.EPIC)) {
                    for (int i = 0; i < EPIC_BALLS; i++) {
                        list.add(c);
                    }
                } else if (c.rarity.equals(CosmeticRarity.LEGENDARY)) {
                    for (int i = 0; i < LEGEND_BALLS; i++) {
                        list.add(c);
                    }
                }
            }
        }
        return list.get(new Random().nextInt(list.size()));
    }

    public static HashMap<Integer, CosmeticParticle> trails;
    public static HashMap<Integer, CosmeticSound> winSongs;
    public static HashMap<Integer, CosmeticParticle> explosions;
    public static HashMap<Integer, CosmeticBlocks> borders;




}
