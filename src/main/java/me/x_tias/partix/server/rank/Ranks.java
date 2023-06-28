package me.x_tias.partix.server.rank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Ranks {

    public static void setup(Scoreboard s) {
        scoreboard = s;
        admin = createTeam("aaa_admin",NamedTextColor.DARK_RED, "§cADMIN §f");
        mod = createTeam("bbb_mod",NamedTextColor.BLUE, "§9Mod §f");
        media = createTeam("ccc_media",NamedTextColor.LIGHT_PURPLE, "§dMedia §f");
        pro = createTeam("ddd_pro",NamedTextColor.GOLD, "§6PRO §f");
        vip = createTeam("eee_vip",NamedTextColor.GREEN, "§aVIP §f");
        def = createTeam("fff_default",NamedTextColor.GRAY, "§7");
    }

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    private static Scoreboard scoreboard;

    private static Team admin;
    private static Team mod;
    private static Team media;
    private static Team pro;
    private static Team vip;
    private static Team def;

    public static Team getAdmin() {
        return admin;
    }

    public static Team getMod() {
        return mod;
    }

    public static Team getMedia() {
        return media;
    }

    public static Team getPro() {
        return pro;
    }

    public static Team getVip() {
        return vip;
    }

    public static Team getDefault() {
        return def;
    }

    private static Team createTeam(String name, NamedTextColor color, String prefix) {
        Team team;
        if (scoreboard.getTeam(name) != null) {
            team = scoreboard.getTeam(name);
        } else {
            team = scoreboard.registerNewTeam(name);
        }
        if (team != null) {
            team.color(color);
            team.prefix(Component.text(prefix));
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        }
        return team;
    }


}
