package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.StoreEndpoints;
import api.payload.Store;
import api.utilities.StoreResponseValidator;
import io.restassured.response.Response;

public class StoreTest {

	private static final int HTTP_OK = 200;
	private static final int HTTP_NOT_FOUND = 404;

	Store orderPayload;
	long orderId;
	public Logger logger;

	@BeforeClass
	public void setup() {
		orderPayload = new Store();
		orderPayload.setId(0);
		orderPayload.setPetId(7);
		orderPayload.setQuantity(999);
		orderPayload.setShipDate("2026-05-20T17:05:24.403Z");
		orderPayload.setStatus("placed");
		orderPayload.setComplete(true);

		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void testGetInventory() {
		logger.info("********** Get Store Inventory ****************");

		Response response = StoreEndpoints.GetInventory();
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), HTTP_OK,
				"GET /store/inventory should return 200");
		StoreResponseValidator.assertInventory(response);

		logger.info("********** Store Inventory Retrieved ****************");
	}

	@Test(priority = 2)
	public void testPostOrder() {
		logger.info("********** Place Store Order ****************");

		Response response = StoreEndpoints.CreateOrder(orderPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), HTTP_OK,
				"POST /store/order should return 200");
		StoreResponseValidator.assertCreateOrder(response, orderPayload);

		orderId = response.jsonPath().getLong("id");
		Assert.assertTrue(orderId > 0, "Order id should be assigned after POST");

		logger.info("********** Store Order Placed, orderId=" + orderId + " ****************");
	}

	@Test(priority = 3)
	public void testGetOrderById() {
		logger.info("********** Get Store Order By ID ****************");

		Response response = StoreEndpoints.GetOrder(orderId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), HTTP_OK,
				"GET /store/order/{orderId} should return 200");
		StoreResponseValidator.assertGetOrder(response, orderPayload, orderId);

		logger.info("********** Store Order Retrieved ****************");
	}

	@Test(priority = 4)
	public void testDeleteOrderById() {
		logger.info("********** Delete Store Order ****************");

		Response response = StoreEndpoints.DeleteOrder(orderId);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), HTTP_OK,
				"DELETE /store/order/{orderId} should return 200");
		StoreResponseValidator.assertDeleteOrder(response);

		Response getAfterDelete = StoreEndpoints.GetOrder(orderId);
		Assert.assertEquals(getAfterDelete.getStatusCode(), HTTP_NOT_FOUND,
				"GET /store/order/{orderId} after delete should return 404");

		logger.info("********** Store Order Deleted ****************");
	}
}
