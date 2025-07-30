package me.dablakbandit.mongodbloader.spigot;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import org.bukkit.plugin.java.JavaPlugin;

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
}
