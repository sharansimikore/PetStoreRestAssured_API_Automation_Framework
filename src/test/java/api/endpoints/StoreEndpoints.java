package api.endpoints;

import api.client.ApiClient;
import api.payload.Store;
import io.restassured.response.Response;

public final class StoreEndpoints {

    private StoreEndpoints() {
    }

    public static Response getInventory() {
        return ApiClient.request()
                .when()
                .get(Routes.STORE_INVENTORY);
    }

    public static Response createOrder(Store payload) {
        return ApiClient.request()
                .body(payload)
                .when()
                .post(Routes.STORE_ORDER);
    }

    public static Response getOrder(long orderId) {
        return ApiClient.request()
                .pathParam("orderId", orderId)
                .when()
                .get(Routes.STORE_ORDER_BY_ID);
    }

    public static Response deleteOrder(long orderId) {
        return ApiClient.request()
                .pathParam("orderId", orderId)
                .when()
                .delete(Routes.STORE_ORDER_BY_ID);
    }
}
