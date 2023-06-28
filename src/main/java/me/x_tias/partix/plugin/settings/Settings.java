package me.x_tias.partix.plugin.settings;

import org.bukkit.potion.PotionEffect;

public class Settings {

        public WinType winType;
        public GameType gameType;
        public WaitType waitType;
        public CompType compType;
        public int playersPerTeam;
        public boolean teamLock;
        public boolean arenaLock;
        public boolean suddenDeath;
        public int periods;
        public GameEffectType gameEffect;

        public Settings(WinType winType, GameType gameType, WaitType waitType, CompType compType, int playersPerTeam, boolean teamLock, boolean arenaLock, boolean suddenDeath, int periods, GameEffectType effect) {
                this.winType = winType;
                this.gameType = gameType;
                this.waitType = waitType;
                this.compType = compType;
                this.playersPerTeam = playersPerTeam;
                this.teamLock = teamLock;
                this.arenaLock = arenaLock;
                this.suddenDeath = suddenDeath;
                this.periods = periods;
                this.gameEffect = effect;


        }

        public Settings copy() {
                return new Settings(winType,gameType,waitType,compType,playersPerTeam,teamLock,arenaLock,suddenDeath,periods,gameEffect);
        }

}
