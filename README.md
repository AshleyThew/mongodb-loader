# MongoDB Loader

This plugin allows connecting with MongoDB using the latest `mongodb-driver-sync` library. It supports multiple Minecraft server platforms:

- **Bukkit/Spigot/Paper** - Traditional Minecraft servers
- **BungeeCord** - Proxy server for Minecraft networks
- **Velocity** - Modern, high-performance proxy server
- **Sponge** - Plugin platform for Minecraft servers

## Installation for Server Owners

MongoDB Loader is a library plugin that other plugins depend on to connect to MongoDB databases. If you have plugins that require MongoDB Loader, follow these steps:

1. Download the latest `mongodb-loader-<version>.jar` from the [releases page](https://github.com/AshleyThew/mongodb-loader/releases).

2. Copy the JAR file to your server's plugins directory:

   - **Bukkit/Spigot/Paper**: Copy to `plugins/` directory
   - **BungeeCord**: Copy to `plugins/` directory
   - **Velocity**: Copy to `plugins/` directory
   - **Sponge**: Copy to `mods/` directory

3. Start your server. The plugin will automatically detect the platform and load the appropriate implementation.

4. Install any plugins that depend on MongoDB Loader.

**Note**: MongoDB Loader is a dependency plugin - it doesn't add any gameplay features by itself. It provides MongoDB database connectivity for other plugins that need it.

---

## Developer Documentation

The following sections are for plugin developers who want to use MongoDB Loader in their projects.

## Adding as a Dependency to Your Plugin

To use MongoDB Loader in your own plugin, add the following to your plugin configuration:

### Bukkit/Spigot/Paper (`plugin.yml`)

- If your plugin requires MongoDB Loader to be present:
  ```yaml
  depend: [MongoDBLoader]
  ```
- If your plugin can work without MongoDB Loader, but will use it if available:
  ```yaml
  softdepend: [MongoDBLoader]
  ```

### BungeeCord (`bungee.yml`)

- If your plugin requires MongoDB Loader to be present:
  ```yaml
  depends: [MongoDBLoader]
  ```
- If your plugin can work without MongoDB Loader, but will use it if available:
  ```yaml
  softDepends: [MongoDBLoader]
  ```

### Velocity (`velocity-plugin.json`)

```json
{
  "dependencies": [
    {
      "id": "mongodb-loader",
      "optional": false
    }
  ]
}
```

### Sponge

Use the `@Dependency` annotation in your main plugin class:

```java
@Plugin("your-plugin-id")
@Dependency("mongodb-loader")
public class YourPlugin {
    // ...
}
```

This ensures your plugin loads in the correct order and can interact with MongoDB Loader if present.

## Usage Example

Once you have MongoDB Loader as a dependency, you can use the MongoDB driver in your plugin. Here's a complete example:

### Basic MongoDB Connection and Operations

```java
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;

public class YourPlugin extends JavaPlugin {

    private MongoClient mongoClient;
    private MongoDatabase database;

    @Override
    public void onEnable() {
        // Connect to MongoDB
        connectToMongoDB();

        // Example usage
        savePlayerData("player123", "ExamplePlayer", 100);
        PlayerData data = getPlayerData("player123");
        getLogger().info("Player: " + data.name + ", Score: " + data.score);
    }

    @Override
    public void onDisable() {
        // Close MongoDB connection
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    private void connectToMongoDB() {
        try {
            // Connect to MongoDB (replace with your connection string)
            String connectionString = "mongodb://localhost:27017";
            mongoClient = MongoClients.create(connectionString);

            // Get database (creates it if it doesn't exist)
            database = mongoClient.getDatabase("minecraft_server");

            getLogger().info("Successfully connected to MongoDB!");

        } catch (Exception e) {
            getLogger().severe("Failed to connect to MongoDB: " + e.getMessage());
        }
    }

    // Example: Save player data
    public void savePlayerData(String playerId, String playerName, int score) {
        try {
            MongoCollection<Document> collection = database.getCollection("players");

            Document playerDoc = new Document("_id", playerId)
                    .append("name", playerName)
                    .append("score", score)
                    .append("lastSeen", System.currentTimeMillis());

            // Use upsert to insert or update
            collection.replaceOne(
                new Document("_id", playerId),
                playerDoc,
                new com.mongodb.client.model.ReplaceOptions().upsert(true)
            );

            getLogger().info("Saved data for player: " + playerName);

        } catch (Exception e) {
            getLogger().severe("Failed to save player data: " + e.getMessage());
        }
    }

    // Example: Get player data
    public PlayerData getPlayerData(String playerId) {
        try {
            MongoCollection<Document> collection = database.getCollection("players");

            Document playerDoc = collection.find(new Document("_id", playerId)).first();

            if (playerDoc != null) {
                return new PlayerData(
                    playerDoc.getString("name"),
                    playerDoc.getInteger("score", 0)
                );
            }

        } catch (Exception e) {
            getLogger().severe("Failed to get player data: " + e.getMessage());
        }

        return null;
    }

    // Simple data class
    public static class PlayerData {
        public final String name;
        public final int score;

        public PlayerData(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
```

### Configuration Example

Add MongoDB settings to your `config.yml`:

```yaml
mongodb:
  connection-string: 'mongodb://localhost:27017'
  database-name: 'minecraft_server'
# Alternative with authentication
# mongodb:
#   connection-string: "mongodb://username:password@localhost:27017/database_name"
#   database-name: "minecraft_server"
```

And load it in your plugin:

```java
private void loadConfig() {
    saveDefaultConfig();
    String connectionString = getConfig().getString("mongodb.connection-string");
    String databaseName = getConfig().getString("mongodb.database-name");

    mongoClient = MongoClients.create(connectionString);
    database = mongoClient.getDatabase(databaseName);
}
```

### Advanced Features

The MongoDB driver included supports all modern MongoDB features:

- **Transactions**: For multi-document ACID operations
- **Aggregation Pipeline**: For complex data processing
- **Change Streams**: For real-time data monitoring
- **GridFS**: For storing large files
- **Indexes**: For query optimization

For more advanced usage, refer to the [MongoDB Java Driver Documentation](https://mongodb.github.io/mongo-java-driver/).

## Using MongoDB Loader as a Gradle Dependency (JitPack)

You can also include MongoDB Loader directly in your project using JitPack instead of loading it as a separate plugin.

### Gradle

Add JitPack repository to your `build.gradle`:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency:

```gradle
dependencies {
    compileOnly 'com.github.AshleyThew:mongodb-loader:main-SNAPSHOT'

    // Or use a specific version/tag (these correspond to MongoDB driver versions)
    // compileOnly 'com.github.AshleyThew:mongodb-loader:5.4.0'
    // compileOnly 'com.github.AshleyThew:mongodb-loader:latest'
}
```

**Note**: JitPack automatically builds with the latest MongoDB driver version available at build time, so each version includes the most recent MongoDB drivers.

### Maven

Add JitPack repository to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add the dependency:

```xml
<dependency>
    <groupId>com.github.AshleyThew</groupId>
    <artifactId>mongodb-loader</artifactId>
    <version>main-SNAPSHOT</version>
</dependency>
```

## Building the Plugin

1. Clone the repository:

   ```sh
   git clone https://github.com/Dablakbandit/mongodb-loader/
   cd mongodb-loader
   ```

2. Build the universal JAR with Gradle:
   ```sh
   ./gradlew build -Pbom_version=<version>
   ```

This will create a single universal JAR in the `build/libs/` directory:

- `mongodb-loader-<version>.jar` - Works on all supported platforms (Bukkit, BungeeCord, Velocity, Sponge)

## Releasing a New Version

1. Update the `bom_version` in `build.gradle`.

2. Commit and push the changes:

   ```sh
   git commit -am "Update bom_version"
   git push origin main
   ```

3. The GitHub Actions workflow will automatically build and release the new version.

## Manually Triggering the Workflow

You can manually trigger the GitHub Actions workflow by following these steps:

1. Go to the "Actions" tab in your GitHub repository.
2. Select the "Release Plugin" workflow from the list on the left.
3. Click the "Run workflow" button on the right.
4. Optionally, specify any required inputs and click the "Run workflow" button again.
