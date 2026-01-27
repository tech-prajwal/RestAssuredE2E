package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest{
	
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
	}
	
	@Test
	public void createAUserTestWithJsonString() {
		String userJson = "{\n"
				+ "\"name\": \"prajwal\",\n"
				+ "\"email\": \"prajwal8089@gmail.com\",\n"
				+ "\"gender\": \"male\",\n"
				+ "\"status\": \"active\"\n"
				+ "}";
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "prajwal");
		Assert.assertNotNull(response.jsonPath().getString("id"));	
	}
	
	
	@Test
	public void createAUserTestWithJsonfile() throws IOException {

//String emailId = StringUtils.getRandomEmailId();
//		
//		//convert json file content to string
//		String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
//		String updatedJson = rawJson.replace("{{email}}", emailId);
//	
//		
		
		File userFile = new File("./src/test/resources/jsons/user.json");
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "Tom");
		Assert.assertNotNull(response.jsonPath().getString("id"));	
	}
	
}
