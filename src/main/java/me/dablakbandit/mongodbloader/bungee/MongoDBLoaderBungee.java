package me.dablakbandit.mongodbloader.bungee;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import net.md_5.bungee.api.plugin.Plugin;

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
}
