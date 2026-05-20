package api.test;

import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.UserResponseValidator;
import api.utilities.dataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {

    @Test(priority=1,
          dataProvider="data",
          dataProviderClass=dataProviders.class)

    public void testPostUser(
            String userid,
            String username,
            String fname,
            String lname,
            String email,
            String password,
            String ph)
    {

        User userPayload = new User();
        
        
        userPayload.setId((int) Double.parseDouble(userid));
//        userPayload.setId(Integer.parseInt(userid));
        userPayload.setUsername(username);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(ph);

        Response response =
                UserEndpoints.CreateUser(userPayload);

        UserResponseValidator.assertCreateUser(response);
    }


    @Test(priority=2,
          dataProvider="userName",
          dataProviderClass=dataProviders.class)

    public void testDeleteData(String username)
    {

        Response response =
                UserEndpoints.DeleteUser(username);

        UserResponseValidator.assertDeleteUser(response);
    }
}