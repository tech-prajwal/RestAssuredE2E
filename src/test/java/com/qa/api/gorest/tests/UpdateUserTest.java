package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest{
	
	@Test
	public void updateUserTest() {
		//1. create a user - POST
		User user  = User.builder()
						.name("Prajwal")
						.email(StringUtils.getRandomEmailId())
						.status("active")
						.gender("male")
						.build();		
		Response responsePost = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePost.jsonPath().getString("name"), "Prajwal");
		Assert.assertNotNull(responsePost.jsonPath().getString("id"));
		
		//fetch the userId:
		String userId = responsePost.jsonPath().getString("id");
		System.out.println("user id ===>" + userId);
		
		//2. GET: fetch the user using the same user id:
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
		//3. Update the user using the same user id:
		user.setName("PrajwalAutomation");
		user.setStatus("inactive");
		Response responsePut = restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, user, null,null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responsePut.statusLine().contains("OK"));
		Assert.assertEquals(responsePut.jsonPath().getString("name"), "PrajwalAutomation");
		Assert.assertEquals(responsePut.jsonPath().getString("status"), "inactive");
		Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
		
		//4. GET: fetch the user using the same user id:
		responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		Assert.assertEquals(responseGet.jsonPath().getString("name"), "PrajwalAutomation");
		Assert.assertEquals(responseGet.jsonPath().getString("status"), "inactive");
	}
}
