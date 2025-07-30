# MongoDB Loader

This plugin allows connecting with MongoDB using the latest `mongodb-driver-sync` library. It supports multiple Minecraft server platforms:

- **Bukkit/Spigot/Paper** - Traditional Minecraft servers
- **BungeeCord** - Proxy server for Minecraft networks
- **Velocity** - Modern, high-performance proxy server
- **Sponge** - Plugin platform for Minecraft servers

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
    implementation 'com.github.AshleyThew:mongodb-loader:main-SNAPSHOT'

    // Or use a specific version/tag (these correspond to MongoDB driver versions)
    // implementation 'com.github.AshleyThew:mongodb-loader:5.4.0'
    // implementation 'com.github.AshleyThew:mongodb-loader:latest'
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

### Usage in Code

When using JitPack, you can directly use the MongoDB helper:

```java
import me.dablakbandit.mongodbloader.common.MongoDBHelper;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class YourPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize MongoDB connection
        MongoDBHelper.initialize("mongodb://localhost:27017");

        if (MongoDBHelper.isAvailable()) {
            getLogger().info("MongoDB is available!");

            // Get a database and collection
            MongoDatabase database = MongoDBHelper.getDatabase("your_database");
            MongoCollection<Document> collection = database.getCollection("your_collection");

            // Use MongoDB as needed
            // ...
        }
    }

    @Override
    public void onDisable() {
        // Close MongoDB connection
        MongoDBHelper.close();
    }
}
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

## Using the Plugin

1. Copy the generated JAR file `mongodb-loader-<version>.jar` to your server's plugins directory:

   - **Bukkit/Spigot/Paper**: Copy to `plugins/` directory
   - **BungeeCord**: Copy to `plugins/` directory
   - **Velocity**: Copy to `plugins/` directory
   - **Sponge**: Copy to `mods/` directory

2. Start your server. The plugin will automatically detect the platform and load the appropriate implementation.

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
