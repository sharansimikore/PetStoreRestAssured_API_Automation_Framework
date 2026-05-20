package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.base.BaseApiTest;
import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.UserResponseValidator;
import io.restassured.response.Response;

public class UserTest extends BaseApiTest {

    private static final String GROUP_SMOKE = "smoke";
    private static final String GROUP_REGRESSION = "regression";

    Faker faker;
    User userPayload;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        initLogger();
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setUsername(faker.name().username());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().phoneNumber());
    }

    @Test(priority = 1, groups = {GROUP_SMOKE, GROUP_REGRESSION})
    public void testPostUser() {
        logger.info("********** Creating User ****************");
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "POST /user should return 200");
        UserResponseValidator.assertCreateUser(response);

        logger.info("********** User Created ****************");
    }

    @Test(priority = 2, groups = {GROUP_SMOKE, GROUP_REGRESSION})
    public void testGetUser() {
        logger.info("********** Get User ****************");

        Response response = UserEndpoints.getUser(userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "GET /user/{username} should return 200");
        UserResponseValidator.assertGetUser(response, userPayload);

        logger.info("********** Got User ****************");
    }

    @Test(priority = 3, groups = {GROUP_REGRESSION})
    public void testUpdateUserByName() {
        logger.info("********** Update User ****************");

        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());

        Response response = UserEndpoints.updateUser(userPayload.getUsername(), userPayload);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "PUT /user/{username} should return 200");
        UserResponseValidator.assertUpdateUser(response);

        Response responseAfterUpdate = UserEndpoints.getUser(userPayload.getUsername());
        responseAfterUpdate.then().log().all();

        Assert.assertEquals(responseAfterUpdate.getStatusCode(), HTTP_OK,
                "GET /user/{username} after update should return 200");
        UserResponseValidator.assertGetUser(responseAfterUpdate, userPayload);

        logger.info("********** User Updated ****************");
    }

    @Test(priority = 4, groups = {GROUP_REGRESSION})
    public void testDeleteUser() {
        logger.info("********** Deleting User ****************");

        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "DELETE /user/{username} should return 200");
        UserResponseValidator.assertDeleteUser(response);

        Response getAfterDelete = UserEndpoints.getUser(userPayload.getUsername());

        Assert.assertEquals(getAfterDelete.getStatusCode(), HTTP_NOT_FOUND,
                "GET /user/{username} after delete should return 404");
        UserResponseValidator.assertUserNotFound(getAfterDelete);

        logger.info("********** User Deleted ****************");
    }
}
