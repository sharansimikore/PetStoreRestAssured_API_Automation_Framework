package api.utilities;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import api.payload.User;
import io.restassured.response.Response;

/**
 * Status code, JSON schema, and response body assertions for Petstore User API.
 */
public final class UserResponseValidator {

    private static final String USER_GET_SCHEMA = "schemas/user-get-schema.json";
    private static final String API_RESPONSE_SCHEMA = "schemas/api-response-schema.json";
    private static final long MAX_RESPONSE_TIME_MS = 10_000L;

    private UserResponseValidator() {
    }

    public static void assertCreateUser(Response response) {
        assertApiResponseBody(response, HttpStatusCodes.API_SUCCESS_CODE);
    }

    public static void assertGetUser(Response response, User expected) {
        response.then()
                .time(lessThan(MAX_RESPONSE_TIME_MS))
                .contentType("application/json")
                .body(matchesJsonSchemaInClasspath(USER_GET_SCHEMA))
                .body("username", equalTo(expected.getUsername()))
                .body("firstName", equalTo(expected.getFirstName()))
                .body("lastName", equalTo(expected.getLastName()))
                .body("email", equalTo(expected.getEmail()))
                .body("phone", equalTo(expected.getPhone()));
    }

    public static void assertUpdateUser(Response response) {
        assertApiResponseBody(response, HttpStatusCodes.API_SUCCESS_CODE);
    }

    public static void assertDeleteUser(Response response) {
        assertApiResponseBody(response, HttpStatusCodes.API_SUCCESS_CODE);
    }

    public static void assertUserNotFound(Response response) {
        response.then().time(lessThan(MAX_RESPONSE_TIME_MS));
        assertBodyMatchesSchemaOrEmpty(response, API_RESPONSE_SCHEMA);
    }

    /**
     * Petstore POST/PUT/DELETE return ApiResponse JSON with code, type, message.
     */
    private static void assertApiResponseBody(Response response, int expectedApiCode) {
        assertBodyMatchesSchemaOrEmpty(response, API_RESPONSE_SCHEMA);

        String body = response.getBody().asString().trim();
        if (!body.isEmpty()) {
            response.then()
                    .time(lessThan(MAX_RESPONSE_TIME_MS))
                    .body("code", equalTo(expectedApiCode))
                    .body("type", equalTo("unknown"));
        }
    }

    /**
     * When a body is present, validate it against the given JSON schema.
     */
    private static void assertBodyMatchesSchemaOrEmpty(Response response, String schemaClasspath) {
        String body = response.getBody().asString().trim();
        if (!body.isEmpty()) {
            response.then().body(matchesJsonSchemaInClasspath(schemaClasspath));
        }
    }
}
