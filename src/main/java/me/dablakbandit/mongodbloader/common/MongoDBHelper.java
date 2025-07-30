package me.dablakbandit.mongodbloader.common;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Utility class for accessing MongoDB functionality
 * This class can be used by other plugins to interact with MongoDB
 */
public class MongoDBHelper {
    
    private static MongoClient mongoClient;
    private static boolean initialized = false;
    
    /**
     * Initialize the MongoDB connection
     * @param connectionString MongoDB connection string
     */
    public static void initialize(String connectionString) {
        if (!initialized) {
            try {
                mongoClient = MongoClients.create(connectionString);
                initialized = true;
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize MongoDB connection", e);
            }
        }
    }
    
    /**
     * Get the MongoDB client instance
     * @return MongoClient instance or null if not initialized
     */
    public static MongoClient getClient() {
        return mongoClient;
    }
    
    /**
     * Get a MongoDB database
     * @param databaseName Name of the database
     * @return MongoDatabase instance
     */
    public static MongoDatabase getDatabase(String databaseName) {
        if (mongoClient == null) {
            throw new IllegalStateException("MongoDB client not initialized");
        }
        return mongoClient.getDatabase(databaseName);
    }
    
    /**
     * Check if MongoDB is available and initialized
     * @return true if MongoDB is available
     */
    public static boolean isAvailable() {
        return initialized && mongoClient != null;
    }
    
    /**
     * Close the MongoDB connection
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            initialized = false;
        }
    }
}
