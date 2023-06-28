package me.x_tias.partix.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.x_tias.partix.mini.lobby.MainLobby;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.party.Party;
import me.x_tias.partix.plugin.party.PartyFactory;
import me.x_tias.partix.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("party|p")
public class PartyCommand extends BaseCommand {

    @Default
    public void onPartyCommand(CommandSender sender) {
        sender.sendMessage("§6§lPartix Parties");
        sender.sendMessage("§e/party join <id> §7- §fJoin a party");
        sender.sendMessage("§e/party leave §7- §fLeave your current party");
        sender.sendMessage("§e/party create §7- §fCreate your own party");
        sender.sendMessage("§e/party disband §7- §fDisband your current party");
        sender.sendMessage("§e/party lock §7- §fPrevent new players from joining your party");
        sender.sendMessage("§e/party unlock §7- §fAllow new players to join your party");
        if (sender instanceof Player player) {
            if (AthleteManager.get(player.getUniqueId()).getParty() > 0) {
                sender.sendMessage("§aYour current party id is: §e"+AthleteManager.get(player.getUniqueId()).getParty());
            } else {
                sender.sendMessage("§aYou are not currently in a party!");
            }
        }

    }

    @Subcommand("join")
    public void onPartyJoin(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            if (athlete.getParty() < 0) {
                if (athlete.getPlace() instanceof MainLobby) {
                    if (args.length == 1) {
                        int i;
                        try {
                            i = Integer.parseInt(args[0]);
                        } catch (Exception ex) {
                            i = -1;
                        }
                        if (i > 0) {
                            if (i < 10000 || i > 99999) {
                                player.sendMessage("§6Party > §c" + args[0] + " is not a valid 5-digit party ID!");
                            } else {
                                if (PartyFactory.exists(i)) {
                                    Party party = PartyFactory.get(i);
                                    if (party.count() > 4) {
                                        player.sendMessage("§6Party > §cParty " + args[0] + " is already full! (5/5)");
                                    } else {
                                        int id = party.id;
                                        party.sendMessage("§6Party > §aWelcome §2" + player.getName() + "§a to the party!");
                                        athlete.setParty(id);
                                    }
                                } else {
                                    player.sendMessage("§6Party > §7Party " + args[0] + " could not be found.");

                                }
                            }
                        } else {
                            player.sendMessage("§6Party > §7" + args[0] + " is not a valid 5-digit party ID!");
                        }
                    } else {
                        player.sendMessage("§6Party > §7/party join <ID>");
                    }
                } else {
                    player.sendMessage("§6Party > §7You must be in the Lobby to join a party!");
                }
            } else {
                player.sendMessage("§6Party > §7You are already in a party!");
            }
        }
    }

    @Subcommand("leave")
    public void onLeaveParty(CommandSender sender) {
        if (sender instanceof Player player) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            if (athlete.getParty() > 0) {
                Party party = PartyFactory.get(athlete.getParty());
                if (party.leader.equals(player.getUniqueId())) {
                    player.sendMessage("§6Party > §7You cannot leave your own party! Use '/party disband' instead.");
                } else {
                    party.sendMessage("§6Party > §cSadly, §4"+player.getName()+"§c has left the party..");
                    athlete.setParty(-1);
                }
            } else {
                player.sendMessage("§6Party > §7You are not in a party!");
            }
        }
    }

    @Subcommand("create")
    public void onPartyCreate(CommandSender sender) {
        if (sender instanceof Player player) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            if (athlete.getParty() < 0) {
                if (athlete.getPlace() instanceof MainLobby) {
                    Party party = PartyFactory.create(player);
                    party.leader = player.getUniqueId();
                    athlete.setParty(party.id);
                    player.sendMessage("§6Party > §fWelcome to your Party! §7- §aParty id: "+party.id);
                } else {
                    player.sendMessage("§6Party > §7You must be in the lobby to create a party.");
                }
            } else {
                player.sendMessage("§6Party > §7You are already in a party!");
            }
        }
    }

    @Subcommand("disband")
    public void onDisbandParty(CommandSender sender) {
        if (sender instanceof Player player) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            if (athlete.getParty() > 0) {
                Party party = PartyFactory.get(athlete.getParty());
                if (party.leader.equals(player.getUniqueId())) {
                    party.sendMessage("§7This party has been disbanded.");
                    party.toList().forEach(a -> a.setParty(-1));
                    PartyFactory.disband(party.id);
                } else {
                    player.sendMessage("§6Party > §7You cannot disband someone else's party!");
                }
            } else {
                player.sendMessage("§6Party > §7You are not in a party!");
            }
        }
    }

    @Subcommand("invite")
    public void onInvite(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            if (args.length == 0) {
                player.sendMessage("§6Party > §7Please enter the players name!");
                return;
            }
            if (athlete.getParty() > 0) {
                Party party = PartyFactory.get(athlete.getParty());
                if (party.leader.equals(player.getUniqueId())) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p != null) {
                        if (p.isOnline()) {
                            p.sendMessage("§6Party > §7"+player.getName()+" sent you an invite! §8- §f/party join "+party.id);

                            player.sendMessage("§6Party > §7You have sent "+p.getName()+" this parties join code!");
                        } else {
                            player.sendMessage("§6Party > §7'"+p.getName()+"' is not online!");
                        }
                    } else {
                        player.sendMessage("§6Party > §7Could not find an online player named '"+args[0]+"'");
                    }
                } else {
                    party.sendMessage("§6Party > §7You cannot invite someone to the party, although, anyone can use /party join "+party.id+"!");
                }
            } else {
                player.sendMessage("§6Party > §7You are not in a party!");
            }
        }
    }

}
