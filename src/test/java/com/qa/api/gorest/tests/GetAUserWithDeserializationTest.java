package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserWithDeserializationTest extends BaseTest{

	private String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId = "a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void createAUserTest() {
		User user = new User(null, "Prajwal", StringUtils.getRandomEmailId(), "male", "active");			
	
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "Prajwal");
		Assert.assertEquals(response.jsonPath().getString("gender"), "male");
		Assert.assertEquals(response.jsonPath().getString("status"), "active");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		
		String userID = response.jsonPath().getString("id");
		
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userID, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		
		User userResponse = JsonUtils.deserialize(responseGet, User.class);
		
		Assert.assertEquals(userResponse.getName(), user.getName());
		
	}
	
}
