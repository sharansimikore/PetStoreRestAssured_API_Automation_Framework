package api.endpoints;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import api.payload.User;
import io.restassured.response.Response;

import static io.restassured.matcher.RestAssuredMatchers.*;


public class UserEndpoints {

	
	public static Response CreateUser(User payload) {
		
		Response response=
		given()
			.accept("application/json")
			.contentType("application/json")
			.body(payload)
		
		.when()
			.post(Routes.post_url);
		
	return response;	
		
	}
	
	
	public static Response GetUser(String userName) {
		
		Response response=
		given()
		.pathParam("username", userName)
		
		.when()
		
			.get(Routes.get_url);
		
		return response;
		
	}

	
	public static Response UpdateUser(String userName, User payload) {
	
		Response response=
		given()
			.accept("application/json")
			.contentType("application/json")
			.pathParam("username", userName)
			.body(payload)
		
		.when()
			.put(Routes.update_url);
		
	return response;	
}

	public static Response DeleteUser(String userName) {
	
		Response response=
				given()
				.pathParam("username", userName)
				
				.when()
				
					.delete(Routes.delete_url);
				
				return response;
}	
	
}
