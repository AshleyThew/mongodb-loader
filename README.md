# Minecraft SpigotMC Plugin

This plugin connects to MongoDB using the latest `mongodb-driver-sync` library.

## Building the Plugin

1. Clone the repository:

   ```sh
   git clone https://github.com/Dablakbandit/mongodb-loader/
   cd mongodb-loader
   ```

2. Build the plugin with Gradle:
   ```sh
   ./gradlew build -Pbom_version=<version>
   ```

## Using the Plugin

1. Copy the generated JAR file from `build/libs/mongodb-loader-<version>.jar` to your Minecraft server's `plugins` directory.

2. Start your Minecraft server. The plugin will automatically connect to MongoDB.

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
