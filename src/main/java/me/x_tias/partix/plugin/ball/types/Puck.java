package me.x_tias.partix.plugin.ball.types;

import me.x_tias.partix.plugin.ball.Ball;
import me.x_tias.partix.plugin.ball.BallType;
import me.x_tias.partix.util.Colour;
import me.x_tias.partix.util.Position;
import me.x_tias.partix.util.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Puck extends Ball {
    public Puck(Location location) {
        super(location, BallType.PUCK, 0.4,0.1, 0.05, 0.03, 0.19, 0.74,0.05,0.53,true, true, 1.5, Color.BLACK, Color.BLACK);
        delay = 5;
        handYaw = 30;
        handModifier = 0;
    }

    @Override
    public Component getControls(Player player) {
        String leftClick = "Pass";
        String rightClick = "Shoot";
        String dropItem = "Side Step";
        String swapHand = "Swap Hand";
        Component lc = Component.text("[", Colour.border()).append(Component.keybind("key.attack", Colour.title()).append(Component.text("]",Colour.border())).append(Component.text(" "+leftClick+", ",Colour.text())));
        Component rc = Component.text("[",Colour.border()).append(Component.keybind("key.use", Colour.title()).append(Component.text("]",Colour.border())).append(Component.text(" "+rightClick+", ",Colour.text())));
        Component di = Component.text("[",Colour.border()).append(Component.keybind("key.drop", Colour.title()).append(Component.text("]",Colour.border())).append(Component.text(" "+dropItem+", ",Colour.text())));
        Component sh = Component.text("[",Colour.border()).append(Component.keybind("key.swapOffhand", Colour.title()).append(Component.text("]",Colour.border())).append(Component.text(" "+swapHand+", ",Colour.text())));
        return lc.append(rc).append(di).append(sh);
    }

    int handYaw;
    int handModifier;
    int delay;
    public void takePuck(Player player) {
        if (delay < 1) {
            if (getCurrentDamager() == null) {
                setDamager(player);
                delay = 20;
                return;
            }
            if (getCurrentDamager() != player) {
                setDamager(player);
                delay = 20;
            }
        }
    }

    public void pass(Player player) {
        if (delay < 1) {
            if (getCurrentDamager() != null) {
                if (player.equals(getCurrentDamager())) {
                    setHorizontal(player, Position.stabilize(player, 0.0).getDirection().multiply(1.0));
                    delay = 5;
                    giveaway();
                }
            }
        }
    }

    public void shoot(Player player) {
        if (delay < 1) {
            if (getCurrentDamager() != null) {
                if (player.equals(getCurrentDamager())) {
                    setHorizontal(player, player.getLocation().getDirection().multiply(2.0));
                    delay = 5;
                    giveaway();
                }
            }
        }
    }

    public void leftClick(Player player) {
        if (getCurrentDamager() != null) {
             pass(player);
        } else {
            if (delay < 1 ) {
                takePuck(player);
            }
        }
    }


    public boolean changeHand(Player player) {
        if (getCurrentDamager() != null) {
            if (getCurrentDamager().equals(player)) {
                if (handModifier == 0) {
                    handModifier = 8;
                } else if (handModifier == 8) {
                    handModifier = -8;
                } else if (handModifier == -8) {
                    handModifier = 8;
                }
                return true;
            }
        }
        return false;
    }

    public boolean sideStep(Player player) {
        if (getCurrentDamager() != null) {
            if (getCurrentDamager().equals(player)) {
                if (handModifier == 0) {
                    handModifier = 5;
                } else if (handModifier > 0) {
                    handModifier = -5;
                } else {
                    handModifier = 5;
                }
                setVertical(0.225);
                return true;
            }
        }
        return false;
    }

    public void giveaway() {
        removeCurrentDamager();
    }

    private void modifyHand() {
        int nextHand = handYaw + handModifier;
        if (nextHand < 40 && nextHand > -40) {
            handYaw = nextHand;
        }
    }

    public boolean collides(Player player) {
        if (delay < 1) {
            if (getLastDamager() != null) {
                player.sendMessage("collision");
                return getLastDamager() == player;
            }
        }
        return false;
    }

    @Override
    public void modify() {
        if (getCurrentDamager() != null) {
            Player poss = getCurrentDamager();
            modifyHand();
            setHorizontal(Position.stabilize(poss,handYaw, 1.25));
        }
        if (delay > 0) {
            delay -= 1;
        }
    }
}
