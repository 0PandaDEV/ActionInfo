package tk.pandadev.actioninfo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.actioninfo.commands.ActionCommand;
import tk.pandadev.actioninfo.commands.ActionGuiCommand;
import tk.pandadev.actioninfo.listener.JoinListener;
import tk.pandadev.actioninfo.utils.ActionBar;

public final class Main extends JavaPlugin {

    private static Main instance;

    private static final String prefix = "§x§F§F§B§F§0§0§lActionInfo §8» ";

    public static long mspt;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage(prefix + "§aEnabled");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getConfig().get(player.getUniqueId() + ".active") == null) {
                getConfig().set(player.getUniqueId() + ".active", true);
            }
            if (getConfig().get(player.getUniqueId() + ".tps") == null) {
                getConfig().set(player.getUniqueId() + ".tps", true);
            }
            if (getConfig().get(player.getUniqueId() + ".mspt") == null) {
                getConfig().set(player.getUniqueId() + ".mspt", false);
            }
            if (getConfig().get(player.getUniqueId() + ".cpu") == null) {
                getConfig().set(player.getUniqueId() + ".cpu", true);
            }
            if (getConfig().get(player.getUniqueId() + ".ram") == null) {
                getConfig().set(player.getUniqueId() + ".ram", false);
            }
            if (getConfig().get(player.getUniqueId() + ".ping") == null) {
                getConfig().set(player.getUniqueId() + ".ping", false);
            }

            saveConfig();
        }
        if (Bukkit.getOnlinePlayers().size() == 0) {
            ActionBar.stopActionBar();
        }
        if (Bukkit.getOnlinePlayers().size() >= 1) {
            ActionBar.startActionBar();
        }

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        getCommand("actioninfo").setExecutor(new ActionCommand());
        getCommand("actiongui").setExecutor(new ActionGuiCommand());


        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            new BukkitRunnable() {
                @Override
                public void run() {

                    long oldTime = System.currentTimeMillis();
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            long newTime = System.currentTimeMillis();
                            mspt = newTime - oldTime;
                        }
                    }, 1);


                }
            }.runTaskTimer(Main.getInstance(), 0, 20);
        });
    }

    @Override
    public void onDisable() {
        instance = null;
        ActionBar.stopActionBar();
    }

    public static Main getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }


}
