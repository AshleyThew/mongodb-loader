package me.dablakbandit.mongodbloader.spigot;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class MongoDBLoaderSpigot extends JavaPlugin implements MongoDBLoaderCommon {

    @Override
    public void onEnable() {
        MongoDBLoaderCommon.super.onEnable();
    }

    @Override
    public void onDisable() {
        MongoDBLoaderCommon.super.onDisable();
    }

    @Override
    public void logInfo(String message) {
        getLogger().info(message);
    }

    @Override
    public void logSevere(String message) {
        getLogger().severe(message);
    }

    @Override
    public Object scheduleAsyncRepeating(Runnable task, long delay, long period) {
        // Convert minutes to ticks (20 ticks = 1 second, so 1200 ticks = 1 minute)
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, delay * 1200L, period * 1200L);
    }

    @Override
    public void cancelTask(Object task) {
        if (task instanceof BukkitTask) {
            ((BukkitTask) task).cancel();
        }
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }
}
