package tk.pandadev.actioninfo.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tk.pandadev.actioninfo.Main;
import tk.pandadev.actioninfo.utils.Utils;

public class Guis {

    public static void mainGui(Player player) {
        FileConfiguration config = Main.getInstance().getConfig();

        Gui gui = Gui.gui()
                .disableAllInteractions()
                .rows(5)
                .title(Component.text("ActionInfo"))
                .create();

        gui.setItem(2, 5, ItemBuilder.from(config.getBoolean(player.getUniqueId() + ".active") ? Material.LIME_DYE : Material.RED_DYE).name(Component.text(config.getBoolean(player.getUniqueId() + ".active") ? "§aActionBar is visible" : "§cActionBar is hidden")).asGuiItem(event -> {
            config.set(player.getUniqueId() + ".active", !config.getBoolean(player.getUniqueId() + ".active"));
            if (config.getBoolean(player.getUniqueId() + ".active")) {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            }
            mainGui(player);
        }));

        /////////////////////////

        gui.setItem(4, 1, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU2Yzk0NjE5MDMxMjMxNjhjZTY2N2VhZDdlYTU2YTUxNjEzMDk3MDQ5YmE2NDc4MzJiMzcyMmFmZmJlYjYzNiJ9fX0=")).name(Component.text(config.getBoolean(player.getUniqueId() + ".cpu") ? "§aCPU" : "§cCPU")).asGuiItem(event -> {
            config.set(player.getUniqueId() + ".cpu", !config.getBoolean(player.getUniqueId() + ".cpu"));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + ".cpu")) {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                if (!config.getBoolean(player.getUniqueId() + ".active")) {
                    player.sendMessage(Main.getPrefix() + "§7Remember turning enabling the actionbar with §a/actioninfo §7or §a/ai");
                }
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            }
            mainGui(player);
        }));

        gui.setItem(4, 3, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzMzYjFhZDUxNzA5NjU2NGMwYThhZmQyOGMxZWQ2OWJiNTUzNzc1NWNhNzdmMGI0NWMxZTVkYjc4NjY1Mzg0ZiJ9fX0=")).name(Component.text(config.getBoolean(player.getUniqueId() + ".ram") ? "§aRAM" : "§cRAM")).asGuiItem(event -> {
            config.set(player.getUniqueId() + ".ram", !config.getBoolean(player.getUniqueId() + ".ram"));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + ".ram")) {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                if (!config.getBoolean(player.getUniqueId() + ".active")) {
                    player.sendMessage(Main.getPrefix() + "§7Remember turning enabling the actionbar with §a/actioninfo §7or §a/ai");
                }
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            }
            mainGui(player);
        }));

        gui.setItem(4, 5, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg5OTI4YjU4YTg3ZWMxODRmMTY4NjkxNTQ0Yjc4YmM2MmM5YWY0ZGY3ZmFlYTIxOTQ0YmIzMjFlNWFmNjEyIn19fQ==")).name(Component.text(config.getBoolean(player.getUniqueId() + ".tps") ? "§aTPS" : "§cTPS")).asGuiItem(event -> {
            config.set(player.getUniqueId() + ".tps", !config.getBoolean(player.getUniqueId() + ".tps"));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + ".tps")) {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                if (!config.getBoolean(player.getUniqueId() + ".active")) {
                    player.sendMessage(Main.getPrefix() + "§7Remember turning enabling the actionbar with §a/actioninfo §7or §a/ai");
                }
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            }
            mainGui(player);
        }));

        gui.setItem(4, 7, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThhZGNlYjBiZDg5MjcxYWY2MmQwMTU3NDc1NTJhMGRiNTI0OWI1MTU1NGQzZDFlNzgyOGYxZWRlYmNiNjMxZSJ9fX0=")).name(Component.text(config.getBoolean(player.getUniqueId() + ".mspt") ? "§aMSPT" : "§cMSPT")).asGuiItem(event -> {
            config.set(player.getUniqueId() + ".mspt", !config.getBoolean(player.getUniqueId() + ".mspt"));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + ".mspt")) {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                if (!config.getBoolean(player.getUniqueId() + ".active")) {
                    player.sendMessage(Main.getPrefix() + "§7Remember turning enabling the actionbar with §a/actioninfo §7or §a/ai");
                }
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            }
            mainGui(player);
        }));

        gui.setItem(4, 9, ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzEzNWYyOGFjZjMzZTA3YTgwMWNmY2Q5YWM1ZjZlZDY4OWIzMGFkYjBjZmE0ZDFhYWNiOTU5ZTZhNjEwNjQ3MSJ9fX0=")).name(Component.text(config.getBoolean(player.getUniqueId() + ".ping") ? "§aPing" : "§cPing")).asGuiItem(event -> {
            config.set(player.getUniqueId() + ".ping", !config.getBoolean(player.getUniqueId() + ".ping"));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + ".ping")) {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 100, 1);
                if (!config.getBoolean(player.getUniqueId() + ".active")) {
                    player.sendMessage(Main.getPrefix() + "§7Remember turning enabling the actionbar with §a/actioninfo §7or §a/ai");
                }
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 100, 1);
            }
            mainGui(player);
        }));

        gui.open(player);
    }
}
