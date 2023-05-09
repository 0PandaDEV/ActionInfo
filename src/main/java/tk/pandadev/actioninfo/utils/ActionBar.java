package tk.pandadev.actioninfo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tk.pandadev.actioninfo.Main;

public class ActionBar {

    public static void showActionBar(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Server server = Bukkit.getServer();
            Runtime runtime = Runtime.getRuntime();

        });
    }

}
