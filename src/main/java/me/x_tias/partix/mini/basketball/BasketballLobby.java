package me.x_tias.partix.mini.basketball;

import me.x_tias.partix.Partix;
import me.x_tias.partix.database.BasketballDb;
import me.x_tias.partix.database.SeasonDb;
import me.x_tias.partix.mini.factories.Hub;
import me.x_tias.partix.mini.game.GoalGame;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.settings.*;
import me.x_tias.partix.server.specific.Game;
import me.x_tias.partix.server.specific.Lobby;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Items;
import me.x_tias.partix.util.Message;
import me.x_tias.partix.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BasketballLobby extends Lobby {

    private final List<Location> maps = new ArrayList<>();
    private final HashMap<Location, BasketballGame> games = new HashMap<>();
    private final HashMap<Athlete, Double> skill = new HashMap<>();

    private final HashMap<UUID, Team> teamStorage = new HashMap<>();
    private final List<Team> waitingTeams = new ArrayList<>();
    private final List<Athlete> waitingExtras = new ArrayList<>();

    private int countdown = 170;
    private final Settings gameSettings = new Settings(
            WinType.TIME_3,
            GameType.AUTOMATIC,
            WaitType.SHORT,
            CompType.RANKED,
            2,
            true,
            false,
            false,
            2,
            GameEffectType.NONE
    );

    private final Settings customSettings = new Settings(
            WinType.TIME_5,
            GameType.MANUAL,
            WaitType.MEDIUM,
            CompType.CASUAL,
            2,
            false,
            false,
            false,
            4,
            GameEffectType.NONE
    );

    public BasketballLobby() {
        //arena creation
        maps.add(new Location(Bukkit.getWorlds().get(0),-56.5, -60, -175.5)); // Example. Targeted block jungle log (f3): -57 -61 -176
        maps.add(new Location(Bukkit.getWorlds().get(0),-121.5, -60, -175.5));  // 2nd location
        maps.add(new Location(Bukkit.getWorlds().get(0),-186.5, -60, -175.5));  // 3rd location
        maps.add(new Location(Bukkit.getWorlds().get(0),-251.5, -60, -175.5));  // 4th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-316.5, -60, -175.5));  // 5th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-381.5, -60, -175.5));  // 6th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-446.5, -60, -175.5));  // 7th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-511.5, -60, -175.5));  // 8th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-576.5, -60, -175.5));  // 9th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-641.5, -60, -175.5));  // 10th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-706.5, -60, -175.5));  // 11th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-771.5, -60, -175.5));  // 12th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-56.5, -60, -232.5)); // new row
        maps.add(new Location(Bukkit.getWorlds().get(0),-121.5, -60, -232.5));  // 2nd location
        maps.add(new Location(Bukkit.getWorlds().get(0),-186.5, -60, -232.5));  // 3rd location
        maps.add(new Location(Bukkit.getWorlds().get(0),-251.5, -60, -232.5));  // 4th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-316.5, -60, -232.5));  // 5th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-381.5, -60, -232.5));  // 6th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-446.5, -60, -232.5));  // 7th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-511.5, -60, -232.5));  // 8th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-576.5, -60, -232.5));  // 9th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-641.5, -60, -232.5));  // 10th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-706.5, -60, -232.5));  // 11th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-771.5, -60, -232.5));  // 12th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-56.5, -60, -232.5)); // new row
        maps.add(new Location(Bukkit.getWorlds().get(0),-121.5, -60, -289.5));  // 2nd location
        maps.add(new Location(Bukkit.getWorlds().get(0),-186.5, -60, -289.5));  // 3rd location
        maps.add(new Location(Bukkit.getWorlds().get(0),-251.5, -60, -289.5));  // 4th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-316.5, -60, -289.5));  // 5th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-381.5, -60, -289.5));  // 6th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-446.5, -60, -289.5));  // 7th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-511.5, -60, -289.5));  // 8th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-576.5, -60, -289.5));  // 9th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-641.5, -60, -289.5));  // 10th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-706.5, -60, -289.5));  // 11th location
        maps.add(new Location(Bukkit.getWorlds().get(0),-771.5, -60, -289.5));  // 12th location


    }

    public List<BasketballGame> getGames() {
        return new ArrayList<>(games.values().stream().toList());
    }

    @Override
    public void onTick() {
        games.values().forEach(BasketballGame::onTick);
        if (countdown > 5) {
            if (isReady()) {
                if (countdown > 70) {
                    updateBossBar("§ePreparing to Generate Teams..",Math.min(1.0,Math.max(0.0,((double)countdown/170))));
                } else if (countdown > 30) {
                    updateBossBar("§6Starting Team Generation..",Math.min(1.0,Math.max(0.0,((double)countdown/170))));
                } else {
                    updateBossBar("§fGenerating.. §7"+waitingExtras.get(new Random().nextInt(waitingExtras.size())).getPlayer().getName()+", "+waitingExtras.get(new Random().nextInt(waitingExtras.size())).getPlayer().getName()+" vs "+waitingExtras.get(new Random().nextInt(waitingExtras.size())).getPlayer().getName()+", "+waitingExtras.get(new Random().nextInt(waitingExtras.size())).getPlayer().getName());
                }
            } else {
                updateBossBar("§cWaiting for more Players..",1.0);
            }
        }
        cleanGames();
        detectMatch();
    }

    private void cleanGames() {
        new HashMap<>(games).forEach((location, game) -> {
            if (game.getPlayers().size() < 1) {
                game.reset();
                games.remove(location);
            }
        });
    }

    public void cleanup() {
        for (BasketballGame game : games.values()) {
            game.reset();
        }
    }

    private void detectMatch() {
        if (isReady()) {
            countdown --;
            if (countdown < 0) {
                matchmaker();
            }
        } else {
            if (countdown < 170) {
                sendTitle(Component.text("Searching for Opponent..").color(Colour.deny()));
                countdown = 170;
            }
        }
    }


    private boolean isReady() {
        int teams = waitingTeams.size();
        int extras = waitingExtras.size();
        int total = (int)Math.floor((double)extras/2)+teams;
        return total > 1;
    }

    private void matchmaker() {
        int extras = waitingExtras.size();
        int teams = waitingTeams.size();
        BasketballGame game = findAvailableGame(false);
        HashMap<Integer, Athlete> ranked = Util.getRankedAthletes(new HashMap<>(skill));
        Team home;
        Team away;
        Random r = new Random();
        boolean one = r.nextBoolean();
        boolean two = r.nextBoolean();
        boolean three = r.nextBoolean();
        boolean four = r.nextBoolean();
        boolean five = r.nextBoolean();

        if (teams == 1) {
            home = waitingTeams.get(0);
            away = new Team(ranked.get(1), ranked.get(2));
        } else if (teams > 1) {
            home = waitingTeams.get(0);
            away = waitingTeams.get(1);
        } else {
            if (extras == 4) {
                home = new Team(one ? ranked.get(1) : ranked.get(2), two ? ranked.get(3) : ranked.get(4));
                away = new Team(one ? ranked.get(2) : ranked.get(1), two ? ranked.get(4) : ranked.get(3));
            } else if (extras == 5) {
                home = new Team(one ? ranked.get(1) : ranked.get(2), two ? ranked.get(3) : three ? ranked.get(4) : ranked.get(5));
                away = new Team(one ? ranked.get(2) : ranked.get(1), two ? three ? ranked.get(5) : ranked.get(4) : ranked.get(3));
            } else if (extras == 6) {
                home = new Team(one ? ranked.get(2) : two ? ranked.get(1) : ranked.get(3), three ? ranked.get(5) : four ? ranked.get(4) : ranked.get(6));
                away = new Team(one ? two ? ranked.get(1) : ranked.get(3) : ranked.get(2), three ? four ? ranked.get(4) : ranked.get(6) : ranked.get(5));
            } else if (extras == 7) {
                home = new Team(one ? ranked.get(3) : two ? five ? ranked.get(2) : ranked.get(1) : ranked.get(4), three ? ranked.get(6) : four ? ranked.get(5) : ranked.get(7));
                away = new Team(one ? two ? five ? ranked.get(1) : ranked.get(2) : ranked.get(4) : ranked.get(3), three ? four ? ranked.get(5) : ranked.get(7) : ranked.get(6));
            } else {
                home = new Team(one ? ranked.get(1) : ranked.get(2), two ? ranked.get(3) : three ? ranked.get(4) : ranked.get(5));
                away = new Team(one ? ranked.get(2) : ranked.get(1), two ? three ? ranked.get(5) : ranked.get(4) : ranked.get(3));
            }
            for (Athlete athlete : home.getAthletes()) {
                waitingExtras.remove(athlete);
            }
            for (Athlete athlete : away.getAthletes()) {
                waitingExtras.remove(athlete);
            }
        }

        joinMatch(game,home,away);


        detectMatch();
    }

    private void joinMatch(BasketballGame game, Team home, Team away) {
        updateBossBar("§eSending to Match..",0.0);
        List<Material[]> homeB = new ArrayList<>();
        List<Material[]> awayB = new ArrayList<>();
        for (Athlete athlete : home.getAthletes()) {
            game.join(athlete);
            game.joinTeam(athlete.getPlayer(), GoalGame.Team.HOME);
            if (athlete.getPlayer().hasPermission("rank.pro")) {
                for (int i = 0; i < 3; i++) {
                    homeB.add(athlete.getBorder().get());
                }
            } else if (athlete.getPlayer().hasPermission("rank.vip")) {
                for (int i = 0; i < 2; i++) {
                    homeB.add(athlete.getBorder().get());
                }
            } else {
                homeB.add(athlete.getBorder().get());
            }
        }
        for (Athlete athlete : away.getAthletes()) {
            game.join(athlete);
            game.joinTeam(athlete.getPlayer(), GoalGame.Team.AWAY);
            if (athlete.getPlayer().hasPermission("rank.pro")) {
                for (int i = 0; i < 3; i++) {
                    awayB.add(athlete.getBorder().get());
                }
            } else if (athlete.getPlayer().hasPermission("rank.vip")) {
                for (int i = 0; i < 2; i++) {
                    awayB.add(athlete.getBorder().get());
                }
            } else {
                awayB.add(athlete.getBorder().get());
            }
        }
        game.backdrops(homeB.get(new Random().nextInt(homeB.size())),awayB.get(new Random().nextInt(awayB.size())));
        game.startCountdown(GoalGame.State.FACEOFF,15);
    }

    public BasketballGame findAvailableGame(boolean custom) {
        List<Location> a = new ArrayList<>(maps);
        a.removeAll(games.keySet());

        Optional<Location> possibleGame = a.stream().findFirst();
        if (possibleGame.isPresent()) {
            Location l = possibleGame.get();
            BasketballGame game = create(l, custom, false);
            games.put(l,game);
            return game;
        }

        return null;

    }

    private BasketballGame create(Location l, boolean custom, boolean arena) {
        return new BasketballGame(custom ? customSettings : gameSettings, l, 24, 2.8, 0.45,0.45,0.575);
    }



    @Override
    public void onJoin(Athlete... athletes) {
        for (Athlete athlete : athletes) {
            Player player = athlete.getPlayer();
            athlete.setSpectator(true);
            player.teleport(new Location(Bukkit.getWorlds().get(0), 0.5, -58, 0.5));
            skill.put(athlete, generateSkill(athlete));
            waitingExtras.add(athlete);
        }
    }

    @Override
    public void onQuit(Athlete... athletes) {
        boolean party = athletes.length > 1;
        if (party) {
            Team team = null;
            for (Athlete athlete : athletes) {
                Team t = teamStorage.get(athlete.getPlayer().getUniqueId());
                if (t != null) {
                    team = t;
                }
                teamStorage.remove(athlete.getPlayer().getUniqueId());
            }
            final Team compare = team;
            waitingTeams.removeIf(t -> t != null && t.equals(compare));
        }
        for (Athlete athlete : athletes) {
            waitingExtras.remove(athlete);
            skill.remove(athlete);
        }
    }

    @Override
    public void clickItem(Player player, ItemStack itemStack) {
        if (itemStack.getType().equals(Material.RED_BED)) {
            Hub.hub.join(AthleteManager.get(player.getUniqueId()));
        }
    }

    @Override
    public void giveItems(Player player) {
        player.getInventory().setItem(8, Items.get(Message.itemName("Return to Hub","key.use", player),Material.RED_BED));
    }

    private double generateSkill(Athlete athlete) {
        Player player = athlete.getPlayer();
        UUID uuid = player.getUniqueId();
        double seasonWins = SeasonDb.get(uuid, SeasonDb.Stat.WINS);
        double seasonLosses = SeasonDb.get(uuid, SeasonDb.Stat.LOSSES);
        double seasonPoints = SeasonDb.get(uuid, SeasonDb.Stat.POINTS);
        double sportWins = BasketballDb.get(uuid, BasketballDb.Stat.WINS);
        double sportLosses = BasketballDb.get(uuid, BasketballDb.Stat.LOSSES);
        double sportMVP = BasketballDb.get(uuid, BasketballDb.Stat.MVP);

        double seasonGames = seasonWins + seasonLosses;
        double sportGames = sportWins + sportLosses;

        double season = (seasonPoints / (seasonGames * 3)) * 0.3;
        double sport = (sportWins / (sportGames)) * 0.4;
        double mvp = (sportMVP / sportGames) * 0.3;
        double wins = (Math.min(35,seasonWins)/35) * 0.4;

        return Math.min(Math.max(((season + sport + mvp + wins) * 10),0.19)+(Math.random() * 0.1),9.99);
    }


    private class Team {
        public Team(Athlete... athletes) {
            this.athletes = new ArrayList<>();
            if (athletes.length > 0) {
                this.athletes.addAll(Arrays.stream(athletes).toList());
                for (Athlete athlete : athletes) {
                    if (athlete != null) {
                        skill += generateSkill(athlete);
                    }
                }
                skill = skill / athletes.length;
            } else {
                skill = 255;
            }
        }

        private int skill;
        private final List<Athlete> athletes;

        public List<Athlete> getAthletes() {
            return athletes;
        }

        public int getSkill() {
            return skill;
        }
    }
}
