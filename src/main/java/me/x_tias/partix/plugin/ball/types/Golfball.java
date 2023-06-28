package me.x_tias.partix.plugin.ball.types;

import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallType;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Golfball extends Ball {
    public Golfball(Location location) {
        super(location, BallType.GOLFBALL, 0.4,0.01, 0.01, 0.035, 0.19, 0.94,0.05,0.66,true, true, 3.0, Color.WHITE, Color.WHITE);
    }

    @Override
    public Component getControls(Player player) {
        String leftClick = "";
        String rightClick = "";
        String dropItem = "";
        String swapHand = "";
        Component lc = Component.text("[", TextColor.color(0x3d3d3d)).append(Component.keybind("key.attack", TextColor.color(0x4eb317))).append(Component.text("]",TextColor.color(0x3d3d3d))).append(Component.text(" "+leftClick+"  ",TextColor.color(0xc4c91a)));
        Component rc = Component.text("[",TextColor.color(0x3d3d3d)).append(Component.keybind("key.use", TextColor.color(0x4eb317))).append(Component.text("]",TextColor.color(0x3d3d3d))).append(Component.text(" "+rightClick+"  ",TextColor.color(0xc4c91a)));
        Component di = Component.text("[",TextColor.color(0x3d3d3d)).append(Component.keybind("key.drop", TextColor.color(0x4eb317))).append(Component.text("]",TextColor.color(0x3d3d3d))).append(Component.text(" "+dropItem+"  ",TextColor.color(0xc4c91a)));
        Component sh = Component.text("[",TextColor.color(0x3d3d3d)).append(Component.keybind("key.swapOffhand", TextColor.color(0x4eb317))).append(Component.text("]",TextColor.color(0x3d3d3d))).append(Component.text(" "+swapHand+"  ",TextColor.color(0xc4c91a)));
        return lc.append(rc).append(di).append(sh);
    }

    @Override
    public void modify() {

    }
}
