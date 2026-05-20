package api.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

import api.client.ApiClient;
import api.config.ConfigManager;
import io.restassured.RestAssured;

/**
 * Base class for all API tests — loads environment config once per suite.
 */
public abstract class BaseApiTest {

    protected static final int HTTP_OK = 200;
    protected static final int HTTP_NOT_FOUND = 404;

    protected Logger logger;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        ConfigManager.loadEnvironment();
        RestAssured.baseURI = ConfigManager.getBaseUri();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ApiClient.init();

        logger = LogManager.getLogger(getClass());
        logger.info("Running tests on environment [{}] with base URI [{}]",
                ConfigManager.getActiveEnvironment(),
                ConfigManager.getBaseUri());
    }

    protected void initLogger() {
        if (logger == null) {
            logger = LogManager.getLogger(getClass());
        }
    }
}
