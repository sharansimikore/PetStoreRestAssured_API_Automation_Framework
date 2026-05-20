//mvn clean test "-Denv=staging" "-DsuiteXmlFile=testng-regression.xml"
//mvn clean test "-Denv=mock-server" "-DsuiteXmlFile=testng-regression.xml"
//mvn clean test "-Denv=qa" "-DsuiteXmlFile=testng-smoke.xml"
//mvn clean test "-Denv=qa" "-DsuiteXmlFile=testng-regression.xml"

package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.base.BaseApiTest;
import api.endpoints.StoreEndpoints;
import api.payload.Store;
import api.utilities.StoreResponseValidator;
import io.restassured.response.Response;

public class StoreTest extends BaseApiTest {

    private static final String GROUP_SMOKE = "smoke";
    private static final String GROUP_REGRESSION = "regression";

    Store orderPayload;
    long orderId;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        initLogger();
        orderPayload = new Store();
        orderPayload.setId(0);
        orderPayload.setPetId(7);
        orderPayload.setQuantity(999);
        orderPayload.setShipDate("2026-05-20T17:05:24.403Z");
        orderPayload.setStatus("placed");
        orderPayload.setComplete(true);
    }

    @Test(priority = 1, groups = {GROUP_SMOKE, GROUP_REGRESSION})
    public void testGetInventory() {
        logger.info("********** Get Store Inventory ****************");

        Response response = StoreEndpoints.getInventory();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "GET /store/inventory should return 200");
        StoreResponseValidator.assertInventory(response);

        logger.info("********** Store Inventory Retrieved ****************");
    }

    @Test(priority = 2, groups = {GROUP_SMOKE, GROUP_REGRESSION})
    public void testPostOrder() {
        logger.info("********** Place Store Order ****************");

        Response response = StoreEndpoints.createOrder(orderPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "POST /store/order should return 200");
        StoreResponseValidator.assertCreateOrder(response, orderPayload);

        orderId = response.jsonPath().getLong("id");
        Assert.assertTrue(orderId > 0, "Order id should be assigned after POST");

        logger.info("********** Store Order Placed, orderId={} ****************", orderId);
    }

    @Test(priority = 3, groups = {GROUP_REGRESSION})
    public void testGetOrderById() {
        logger.info("********** Get Store Order By ID ****************");

        Response response = StoreEndpoints.getOrder(orderId);
        response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200,
        "Intentional failure — DELETE returns 200");

        // Assert.assertEquals(response.getStatusCode(), HTTP_OK,
        //         "GET /store/order/{orderId} should return 200");
        StoreResponseValidator.assertGetOrder(response, orderPayload, orderId);

        logger.info("********** Store Order Retrieved ****************");
    }

    @Test(priority = 4, groups = {GROUP_REGRESSION})
    public void testDeleteOrderById() {
        logger.info("********** Delete Store Order ****************");

        Response response = StoreEndpoints.deleteOrder(orderId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "DELETE /store/order/{orderId} should return 200");
        StoreResponseValidator.assertDeleteOrder(response);

        Response getAfterDelete = StoreEndpoints.getOrder(orderId);
        Assert.assertEquals(getAfterDelete.getStatusCode(), HTTP_NOT_FOUND,
                "GET /store/order/{orderId} after delete should return 404");

        logger.info("********** Store Order Deleted ****************");
    }
}
