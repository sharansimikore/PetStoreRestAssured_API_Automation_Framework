package api.utilities;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import api.payload.Store;
import io.restassured.response.Response;

public final class StoreResponseValidator {

	private static final String INVENTORY_SCHEMA = "schemas/store-inventory-schema.json";
	private static final String ORDER_SCHEMA = "schemas/store-order-schema.json";
	private static final long MAX_RESPONSE_TIME_MS = 10_000L;

	private StoreResponseValidator() {
	}

	public static void assertInventory(Response response) {
		response.then()
				.time(lessThan(MAX_RESPONSE_TIME_MS))
				.contentType("application/json")
				.body(matchesJsonSchemaInClasspath(INVENTORY_SCHEMA))
				.body("$", notNullValue());
	}

	public static void assertCreateOrder(Response response, Store expected) {
		response.then()
				.time(lessThan(MAX_RESPONSE_TIME_MS))
				.contentType("application/json")
				.body(matchesJsonSchemaInClasspath(ORDER_SCHEMA))
				.body("id", notNullValue())
				.body("petId", equalTo(expected.getPetId()))
				.body("quantity", equalTo(expected.getQuantity()))
				.body("status", equalTo(expected.getStatus()))
				.body("complete", equalTo(expected.isComplete()));
	}

	public static void assertGetOrder(Response response, Store expected, long orderId) {
		response.then()
				.time(lessThan(MAX_RESPONSE_TIME_MS))
				.contentType("application/json")
				.body(matchesJsonSchemaInClasspath(ORDER_SCHEMA))
				.body("id", equalTo(orderId))
				.body("petId", equalTo(expected.getPetId()))
				.body("quantity", equalTo(expected.getQuantity()))
				.body("status", equalTo(expected.getStatus()))
				.body("complete", equalTo(expected.isComplete()));
	}

	public static void assertDeleteOrder(Response response) {
		response.then()
				.time(lessThan(MAX_RESPONSE_TIME_MS));
	}
}
