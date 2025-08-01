package me.dablakbandit.mongodbloader.common;

import me.dablakbandit.mongodbloader.updater.UpdateChecker;

public interface MongoDBLoaderCommon {

    void logInfo(String message);

    void logSevere(String message);

    // Add scheduling capability
    Object scheduleAsyncRepeating(Runnable task, long delay, long period);

    void cancelTask(Object task);

    // Abstract method to get the version from each platform
    String getVersion();

    default void onEnable() {
        logInfo("MongoDB Loader enabled!");

        // Confirm the mongo-driver-sync driver exists
        try {
            Class.forName("com.mongodb.client.MongoClient");
            logInfo("MongoDB driver is present!");
        } catch (ClassNotFoundException e) {
            logSevere("MongoDB driver is missing!");
        }

        // Start the update checker
        UpdateChecker.getInstance().start(this, getVersion());
    }

    default void onDisable() {
        // Stop the update checker
        UpdateChecker.getInstance().stop();

        logInfo("MongoDB Loader disabled!");
    }
}
