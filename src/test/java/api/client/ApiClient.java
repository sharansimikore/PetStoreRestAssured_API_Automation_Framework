package api.client;

import static io.restassured.RestAssured.given;

import api.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Central Rest Assured request builder with shared headers and timeouts.
 */
public final class ApiClient {

    private static RequestSpecification baseSpec;

    private ApiClient() {
    }

    public static void init() {
        baseSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUri())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .log(LogDetail.URI)
                .build();
    }

    public static RequestSpecification request() {
        if (baseSpec == null) {
            init();
        }
        return given().spec(baseSpec);
    }
}
