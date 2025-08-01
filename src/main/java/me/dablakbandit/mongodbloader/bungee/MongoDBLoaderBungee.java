package me.dablakbandit.mongodbloader.bungee;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public class MongoDBLoaderBungee extends Plugin implements MongoDBLoaderCommon {

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
        // BungeeCord uses TimeUnit.MINUTES directly
        return getProxy().getScheduler().schedule(this, task, delay, period, TimeUnit.MINUTES);
    }

    @Override
    public void cancelTask(Object task) {
        if (task instanceof ScheduledTask) {
            ((ScheduledTask) task).cancel();
        }
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }
}
