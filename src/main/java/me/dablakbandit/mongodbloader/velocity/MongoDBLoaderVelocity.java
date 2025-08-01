package me.dablakbandit.mongodbloader.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;
import java.time.Duration;

@Plugin(id = "mongodb-loader", name = "MongoDBLoader", version = "@version@", authors = { "YourName" })
public class MongoDBLoaderVelocity implements MongoDBLoaderCommon {

    private final Logger logger;
    private final ProxyServer server;
    private final PluginContainer pluginContainer;

    @Inject
    public MongoDBLoaderVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory,
            PluginContainer pluginContainer) {
        this.server = server;
        this.logger = logger;
        this.pluginContainer = pluginContainer;
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

    @Override
    public Object scheduleAsyncRepeating(Runnable task, long delay, long period) {
        // Velocity uses Duration for time units
        return server.getScheduler()
                .buildTask(this, task)
                .delay(Duration.ofMinutes(delay))
                .repeat(Duration.ofMinutes(period))
                .schedule();
    }

    @Override
    public void cancelTask(Object task) {
        if (task instanceof ScheduledTask) {
            ((ScheduledTask) task).cancel();
        }
    }

    @Override
    public String getVersion() {
        return pluginContainer.getDescription().getVersion().orElse("@version@");
    }
}
