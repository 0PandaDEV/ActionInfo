package tk.pandadev.actioninfo.utils;

import com.sun.management.OperatingSystemMXBean;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.actioninfo.Main;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

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
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            runnable = new BukkitRunnable() {
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
                            if (!config.getBoolean(player.getUniqueId() + ".cpu") && !config.getBoolean(player.getUniqueId() + ".ram") && !config.getBoolean(player.getUniqueId() + ".tps") && !config.getBoolean(player.getUniqueId() + ".mspt") && !config.getBoolean(player.getUniqueId() + ".ping")) {
                                config.set(player.getUniqueId() + ".active", false);
                            }
                            String joinedText = String.join("  §8-  ", actionBarText);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(joinedText));
                        }
                    }
                }
            };
            runnable.runTaskTimer(Main.getInstance(), 0, 20);
        });
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
            usedMemoryString = String.format("%.1f", usedMemory / (1024.0 * 1024.0)) + "M";
        } else {
            usedMemoryString = String.format("%.1f", usedMemory / (1024.0 * 1024.0 * 1024.0)) + "G";
        }

        String maxMemoryString;
        if (maxMemory < 1024 * 1024 * 1024) {
            maxMemoryString = String.format("%.1f", maxMemory / (1024.0 * 1024.0)) + "M";
        } else {
            maxMemoryString = String.format("%.1f", maxMemory / (1024.0 * 1024.0 * 1024.0)) + "G";
        }

        return "§7RAM: §a" + usedMemoryString + "§7/§a" + maxMemoryString;
    }


    private static String getCPU() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
        int cpuUsage = (int) Math.round(osBean.getSystemCpuLoad() * 100);

        String finalCpuUsage = null;
        if (cpuUsage >= 90) {
            finalCpuUsage = "§c" + cpuUsage;
        } else if (cpuUsage >= 60) {
            finalCpuUsage = "§e" + cpuUsage;
        } else if (cpuUsage < 60) {
            finalCpuUsage = "§a" + cpuUsage;
        }

        return "§7CPU: " + finalCpuUsage + "%";
    }

    private static String getTPS() {
        double tpsCurrent = 0;
        try {
            Server server = Bukkit.getServer();
            Object minecraftServer = server.getClass().getMethod("getServer").invoke(server);
            double[] recentTps = (double[]) minecraftServer.getClass().getField("recentTps").get(minecraftServer);
            tpsCurrent = Math.min(20, Math.round(recentTps[0] * 10.0) / 10.0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalTps = null;
        if (tpsCurrent <= 15) {
            finalTps = "§c" + tpsCurrent;
        } else if (tpsCurrent <= 17) {
            finalTps = "§e" + tpsCurrent;
        } else if (tpsCurrent > 17) {
            finalTps = "§a" + tpsCurrent;
        }

        return "§7TPS: " + finalTps;
    }

    private static String getMSPT() {
        final long[] lastTime = {System.nanoTime()};
        final long[] totalElapsedTime = {0};
        final int[] tickCount = {0};
        final String[] roundedMspt = {""};

        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.nanoTime();
                long elapsedTime = currentTime - lastTime[0];
                lastTime[0] = currentTime;

                totalElapsedTime[0] += elapsedTime;
                tickCount[0]++;

                if (tickCount[0] >= 20) {
                    double mspt = totalElapsedTime[0] / (tickCount[0] * 1000000.0);
                    totalElapsedTime[0] = 0;
                    tickCount[0] = 0;

                    roundedMspt[0] = String.format("%.1f", mspt);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);

        System.out.println(roundedMspt);
        return "§7MSPT: §a" + roundedMspt[0];
    }


    private static String getPing(Player player) {
        return "§7Ping: §a" + player.getPing() + "ms";
    }


}
