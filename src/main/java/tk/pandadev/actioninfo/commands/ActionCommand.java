package tk.pandadev.actioninfo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tk.pandadev.actioninfo.Main;

import java.util.ArrayList;
import java.util.List;

public class ActionCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§6This command can only be run by a player!");
            return false;
        }
        Player player = (Player) (sender);

        if (args.length == 1){
            if (!args[0].equalsIgnoreCase("tps") && args[0].equalsIgnoreCase("mspt") && args[0].equalsIgnoreCase("ram") && args[0].equalsIgnoreCase("cpu")){
                player.sendMessage(Main.getPrefix() + "§cThe arguments only allow §6tps, mspt, ram and cpu!");
                return false;
            }
            FileConfiguration config = Main.getInstance().getConfig();
            config.set(player.getUniqueId() + "." + args[0], !config.getBoolean(player.getUniqueId() + "." + args[0]));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + "." + args[0])){
                player.sendMessage(Main.getPrefix() + "§a" + args[0] + " §7was activated");
            } else {
                player.sendMessage(Main.getPrefix() + "§a" + args[0] + " §7was deactivated");
            }
        } else if (args.length == 0){
            FileConfiguration config = Main.getInstance().getConfig();
            config.set(player.getUniqueId() + ".active", !config.getBoolean(player.getUniqueId() + ".active"));
            Main.getInstance().saveConfig();
            if (config.getBoolean(player.getUniqueId() + ".active")){
                player.sendMessage(Main.getPrefix() + "§7The action bar is now §avisible");
            } else {
                player.sendMessage(Main.getPrefix() + "§7The action bar is now §ahidden");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1){
            list.add("tps");
            list.add("mspt");
            list.add("ram");
            list.add("cpu");
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }

        return completerList;
    }

}