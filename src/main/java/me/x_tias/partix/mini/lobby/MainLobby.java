package me.x_tias.partix.mini.lobby;

import me.x_tias.partix.Partix;
import me.x_tias.partix.database.BasketballDb;
import me.x_tias.partix.database.PlayerDb;
import me.x_tias.partix.database.SeasonDb;
import me.x_tias.partix.mini.basketball.BasketballGame;
import me.x_tias.partix.mini.factories.Hub;
import me.x_tias.partix.mini.game.GoalGame;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.cosmetics.*;
import me.x_tias.partix.plugin.gui.GUI;
import me.x_tias.partix.plugin.gui.ItemButton;
import me.x_tias.partix.plugin.party.Party;
import me.x_tias.partix.plugin.party.PartyFactory;
import me.x_tias.partix.plugin.sidebar.Sidebar;
import me.x_tias.partix.server.specific.Lobby;
import me.x_tias.partix.util.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainLobby extends Lobby {

    public MainLobby() {
        Bukkit.getScheduler().runTaskLater(Partix.getInstance(), () -> {

        },(60*20)*60);
    }



    int i = 0;
    @Override
    public void onTick() {
        i += 1;
        if (i > 300) {
            getAthletes().forEach(this::updateSidebar);
            i = 0;
        }

        if (i < 150) {
            updateBossBar("§6§lPLAY.PARTIX.NET §7§l> §f§lCLOSED TESTING!");
        } else {
            updateBossBar("§e§lSUBMIT FEEDBACK TO §6§lTias#6453§e§l!");
        }
    }

    public void updateSidebar(Athlete athlete) {
        Player player = athlete.getPlayer();
        String rank = athlete.getRank();
        int coins = PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.COINS);
        int pts = (SeasonDb.get(player.getUniqueId(), SeasonDb.Stat.POINTS));
        String div = pts >= 50000 ? "§6Gold" : "§7Silver";
        String points = String.valueOf(pts - (pts >= 50000 ? 50000 : 0));
        String trophies = String.valueOf(PlayerDb.get(player.getUniqueId(),PlayerDb.Stat.CHAMPIONSHIPS));

        Sidebar.set(player,Component.text("  play.partix.net  ").color(Colour.partix()).decorate(TextDecoration.BOLD),
                " ",
                "§6§lYour Info  ",
                "  §fName: §e"+player.getName(),
                "  §fRank: "+rank,
                "  §fVer: "+(player.getName().startsWith(".") ? "§eBedrock" : "§eJava"),
                "     ",
                "§6§lYour Stats  ",
                "  §fCoins: §e"+coins,
                "  §fTrophies: §e"+trophies,
                "        ",
                "§6§lThis Season  ",
                "  §fDiv: §e"+div,
                "  §fPts: §e"+points,
                "                     ");
    }

    @Override
    public void onJoin(Athlete... athletes) {
        for (Athlete athlete : athletes) {
            Player player = athlete.getPlayer();
            player.teleport(new Location(Bukkit.getWorlds().get(0),0.5,4.5,0.5));
            athlete.setSpectator(false);
            updateSidebar(athlete);
            player.getInventory().clear();
            giveItems(player);
        }
    }

    @Override
    public void onQuit(Athlete... athletes) {

    }

    @Override
    public void giveItems(Player player) {
        Inventory i = player.getInventory();
        i.setItem(0, Items.get(Message.itemName("Game Selector","key.use", player), Material.COMPASS));
        i.setItem(1, Items.get(Message.itemName("Custom Games","key.use", player), Material.BEACON));
        i.setItem(2, Items.get(Message.itemName("Your Stats","key.use", player), Material.KNOWLEDGE_BOOK));
        i.setItem(7, Items.get(Message.itemName("Item Shop","key.use", player), Material.EMERALD));
        i.setItem(8, Items.get(Message.itemName("Your Cosmetics","key.use", player), Material.ENDER_CHEST));
    }

    private void attemptJoin(Athlete athlete, Consumer<Athlete[]> join) {
        if (athlete.getParty() < 0) {
            join.accept(new Athlete[]{athlete});
        } else {
            Party party = PartyFactory.get(athlete.getParty());
            if (party.leader.equals(athlete.getPlayer().getUniqueId())) {
                join.accept(party.toList().toArray(getAthletes().toArray(new Athlete[0])));
            } else {
                athlete.getPlayer().sendMessage(Message.onlyPartyLeader());
            }
        }
    }

    @Override
    public void clickItem(Player player, ItemStack itemStack) {
        if (itemStack.getType().equals(Material.COMPASS)) {
            new GUI("Game Selector",3,
                    false, new ItemButton(11, Items.get(Component.text("Basketball").color(Colour.partix()),Material.SLIME_BALL), p -> {
                        Athlete athlete = AthleteManager.get(player.getUniqueId());
                        attemptJoin(athlete, athletes -> {
                            for (Athlete a : athletes) {
                                Hub.basketballLobby.join(a);
                            }
                        });
                    }),
                    new ItemButton(13, Items.get(Component.text("Coming Soon").color(Colour.partix()),Material.BARRIER), p -> {
                        p.sendMessage("Coming soon");
                    }),
                    new ItemButton(15, Items.get(Component.text("Coming Soon").color(Colour.partix()),Material.BARRIER), p -> {
                        p.sendMessage("Coming soon");
                    })).openInventory(player);
        }
        if (itemStack.getType().equals(Material.KNOWLEDGE_BOOK)) {
            new GUI(PlayerDb.getName(player.getUniqueId()) +"'s Statistics",3,
                    false, new ItemButton(11, Items.get(Component.text("Basketball").color(Colour.partix()),Material.SLIME_BALL, 1,
                            "§r§fMVPs: §e"+ BasketballDb.get(player.getUniqueId(), BasketballDb.Stat.MVP),
                            "§r§7Games Won: §e"+ BasketballDb.get(player.getUniqueId(), BasketballDb.Stat.WINS),
                            "§r§7Games Lost: §e"+ BasketballDb.get(player.getUniqueId(), BasketballDb.Stat.LOSSES),
                            "§r§7Total Points: §e"+ BasketballDb.get(player.getUniqueId(), BasketballDb.Stat.POINTS),
                            "§r§7Total Threes: §e"+ BasketballDb.get(player.getUniqueId(), BasketballDb.Stat.THREES)
                            ), p -> {
                        // do nothing
                    }),
                    new ItemButton(13, Items.get(Component.text("Coming Soon").color(Colour.partix()),Material.BARRIER), p -> {
                        p.sendMessage("Coming soon");
                    }),
                    new ItemButton(15, Items.get(Component.text("Coming Soon").color(Colour.partix()),Material.BARRIER), p -> {
                        p.sendMessage("Coming soon");
                    })).openInventory(player);
        }
        if (itemStack.getType().equals(Material.BEACON)) {
            new GUI("Custom Games > Select Type",3,
                    true, new ItemButton(11, Items.get(Component.text("Basketball").color(Colour.partix()),Material.SLIME_BALL), p -> {
                        List<BasketballGame> games = Hub.basketballLobby.getGames().stream().filter(g -> g.owner != null).toList();
                        ItemButton[] buttons = new ItemButton[48];
                        if (games.size() > 0) {
                            for (int x = 0; x < 45; x++) {
                                if (x < games.size()) {
                                    BasketballGame g = games.get(x);
                                    buttons[x] = new ItemButton(x, Items.getPlayerHead(g.owner), pl -> {
                                        Athlete athlete = AthleteManager.get(pl.getUniqueId());
                                        g.join(athlete);
                                        g.joinTeam(player, GoalGame.Team.SPECTATOR);
                                    });
                                }
                            }
                        }
                        buttons[46] = new ItemButton(49, Items.get(Component.text("Create Custom Game"),Material.EMERALD,1," ","§6Cost: " + (player.hasPermission("rank.pro") ? "§bFREE §7(with PRO!)" : "§f500 Coins §7(FREE with PRO)"),"§6§lCLICK TO CREATE!"), pla -> {
                            int coins = PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.COINS);

                            /*if (player.hasPermission("rank.pro")) {
                                if ((games.stream().map(g -> g.owner).toList().contains(player.getUniqueId()))) {
                                    player.playSound(player.getLocation(),Sound.ENTITY_ARMOR_STAND_BREAK,SoundCategory.MASTER,100f,1f);
                                    player.sendMessage(Message.alreadyCreatedGame());
                                    return;
                                }
                            } else if (coins < 500) {
                                if ((games.stream().map(g -> g.owner).toList().contains(player.getUniqueId()))) {
                                    player.playSound(player.getLocation(),Sound.ENTITY_ARMOR_STAND_BREAK,SoundCategory.MASTER,100f,1f);
                                    player.sendMessage(Message.alreadyCreatedGame());
                                    return;
                                } else {
                                    player.sendMessage(Message.needCoins(500-coins));
                                    return;
                                }
                            }
                             */

                            attemptJoin(AthleteManager.get(player.getUniqueId()), athletes -> {

                                boolean pro = player.hasPermission("rank.pro");

                                if (!pro) {
                                    PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, 500);
                                    player.sendMessage(Message.purchaseSuccess("Custom Game Server", 500));
                                } else {
                                    player.sendMessage(Message.purchaseSuccess("Custom Game Server", 0));
                                }

                                BasketballGame game = Hub.basketballLobby.findAvailableGame(true);
                                game.owner = player.getUniqueId();
                                game.join(athletes);
                                Material[] backdrop = AthleteManager.get(player.getUniqueId()).getBorder().get();
                                game.backdrops(backdrop, backdrop);

                            });
                        });
                        new GUI("Custom Games > Basketball",6, false, buttons
                        ).openInventory(player);
                    }),
                    new ItemButton(13, Items.get(Component.text("Coming Soon").color(Colour.partix()),Material.BARRIER), p -> {
                        p.sendMessage("Coming soon");
                    }),
                    new ItemButton(15, Items.get(Component.text("Coming Soon").color(Colour.partix()),Material.BARRIER), p -> {
                        p.sendMessage("Coming soon");
                    })).openInventory(player);
        }
        if (itemStack.getType().equals(Material.EMERALD)) {
            int coins = PlayerDb.get(player.getUniqueId(), PlayerDb.Stat.COINS);
            new GUI("Daily Item Shop | "+ ItemShop.getTimeRemaining(),5,
                    true, new ItemButton(11, ItemShop.defaultTrail.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.defaultTrail;
                                int cost = holder.rarity.getCost();
                                if (coins >= cost) {
                                    Perm.add(player,holder.permission);
                                    PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                    player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                    player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                } else {
                                    player.sendMessage(Message.needCoins(cost - coins));
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    new ItemButton(13, ItemShop.defaultExplosion.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.defaultExplosion;
                                int cost = holder.rarity.getCost();
                                if (coins >= cost) {
                                    Perm.add(player,holder.permission);
                                    PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                    player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                    player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                } else {
                                    player.sendMessage(Message.needCoins(cost - coins));
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    new ItemButton(15, ItemShop.defaultBorder.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.defaultBorder;
                                int cost = holder.rarity.getCost();
                                if (coins >= cost) {
                                    Perm.add(player,holder.permission);
                                    PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                    player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                    player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                } else {
                                    player.sendMessage(Message.needCoins(cost - coins));
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    // VIP
                    new ItemButton(37, ItemShop.vipTrail.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.vipTrail;
                                int cost = holder.rarity.getCost();
                                if (player.hasPermission("rank.vip")) {
                                    if (coins >= cost) {
                                        Perm.add(player, holder.permission);
                                        PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                        player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                        player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                    } else {
                                        player.sendMessage(Message.needCoins(cost - coins));
                                        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(Message.needVIP());
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    new ItemButton(38, ItemShop.vipExplosion.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.vipExplosion;
                                int cost = holder.rarity.getCost();
                                if (player.hasPermission("rank.vip")) {
                                    if (coins >= cost) {
                                        Perm.add(player, holder.permission);
                                        PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                        player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                        player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                    } else {
                                        player.sendMessage(Message.needCoins(cost - coins));
                                        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(Message.needVIP());
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    new ItemButton(39, ItemShop.vipBorder.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.vipBorder;
                                int cost = holder.rarity.getCost();
                                if (player.hasPermission("rank.vip")) {
                                    if (coins >= cost) {
                                        Perm.add(player, holder.permission);
                                        PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                        player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                        player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                    } else {
                                        player.sendMessage(Message.needCoins(cost - coins));
                                        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(Message.needVIP());
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    // PRO
                    new ItemButton(41, ItemShop.proTrail.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.proTrail;
                                int cost = holder.rarity.getCost();
                                if (player.hasPermission("rank.pro")) {
                                    if (coins >= cost) {
                                        Perm.add(player, holder.permission);
                                        PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                        player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                        player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                    } else {
                                        player.sendMessage(Message.needCoins(cost - coins));
                                        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(Message.needVIP());
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    new ItemButton(42, ItemShop.proExplosion.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.proExplosion;
                                int cost = holder.rarity.getCost();
                                if (player.hasPermission("rank.pro")) {
                                    if (coins >= cost) {
                                        Perm.add(player, holder.permission);
                                        PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                        player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                        player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                    } else {
                                        player.sendMessage(Message.needCoins(cost - coins));
                                        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(Message.needPRO());
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),
                    new ItemButton(43, ItemShop.proBorder.getGUIItem(),
                            p -> {
                                CosmeticHolder holder = ItemShop.proBorder;
                                int cost = holder.rarity.getCost();
                                if (player.hasPermission("rank.pro")) {
                                    if (coins >= cost) {
                                        Perm.add(player, holder.permission);
                                        PlayerDb.remove(player.getUniqueId(), PlayerDb.Stat.COINS, coins);
                                        player.sendMessage(Message.purchaseSuccess(holder.name, holder.rarity.getCost()));
                                        player.playSound(player.getLocation(), holder.rarity.equals(CosmeticRarity.LEGENDARY) ? Sound.UI_TOAST_CHALLENGE_COMPLETE : Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);
                                    } else {
                                        player.sendMessage(Message.needCoins(cost - coins));
                                        player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                    }
                                } else {
                                    player.sendMessage(Message.needPRO());
                                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 1f, 1f);
                                }
                            }),

                    new ItemButton(28, Items.get(Component.text("§a§lVIP ITEM SHOP"),Material.LIME_STAINED_GLASS_PANE), p -> player.sendMessage(Message.needVIP())),
                    new ItemButton(29, Items.get(Component.text("§a§lVIP ITEM SHOP"),Material.LIME_STAINED_GLASS_PANE), p -> player.sendMessage(Message.needVIP())),
                    new ItemButton(30, Items.get(Component.text("§a§lVIP ITEM SHOP"),Material.LIME_STAINED_GLASS_PANE), p -> player.sendMessage(Message.needVIP())),
                    new ItemButton(32, Items.get(Component.text("§6§lPRO ITEM SHOP"),Material.ORANGE_STAINED_GLASS_PANE), p -> player.sendMessage(Message.needPRO())),
                    new ItemButton(33, Items.get(Component.text("§6§lPRO ITEM SHOP"),Material.ORANGE_STAINED_GLASS_PANE), p -> player.sendMessage(Message.needPRO())),
                    new ItemButton(34, Items.get(Component.text("§6§lPRO ITEM SHOP"),Material.ORANGE_STAINED_GLASS_PANE), p -> player.sendMessage(Message.needPRO()))

                    ).openInventory(player);
        } else if (itemStack.getType().equals(Material.ENDER_CHEST)){
            new CosmeticGUI(player);
        }
    }

}
