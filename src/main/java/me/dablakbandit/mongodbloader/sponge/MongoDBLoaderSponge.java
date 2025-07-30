package me.dablakbandit.mongodbloader.sponge;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppedGameEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

@Plugin("mongodb-loader")
public class MongoDBLoaderSponge implements MongoDBLoaderCommon {

    private final Logger logger;

    @Inject
    public MongoDBLoaderSponge(Logger logger) {
        this.logger = logger;
    }

    @Listener
    public void onServerStart(StartedEngineEvent<org.spongepowered.api.Server> event) {
        MongoDBLoaderCommon.super.onEnable();
    }

    @Listener
    public void onServerStop(StoppedGameEvent event) {
        MongoDBLoaderCommon.super.onDisable();
    }

    @Override
    public void logInfo(String message) {
        logger.info(message);
    }

    @Override
    public void logSevere(String message) {
        logger.error(message);
    }
}
