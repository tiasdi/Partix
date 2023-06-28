package me.x_tias.partix.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.x_tias.partix.database.BasketballDb;
import me.x_tias.partix.database.PlayerDb;
import me.x_tias.partix.database.SeasonDb;
import me.x_tias.partix.mini.lobby.MainLobby;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.sidebar.Sidebar;
import me.x_tias.partix.season.Season;
import me.x_tias.partix.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.eclipse.sisu.launch.Main;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@CommandAlias("partix")
@CommandPermission("rank.admin")
public class PartixCommand extends BaseCommand {


    @Subcommand("coins add")
    public void onCoinAdd(CommandSender sender, String[] args) {
        if (sender.isOp() || sender.hasPermission("rank.admin")) {
            if (args.length == 2) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                if (op.hasPlayedBefore()) {
                    int m = Integer.parseInt(args[1]);
                    if (m > 0) {
                        PlayerDb.add(op.getUniqueId(), PlayerDb.Stat.COINS, m);
                        sender.sendMessage("Successfully gave " + op.getName() + " " + m + " Coin(s)!");
                        if (op.isOnline()) {
                            Player player = op.getPlayer();
                            if (player != null) {
                                Athlete athlete = AthleteManager.get(player.getUniqueId());
                                if (athlete.getPlace() instanceof MainLobby lobby) {
                                    lobby.updateSidebar(athlete);
                                }
                                player.sendMessage(Message.receiveCoins(m));
                                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, SoundCategory.MASTER, 100f, 1f);
                            }
                        }
                    } else {
                        sender.sendMessage("/partix coins add <player> <amount>");
                    }
                } else {
                    sender.sendMessage("Unknown player");
                }
            } else {
                sender.sendMessage("/partix coins add <player> <amount>");
            }
        }
    }

    @Subcommand("coins get")
    public void onCoinGet(CommandSender sender, String[] args) {
        if (sender.isOp() || sender.hasPermission("rank.mod")) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
            if (args.length == 1) {
                if (op.hasPlayedBefore() || op.isOnline()) {
                    int coins = PlayerDb.get(op.getUniqueId(), PlayerDb.Stat.COINS);
                    sender.sendMessage(op.getName() + " has " + coins + " Coin(s)!");
                } else {
                    sender.sendMessage("/partix coins get <player>");
                }
            } else {
                sender.sendMessage("Unknown player");
            }
        } else {
            sender.sendMessage("/partix coins get <player>");
        }
    }

    @Subcommand("seasons top")
    public void onSeasonGet(CommandSender sender) {
        if (sender.isOp() || sender.hasPermission("rank.admin")) {
            HashMap<Integer, UUID> top = SeasonDb.getTop(SeasonDb.Stat.POINTS, 25);
            for (int i : top.keySet()) {
                sender.sendMessage(i+". "+ Objects.requireNonNull(Bukkit.getOfflinePlayer(top.get(i))).getName() + " - "+SeasonDb.get(top.get(i), SeasonDb.Stat.POINTS));
            }
        }
    }

    @Subcommand("seasons trophies")
    public void onSeasonTrophies(CommandSender sender) {
        if (sender.isOp() || sender.hasPermission("rank.admin")) {
            HashMap<Integer, UUID> top = PlayerDb.getTop(PlayerDb.Stat.CHAMPIONSHIPS, 30);
            for (int i : top.keySet()) {
                sender.sendMessage(i+". "+ Objects.requireNonNull(Bukkit.getOfflinePlayer(top.get(i))).getName() + " - "+PlayerDb.get(top.get(i), PlayerDb.Stat.CHAMPIONSHIPS));
            }
        }
    }

    @Subcommand("seasons gold")
    public void onSeasonGold(CommandSender sender) {
        if (sender.isOp() || sender.hasPermission("rank.admin")) {
            HashMap<Integer, UUID> top = PlayerDb.getTop(PlayerDb.Stat.SEASONS_GOLD, 30);
            for (int i : top.keySet()) {
                sender.sendMessage(i+". "+ Objects.requireNonNull(Bukkit.getOfflinePlayer(top.get(i))).getName() + " - "+PlayerDb.get(top.get(i), PlayerDb.Stat.SEASONS_GOLD));
            }
        }
    }

    @Subcommand("stats basketball reset")
    public void onStatsReset(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender c) {
            if (sender.isOp() || sender.hasPermission("rank.admin")) {
                BasketballDb.setAll(BasketballDb.Stat.POINTS, 0);
                BasketballDb.setAll(BasketballDb.Stat.WINS, 0);
                BasketballDb.setAll(BasketballDb.Stat.LOSSES, 0);
                sender.sendMessage("all basketball points, wins, and losses have been reset!");
            }
        }
    }

    @Subcommand("stats trophies reset")
    public void onTrophiesReset(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender c) {
            if (sender.isOp() || sender.hasPermission("rank.admin")) {
                PlayerDb.setAll(PlayerDb.Stat.CHAMPIONSHIPS, 0);
                sender.sendMessage("all trophies have been reset!");
            }
        }
    }

    @Subcommand("stats gold_seasons reset")
    public void onSeasonsInGold(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender c) {
            if (sender.isOp() || sender.hasPermission("rank.admin")) {
                PlayerDb.setAll(PlayerDb.Stat.SEASONS_GOLD, 0);
                sender.sendMessage("all gold seasons have been reset!");
            }
        }
    }

    @Subcommand("rank set")
    public void onRankSet(CommandSender sender, String[] args) {
        if (sender.isOp() || sender.hasPermission("rank.admin")) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
            if (args.length == 2) {
                if (op.hasPlayedBefore() || op.isOnline()) {
                    String name = op.getName();
                    String rank = args[1].toLowerCase(Locale.ROOT);
                    if (rank.equals("admin") || rank.equals("mod") || rank.equals("media") || rank.equals("pro") || rank.equals("vip") || rank.equals("default")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + name + " group set " + args[1]);
                    } else {
                        sender.sendMessage("Rank "+rank+" does not exist..");
                        return;
                    }
                    if (op.isOnline()) {
                        Player player = op.getPlayer();
                        if (player != null) {
                            Athlete athlete = AthleteManager.get(player.getUniqueId());
                            if (athlete.getPlace() instanceof MainLobby l) {
                                l.updateSidebar(athlete);
                                athlete.updateRank();
                            }
                        }
                    }
                } else {
                    sender.sendMessage("/partix rank set <player> <rank>");
                }
            } else {
                sender.sendMessage("Unknown player");
            }
        } else {
            sender.sendMessage("/partix rank set <player> <rank>");
        }
    }



}
