package tk.pandadev.actioninfo.utils;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.actioninfo.Main;

public class ActionBar {

  public static BukkitRunnable runnable;

  public static void stopActionBar() {
    if (runnable == null) {
      return;
    }
    runnable.cancel();
  }

  public static void startActionBar() {
    run();
  }

  private static void run() {
    FileConfiguration config = Main.getInstance().getConfig();
    Bukkit
      .getScheduler()
      .runTaskAsynchronously(
        Main.getInstance(),
        () -> {
          runnable =
            new BukkitRunnable() {
              @Override
              public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                  if (config.getBoolean(player.getUniqueId() + ".active")) {
                    List<String> actionBarText = new ArrayList<>();
                    if (config.getBoolean(player.getUniqueId() + ".cpu")) {
                      actionBarText.add(getCPU());
                    }
                    if (config.getBoolean(player.getUniqueId() + ".ram")) {
                      actionBarText.add(getRAM());
                    }
                    if (config.getBoolean(player.getUniqueId() + ".tps")) {
                      actionBarText.add(getTPS());
                    }
                    if (config.getBoolean(player.getUniqueId() + ".mspt")) {
                      actionBarText.add(getMSPT());
                    }
                    if (config.getBoolean(player.getUniqueId() + ".ping")) {
                      actionBarText.add(getPing(player));
                    }
                    if (
                      !config.getBoolean(player.getUniqueId() + ".cpu") &&
                      !config.getBoolean(player.getUniqueId() + ".ram") &&
                      !config.getBoolean(player.getUniqueId() + ".tps") &&
                      !config.getBoolean(player.getUniqueId() + ".mspt") &&
                      !config.getBoolean(player.getUniqueId() + ".ping")
                    ) {
                      config.set(player.getUniqueId() + ".active", false);
                    }
                    String joinedText = String.join("  ", actionBarText);
                    player
                      .spigot()
                      .sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent(joinedText)
                      );
                  }
                }
              }
            };
          runnable.runTaskTimer(Main.getInstance(), 0, 20);
        }
      );
  }

  private static String getRAM() {
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory();
    long allocatedMemory = runtime.totalMemory();
    long freeMemory = runtime.freeMemory();
    long usedMemory = allocatedMemory - freeMemory;

    double memoryUsage = (double) usedMemory / maxMemory;
    String memoryUsageString = String.format("%.1f", memoryUsage * 100) + "%";

    String usedMemoryString;
    if (usedMemory < 1024 * 1024 * 1024) {
      usedMemoryString =
        String.format("%.1f", usedMemory / (1024.0 * 1024.0)) + "M";
    } else {
      usedMemoryString =
        String.format("%.1f", usedMemory / (1024.0 * 1024.0 * 1024.0)) + "G";
    }

    String maxMemoryString;
    if (maxMemory < 1024 * 1024 * 1024) {
      maxMemoryString =
        String.format("%.1f", maxMemory / (1024.0 * 1024.0)) + "M";
    } else {
      maxMemoryString =
        String.format("%.1f", maxMemory / (1024.0 * 1024.0 * 1024.0)) + "G";
    }

    return "§7RAM: §a" + usedMemoryString + "§7/§a" + maxMemoryString;
  }

  private static String getCPU() {
    double cpuUsage =
      (
        (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()
      ).getSystemCpuLoad() *
      100;
    DecimalFormat df = new DecimalFormat("##.#");
    String cpu = df.format(cpuUsage);
    String color = "§a";
    try {
      org.bukkit.Server server = org.bukkit.Bukkit.getServer();
      Object minecraftServer = server
        .getClass()
        .getMethod("getServer")
        .invoke(server);
      double[] recentTps = (double[]) minecraftServer
        .getClass()
        .getField("recentTps")
        .get(minecraftServer);
      String tpsColor = "";
      if (recentTps.length > 0) {
        double tps = recentTps[0];
        if (tps < 15.0) {
          tpsColor = "§c";
        } else if (tps < 17.0) {
          tpsColor = "§e";
        } else {
          tpsColor = "§a";
        }
      }
      return (
        "§7CPU: " +
        color +
        cpu +
        "%" +
        "  " +
        "§7TPS: " +
        tpsColor +
        recentTps[0]
      );
    } catch (
      NoSuchMethodException
      | InvocationTargetException
      | IllegalAccessException
      | NoSuchFieldException err
    ) {
      System.out.println(err);
      return "";
    }
  }

  private static String getTPS() {
    double tpsCurrent = 0;
    try {
      Server server = Bukkit.getServer();
      Object minecraftServer = server
        .getClass()
        .getMethod("getServer")
        .invoke(server);
      double[] recentTps = (double[]) minecraftServer
        .getClass()
        .getField("recentTps")
        .get(minecraftServer);
      tpsCurrent = Math.min(20, Math.round(recentTps[0] * 10.0) / 10.0);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "§7TPS: §a" + tpsCurrent;
  }

  private static String getMSPT() {
    double mspt = 0.0D;
    try {
      org.bukkit.Server server = org.bukkit.Bukkit.getServer();
      Object minecraftServer = server
        .getClass()
        .getMethod("getServer")
        .invoke(server);
      double[] recentTps = (double[]) minecraftServer
        .getClass()
        .getField("recentTps")
        .get(minecraftServer);
      if (recentTps.length > 0 && recentTps[1] > 0.0D) {
        mspt = recentTps[1];
      }
    } catch (
      NoSuchMethodException
      | InvocationTargetException
      | IllegalAccessException
      | NoSuchFieldException err
    ) {
      System.out.println(err);
    }
    return "§7MSPT: §a" + mspt;
  }

  private static String getPing(Player player) {
    return "§7Ping: §a" + player.getPing() + "ms";
  }
}
