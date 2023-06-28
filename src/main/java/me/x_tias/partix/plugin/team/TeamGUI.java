package me.x_tias.partix.plugin.team;

import me.x_tias.partix.plugin.gui.GUI;
import me.x_tias.partix.plugin.gui.ItemButton;

import java.util.function.Consumer;

public class TeamGUI {

    public static GUI get(String team, Consumer<BaseTeam> run) {
        return new GUI("Select "+team+" Team: ",2,
                false, new ItemButton(0, new TeamPlains().block, (player) -> {run.accept(new TeamPlains());}),
                new ItemButton(1, new TeamSwamp().block, (player) -> {run.accept(new TeamSwamp());}),
                new ItemButton(2, new TeamOcean().block, (player) -> {run.accept(new TeamOcean());}),
                new ItemButton(3, new TeamBadlands().block, (player) -> {run.accept(new TeamBadlands());}),
                new ItemButton(4, new TeamDesert().block, (player) -> {run.accept(new TeamDesert());}),
                new ItemButton(5, new TeamTiaga().block, (player) -> {run.accept(new TeamTiaga());}),
                new ItemButton(6, new TeamIceSpikes().block, (player) -> {run.accept(new TeamIceSpikes());}),
                new ItemButton(7, new TeamFortress().block, (player) -> {run.accept(new TeamFortress());}),
                new ItemButton(8, new TeamCity().block, (player) -> {run.accept(new TeamCity());}),
                new ItemButton(10, new TeamJungle().block, (player) -> {run.accept(new TeamJungle());}),
                new ItemButton(11, new TeamSandTemple().block, (player) -> {run.accept(new TeamSandTemple());}),
                new ItemButton(12, new TeamBastian().block, (player) -> {run.accept(new TeamBastian());}),
                new ItemButton(13, new TeamAncientCity().block, (player) -> {run.accept(new TeamAncientCity());}),
                new ItemButton(14, new TeamFlowerForest().block, (player) -> {run.accept(new TeamFlowerForest());}),
                new ItemButton(15, new TeamWarpedForest().block, (player) -> {run.accept(new TeamWarpedForest());}),
                new ItemButton(16, new TeamCrimsonForest().block, (player) -> {run.accept(new TeamCrimsonForest());})
                );
    }

}
