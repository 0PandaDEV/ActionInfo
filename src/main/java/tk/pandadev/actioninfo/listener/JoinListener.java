package tk.pandadev.actioninfo.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.pandadev.actioninfo.Main;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        FileConfiguration config = Main.getInstance().getConfig();
        Player player = event.getPlayer();
        if (config.get(player.getUniqueId().toString() + ".active") == null){
            config.set(player.getUniqueId().toString() + ".active", false);
            config.set(player.getUniqueId().toString() + ".tps", false);
            config.set(player.getUniqueId().toString() + ".mspt", false);
            config.set(player.getUniqueId().toString() + ".cpu", false);
            config.set(player.getUniqueId().toString() + ".ram", false);
        }

        Main.getInstance().saveConfig();
    }
}
