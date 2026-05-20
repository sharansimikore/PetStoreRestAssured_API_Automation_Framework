package api.endpoints;

/**
 * API path constants (relative to {@link api.config.ConfigManager#getBaseUri()}).
 */
public final class Routes {

    private Routes() {
    }

    // User module
    public static final String USER = "/user";
    public static final String USER_BY_USERNAME = "/user/{username}";

    // Store module
    public static final String STORE_INVENTORY = "/store/inventory";
    public static final String STORE_ORDER = "/store/order";
    public static final String STORE_ORDER_BY_ID = "/store/order/{orderId}";

    // Pet module (placeholder for future tests)
    public static final String PET = "/pet";
    public static final String PET_BY_ID = "/pet/{petId}";
}
