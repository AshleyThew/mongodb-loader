package me.dablakbandit.mongodbloader.updater;

import me.dablakbandit.mongodbloader.common.MongoDBLoaderCommon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private static final UpdateChecker updateChecker = new UpdateChecker();

    public static UpdateChecker getInstance() {
        return updateChecker;
    }

    private String latest;
    private MongoDBLoaderCommon plugin;
    private Object scheduledTask;

    private UpdateChecker() {

    }

    public void start(MongoDBLoaderCommon plugin, String currentVersion) {
        this.latest = currentVersion;
        this.plugin = plugin;
        this.scheduledTask = plugin.scheduleAsyncRepeating(this::checkUpdate, 1, 1440);
    }

    public void stop() {
        if (scheduledTask != null && plugin != null) {
            plugin.cancelTask(scheduledTask);
            scheduledTask = null;
        }
    }

    public void checkUpdate() {
        int latestVersion = Integer.parseInt(latest.replaceAll("[^0-9]", ""));
        plugin.logInfo("Checking update for MongoDB Loader v" + latest);
        try {
            URL checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=124666");
            URLConnection con = checkURL.openConnection();
            con.setConnectTimeout(2000);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String new_version = reader.readLine();
                int new_version_number = Integer.parseInt(new_version.replaceAll("[^0-9]", ""));
                if (new_version_number > latestVersion) {
                    latest = new_version;
                    plugin.logInfo("Plugin MongoDB Loader has been updated to v" + new_version
                            + ", please update your server.");
                }
            }
        } catch (Exception e) {
            plugin.logSevere("Unable to check update for MongoDB Loader v" + latest);
        }
    }

    public Object getScheduledTask() {
        return scheduledTask;
    }
}
