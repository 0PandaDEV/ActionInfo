package tk.pandadev.actioninfo.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.pandadev.actioninfo.Main;
import tk.pandadev.actioninfo.utils.ActionBar;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FileConfiguration config = Main.getInstance().getConfig();
        Player player = event.getPlayer();
        if (config.get(player.getUniqueId() + ".active") == null) {
            config.set(player.getUniqueId() + ".active", false);
        }
        if (config.get(player.getUniqueId() + ".tps") == null) {
            config.set(player.getUniqueId() + ".tps", false);
        }
        if (config.get(player.getUniqueId() + ".mspt") == null) {
            config.set(player.getUniqueId() + ".mspt", false);
        }
        if (config.get(player.getUniqueId() + ".cpu") == null) {
            config.set(player.getUniqueId() + ".cpu", false);
        }
        if (config.get(player.getUniqueId() + ".ram") == null) {
            config.set(player.getUniqueId() + ".ram", false);
        }
        if (config.get(player.getUniqueId() + ".ping") == null) {
            config.set(player.getUniqueId() + ".ping", false);
        }
        Main.getInstance().saveConfig();
        if (Bukkit.getOnlinePlayers().size() == 1) {
            ActionBar.startActionBar();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            ActionBar.stopActionBar();
        }
    }

}
