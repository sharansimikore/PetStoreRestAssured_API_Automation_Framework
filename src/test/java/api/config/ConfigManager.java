package api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads environment-specific configuration from src/test/resources/config/.
 * Active environment: -Denv=qa | staging | mock-server (default: qa)
 */
public final class ConfigManager {

    private static final String DEFAULT_ENV = "qa";
    private static final String CONFIG_FOLDER = "config/";
    private static Properties properties = new Properties();
    private static String activeEnvironment = DEFAULT_ENV;

    private ConfigManager() {
    }

    public static synchronized void loadEnvironment(String environment) {
        properties = new Properties();
        activeEnvironment = (environment == null || environment.isBlank())
                ? DEFAULT_ENV
                : environment.trim().toLowerCase();

        loadPropertiesFile(CONFIG_FOLDER + "config.properties");

        String envFile = CONFIG_FOLDER + activeEnvironment + ".properties";
        if (!loadPropertiesFile(envFile)) {
            throw new IllegalStateException(
                    "Environment config not found: " + envFile
                            + ". Supported: qa, staging, mock-server");
        }
    }

    public static void loadEnvironment() {
        loadEnvironment(System.getProperty("env", DEFAULT_ENV));
    }

    public static String getActiveEnvironment() {
        return activeEnvironment;
    }

    public static String getBaseUri() {
        return getRequired("base.uri");
    }

    public static int getTimeoutMs() {
        return Integer.parseInt(getRequired("timeout.ms"));
    }

    public static String getTestDataPath() {
        return getRequired("testdata.path");
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required config property: " + key
                            + " for environment: " + activeEnvironment);
        }
        return value.trim();
    }

    private static boolean loadPropertiesFile(String classpathResource) {
        try (InputStream stream = ConfigManager.class.getClassLoader()
                .getResourceAsStream(classpathResource)) {
            if (stream == null) {
                return false;
            }
            properties.load(stream);
            return true;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config: " + classpathResource, e);
        }
    }
}
