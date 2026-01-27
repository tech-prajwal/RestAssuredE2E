package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.qa.api.utils.SchemaValidator;

public class GoRestUserAPISchemaTest extends BaseTest{
	
	@Test
	public void getUsersAPISchemaTest() {	
		ConfigManager.set("bearertoken", "a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea");
		
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
	
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getuserschema.json"));
	
	}
	
	
	@Test
	public void createUserAPISchemaTest() {	
		ConfigManager.set("bearertoken", "a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea");
		
		User user = User.builder()
		.name("api")
		.status("active")
		.email(StringUtils.getRandomEmailId())
		.gender("male")
		.build();
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/createuserschema.json"));
	
	}
	
	

}
