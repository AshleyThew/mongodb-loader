package me.dablakbandit.mongodbloader.sponge;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppedGameEvent;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.Duration;

@Plugin("mongodb-loader")
public class MongoDBLoaderSponge implements MongoDBLoaderCommon {

    private final Logger logger;
    private final PluginContainer pluginContainer;

    @Inject
    public MongoDBLoaderSponge(Logger logger, PluginContainer pluginContainer) {
        this.logger = logger;
        this.pluginContainer = pluginContainer;
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

    @Override
    public Object scheduleAsyncRepeating(Runnable task, long delay, long period) {
        // Sponge uses Duration for time units
        return Sponge.asyncScheduler()
                .submit(org.spongepowered.api.scheduler.Task.builder()
                        .plugin(pluginContainer)
                        .delay(Duration.ofMinutes(delay))
                        .interval(Duration.ofMinutes(period))
                        .execute(task)
                        .build());
    }

    @Override
    public void cancelTask(Object task) {
        if (task instanceof ScheduledTask) {
            ((ScheduledTask) task).cancel();
        }
    }

    @Override
    public String getVersion() {
        return pluginContainer.metadata().version().toString();
    }
}
