package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.base.BaseApiTest;
import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.UserResponseValidator;
import io.restassured.response.Response;

public class DataDrivenTests extends BaseApiTest {

    private static final String GROUP_REGRESSION = "regression";

    @Test(priority = 1,
            groups = {GROUP_REGRESSION},
            dataProvider = "data",
            dataProviderClass = DataProviders.class)
    public void testPostUser(
            String userid,
            String username,
            String fname,
            String lname,
            String email,
            String password,
            String ph) {

        initLogger();

        User userPayload = new User();
        userPayload.setId((int) Double.parseDouble(userid));
        userPayload.setUsername(username);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(ph);

        Response response = UserEndpoints.createUser(userPayload);

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "POST /user should return 200");
        UserResponseValidator.assertCreateUser(response);
    }

    @Test(priority = 2,
            groups = {GROUP_REGRESSION},
            dataProvider = "userName",
            dataProviderClass = DataProviders.class)
    public void testDeleteUserByUsername(String username) {

        initLogger();

        Response response = UserEndpoints.deleteUser(username);

        Assert.assertEquals(response.getStatusCode(), HTTP_OK,
                "DELETE /user/{username} should return 200");
        UserResponseValidator.assertDeleteUser(response);
    }
}
