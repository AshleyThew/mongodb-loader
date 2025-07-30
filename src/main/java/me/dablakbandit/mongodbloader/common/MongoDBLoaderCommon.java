package me.dablakbandit.mongodbloader.common;

public interface MongoDBLoaderCommon {

    void logInfo(String message);

    void logSevere(String message);

    default void onEnable() {
        logInfo("MongoDB Loader enabled!");

        // Confirm the mongo-driver-sync driver exists
        try {
            Class.forName("com.mongodb.client.MongoClient");
            logInfo("MongoDB driver is present!");
        } catch (ClassNotFoundException e) {
            logSevere("MongoDB driver is missing!");
        }
    }

    default void onDisable() {
        logInfo("MongoDB Loader disabled!");
    }
}
