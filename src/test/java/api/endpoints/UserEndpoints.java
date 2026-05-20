package api.endpoints;

import api.client.ApiClient;
import api.payload.User;
import io.restassured.response.Response;

public final class UserEndpoints {

    private UserEndpoints() {
    }

    public static Response createUser(User payload) {
        return ApiClient.request()
                .body(payload)
                .when()
                .post(Routes.USER);
    }

    public static Response getUser(String username) {
        return ApiClient.request()
                .pathParam("username", username)
                .when()
                .get(Routes.USER_BY_USERNAME);
    }

    public static Response updateUser(String username, User payload) {
        return ApiClient.request()
                .pathParam("username", username)
                .body(payload)
                .when()
                .put(Routes.USER_BY_USERNAME);
    }

    public static Response deleteUser(String username) {
        return ApiClient.request()
                .pathParam("username", username)
                .when()
                .delete(Routes.USER_BY_USERNAME);
    }
}
