package me.x_tias.partix.util;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class Text {

    public static String serialize(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static Component get(Section... sections) {
        Component c = Component.empty();
        for (Section section : sections) {
            c = c.append(section.text().color(section.colour()));
        }
        return c;
    }

    public static Section section(String n, TextColor color) {
        return new Section(Component.text(n),color);
    }

    public static Section section(Component n, TextColor color) {
        return new Section(n,color);
    }


    public static Component gradient(String message, TextColor startColor, TextColor endColor, boolean bold) {
        ComponentBuilder<TextComponent, TextComponent.Builder> builder = Component.text();
        double step = 1.0 / (message.length() - 1);
        for (int i = 0; i < message.length(); i++) {
            double ratio = step * i;
            TextColor color = TextColor.lerp((float) ratio, startColor, endColor);
            if (bold) {
                builder.append(Component.text(message.charAt(i), color, TextDecoration.BOLD));
            } else {
                builder.append(Component.text(message.charAt(i), color));
            }
        }
        return builder.build();
    }

}

