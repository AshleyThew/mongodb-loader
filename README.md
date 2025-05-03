# Minecraft SpigotMC Plugin

This plugin connects to MongoDB using the latest `mongo-driver-sync` library.

## Building the Plugin

1. Clone the repository:
   ```sh
   git clone https://github.com/githubnext/workspace-blank.git
   cd workspace-blank
   ```

2. Build the plugin with Gradle:
   ```sh
   ./gradlew build -Pversion=<mongo-driver-sync-version>
   ```

## Using the Plugin

1. Copy the generated JAR file from `build/libs` to your Minecraft server's `plugins` directory.

2. Start your Minecraft server. The plugin will automatically connect to MongoDB.

## Releasing a New Version

1. Update the `mongo-driver-sync` version in `build.gradle`.

2. Commit and push the changes:
   ```sh
   git commit -am "Update mongo-driver-sync version"
   git push origin main
   ```

3. The GitHub Actions workflow will automatically build and release the new version.
