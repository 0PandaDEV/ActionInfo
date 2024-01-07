package net.pandadev.actioninfo.commands;

import net.pandadev.actioninfo.Main;
import net.pandadev.actioninfo.guis.Guis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionGuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§6This command can only be run by a player!");
            return false;
        }
        Player player = (Player) (sender);

        if (args.length == 0) {
            Guis.mainGui(player);
        } else {
            player.sendMessage(Main.getPrefix() + "§c/actiongui or /ag");
        }

        return false;
    }

}