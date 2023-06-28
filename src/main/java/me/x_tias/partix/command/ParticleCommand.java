package me.x_tias.partix.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.x_tias.partix.Partix;
import me.x_tias.partix.plugin.athlete.Athlete;
import me.x_tias.partix.plugin.athlete.AthleteManager;
import me.x_tias.partix.plugin.athlete.RenderType;
import me.x_tias.partix.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("particle")
public class ParticleCommand extends BaseCommand {

    @Default
    public void onParticleCommand(CommandSender sender) {
        if (sender instanceof Player player) {
            Athlete athlete = AthleteManager.get(player.getUniqueId());
            RenderType render = athlete.getRenderType();
            if (render.equals(RenderType.SLIME)) {
                athlete.setRenderType(RenderType.REMOVE_SLIME);
                player.sendMessage(Message.settingChange("Render as particles","yes"));
                Bukkit.getScheduler().runTaskLater(Partix.getInstance(), () -> {
                    athlete.setRenderType(RenderType.PARTICLE);
                }, 2);
            } else {
                athlete.setRenderType(RenderType.SLIME);
                player.sendMessage(Message.settingChange("Render as particles","no"));
            }
        }
    }

}
