package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.Store;
import io.restassured.response.Response;

public class StoreEndpoints {

	public static Response GetInventory() {
		return given()
				.accept("application/json")
				.when()
				.get(Routes.store_inventory_url);
	}

	public static Response CreateOrder(Store payload) {
		return given()
				.accept("application/json")
				.contentType("application/json")
				.body(payload)
				.when()
				.post(Routes.store_post_order_url);
	}

	public static Response GetOrder(long orderId) {
		return given()
				.accept("application/json")
				.pathParam("orderId", orderId)
				.when()
				.get(Routes.store_get_order_url);
	}

	public static Response DeleteOrder(long orderId) {
		return given()
				.accept("application/json")
				.pathParam("orderId", orderId)
				.when()
				.delete(Routes.store_delete_order_url);
	}
}
