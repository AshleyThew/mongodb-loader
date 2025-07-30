package me.dablakbandit.mongodbloader.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin(id = "mongodb-loader", name = "MongoDBLoader", version = "@version@", authors = { "YourName" })
public class MongoDBLoaderVelocity implements MongoDBLoaderCommon {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    @Inject
    public MongoDBLoaderVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        MongoDBLoaderCommon.super.onEnable();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
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
