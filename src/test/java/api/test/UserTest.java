package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	Faker faker;
	User userPayload;
	public Logger logger;//for logs

	@BeforeClass
	public void setup()
	{
		
		faker = new Faker();
		userPayload = new User();
		
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setUsername(faker.name().username());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		
//		Logs
		
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("********** Creating User ****************");		
		Response response=UserEndpoints.CreateUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********** User Created ****************");
		
	}
	
	@Test(priority=2)
	public void testGetUser()
	{
	
		logger.info("********** Get  User ****************");
		
	Response response=UserEndpoints.GetUser(this.userPayload.getUsername());
	response.then().log().all();
	response.statusCode();
	
	Assert.assertEquals(response.getStatusCode(), 200);
	
	logger.info("********** Got All Usesr ****************");
	}
	
	
	@Test(priority=3)
	public void testupdateUserByName() {
		logger.info("********** Update User ****************");
		
//		Update data using Payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
//		userPayload.setUsername(faker.name().username());

		
		Response response=UserEndpoints.UpdateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
//		Checking data after Update
		Response responseAfterUpdate=UserEndpoints.GetUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		responseAfterUpdate.statusCode();
		
		logger.info("********** User Updated****************");
	}
	
	@Test(priority=4)
	public void testDeleteUser()
	{
	
		logger.info("********** Deleting User ****************");
		
	Response response=UserEndpoints.DeleteUser(this.userPayload.getUsername());
	response.then().log().all();
	response.statusCode();
	
	Assert.assertEquals(response.getStatusCode(), 200);
	
	logger.info("**********  User Deleted****************");
	}
	
}
