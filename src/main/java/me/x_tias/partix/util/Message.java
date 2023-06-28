package me.x_tias.partix.util;

import me.x_tias.partix.plugin.athlete.Athlete;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class Message {

    public static Component sendMessage(Athlete athlete, Component component) {
        Player player = athlete.getPlayer();
        TextColor chatColor = player.hasPermission("rank.admin") ? Colour.adminText() : player.hasPermission("rank.pro") ? Colour.premiumText() : Colour.text();
        return athlete.getName().append(component.color(chatColor));
    }

    public static Component joinParty(String name) {
        return Text.get(
                Text.section("Party > ", Colour.title()),
                Text.section("Welcome ", Colour.text()),
                Text.section(name, Colour.bold()),
                Text.section(" to the party!", Colour.text())
                );
    }

    public static Component onlyPartyLeader() {
        return Text.get(
                Text.section("Party > ", Colour.title()),
                Text.section("Only your ", Colour.text()),
                Text.section("party leader", Colour.bold()),
                Text.section(" can do this!", Colour.text())
        );
    }

    public static Component joinServer(Player player) {
        return Text.get(
                Text.section("Join > ", Colour.title()),
                Text.section(player.getName(), Colour.bold()),
                Text.section(" has joined", player.hasPermission("rank.admin") ? Colour.adminText() : player.hasPermission("rank.pro") ? Colour.premiumText() : Colour.text())
        );
    }

    public static Component quitServer(Player player) {
        return Text.get(
                Text.section("Quit > ", Colour.title()),
                Text.section(player.getName(), Colour.bold()),
                Text.section(" has left", player.hasPermission("rank.admin") ? Colour.adminText() : player.hasPermission("rank.pro") ? Colour.premiumText() : Colour.text())
        );
    }


    public static Component partyChat(String text) {
        return Text.get(
                Text.section("Party > ", Colour.title()),
                Text.section(text, Colour.text())
        );
    }

    public static Component partyChat(String name, String text) {
        return Text.get(
                Text.section("Party > ", Colour.title()),
                Text.section(name, Colour.bold()),
                Text.section(": "+text, Colour.text())
        );
    }

    public static Component needsRank() {
        return Text.get(
                Text.section("Rank > ", Colour.title()),
                Text.section("Your rank ", Colour.text()),
                Text.section("does not ", Colour.deny()),
                Text.section("allow you to do this!", Colour.text())
        );
    }

    public static Component itemName(String name, String key, Player player) {
        return Text.get(
                Text.section(name, Colour.title()),
                Text.section(" (", Colour.border()),
                Text.section(player.getName().startsWith(".") ? Component.text(key.equalsIgnoreCase("key.use") ? "Right Click" : "Left Click") : Component.keybind(key), Colour.border()),
                Text.section(")", Colour.border())
                );
    }

    public static Component alreadyCreatedGame() {
        return Text.get(
                Text.section("Custom > ", Colour.title()),
                Text.section("You already have an ", Colour.text()),
                Text.section("active custom game ", Colour.deny()),
                Text.section("running!", Colour.text())
        );
    }

    public static Component joinTeam(String team) {
        return Text.get(
                Text.section("Team > ", Colour.title()),
                Text.section("You have joined the ", Colour.text()),
                Text.section(team, Colour.border()),
                Text.section(" team!", Colour.text())
        );
    }

    public static Component disabled() {
        return Text.get(
                Text.section("Team > ", Colour.title()),
                Text.section("This is currently ", Colour.text()),
                Text.section("disabled", Colour.deny()),
                Text.section(" here..", Colour.text())
        );
    }

    public static Component onlyOwner() {
        return Text.get(
                Text.section("Game > ", Colour.title()),
                Text.section("Only this arenas ", Colour.text()),
                Text.section("owner", Colour.deny()),
                Text.section(" can do this!", Colour.text())
        );
    }

    public static Component needCoins(int difference) {
        return Text.get(
                Text.section("Shop > ", Colour.title()),
                Text.section("You are ", Colour.text()),
                Text.section(difference+" coins", Colour.deny()),
                Text.section(" short of being able to purchase this..", Colour.text())
        );
    }

    public static Component receiveCoins(int amount) {
        return Text.get(
                Text.section("Coins > ", Colour.title()),
                Text.section("You have received ", Colour.text()),
                Text.section(amount+" coins", Colour.allow()),
                Text.section("!", Colour.text())
        );
    }

    public static Component needVIP() {
        return Text.get(
                Text.section("Rank > ", Colour.title()),
                Text.section("You need ", Colour.text()),
                Text.section("VIP", Colour.bold()),
                Text.section(" rank to do this! ", Colour.text()),
                Text.section(" -> ", Colour.bold()),
                Text.section("store.partix.net", Colour.partix())
        );
    }

    public static Component purchaseSuccess(String item, int cost) {
        return Text.get(
                Text.section("Shop > ", Colour.title()),
                Text.section("Successfully purchased ", Colour.text()),
                Text.section(item, Colour.bold()),
                Text.section("! (-"+cost+")", Colour.text())
        );
    }

    public static Component selectCosmetic(String item) {
        return Text.get(
                Text.section("Cosmetic > ", Colour.title()),
                Text.section("Selected ", Colour.text()),
                Text.section(item, Colour.bold()),
                Text.section("!", Colour.text())
        );
    }

    public static Component needPRO() {
        return Text.get(
                Text.section("Rank > ", Colour.title()),
                Text.section("You need ", Colour.text()),
                Text.section("PRO", Colour.bold()),
                Text.section(" rank to do this! ", Colour.text()),
                Text.section(" -> ", Colour.bold()),
                Text.section("store.partix.net", Colour.partix())
        );
    }

    public static Component cantDoThisNow() {
        return Text.get(
                Text.section("Game > ", Colour.title()),
                Text.section("You ", Colour.text()),
                Text.section("cannot", Colour.deny()),
                Text.section(" do this right now!", Colour.text())
        );
    }

    public static Component seasonWin(String name) {
        return Text.get(
                Text.section("Season > ", Colour.title()),
                Text.section(name, Colour.bold()),
                Text.section(" has ", Colour.partix()),
                Text.section("won", Colour.allow()),
                Text.section(" the Gold Division!", Colour.partix())
        );
    }

    public static Component seasonPromote() {
        return Text.get(
                Text.section("Season > ", Colour.title()),
                Text.section("You have been ", Colour.partix()),
                Text.section("promoted", Colour.allow()),
                Text.section(" to the Gold Division!", Colour.partix())
        );
    }

    public static Component seasonDemote() {
        return Text.get(
                Text.section("Season > ", Colour.title()),
                Text.section("You have been ", Colour.text()),
                Text.section("demoted", Colour.allow()),
                Text.section(" to the Silver Division..", Colour.text())
        );
    }


    public static Component fullTeam() {
        return Text.get(
                Text.section("Team > ", Colour.title()),
                Text.section("Your team is currently ", Colour.text()),
                Text.section("full", Colour.deny()),
                Text.section("!", Colour.text())
        );
    }

    public static Component settingChange(String setting, String set) {
        return Text.get(
                Text.section("Settings > ", Colour.title()),
                Text.section(setting, Colour.bold()),
                Text.section(" has been changed to ", Colour.text()),
                Text.section(set, Colour.bold()),
                Text.section(".", Colour.text())
                );
    }

    public static Component gameOver(String mvp, int homeScore, int awayScore) {
        return Text.get(
                Text.section("==============================\n ", Colour.border()),
                Text.section("    Game Over ‣ play.partix.net         \n", Colour.title()),
                Text.section("------------------------------\n", Colour.border()),
                Text.section("       Final Score: ", Colour.premiumText()),
                Text.section(Math.max(homeScore, awayScore)+"-"+Math.min(homeScore, awayScore)+"\n", Colour.partix()),
                Text.section("       MVP: ", Colour.premiumText()),
                Text.section(mvp+"\n", Colour.partix()),
                Text.section("==============================\n ", Colour.border())

                );
    }



}
