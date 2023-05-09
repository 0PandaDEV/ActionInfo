package tk.pandadev.actioninfo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.pandadev.actioninfo.commands.ActionCommand;
import tk.pandadev.actioninfo.listener.JoinListener;

public final class Main extends JavaPlugin {

    private static Main instance;

    private static final String prefix = "§x§F§F§B§F§0§0§lActionInfo §8» ";

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage(prefix + "§aEnabled");

        for (Player player : Bukkit.getOnlinePlayers()){
            if (getConfig().get(player.getUniqueId().toString() + ".active") == null){
                getConfig().set(player.getUniqueId().toString() + ".active", false);
                getConfig().set(player.getUniqueId().toString() + ".tps", false);
                getConfig().set(player.getUniqueId().toString() + ".mspt", false);
                getConfig().set(player.getUniqueId().toString() + ".cpu", false);
                getConfig().set(player.getUniqueId().toString() + ".ram", false);
            }

            saveConfig();
        }

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("actioninfo").setExecutor(new ActionCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }
}
