package com.example.minecraftplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled!");

        // Confirm the mongo-driver-sync driver exists
        try {
            Class.forName("com.mongodb.client.MongoClients");
            getLogger().info("MongoDB driver is present!");
        } catch (ClassNotFoundException e) {
            getLogger().severe("MongoDB driver is missing!");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }
}
