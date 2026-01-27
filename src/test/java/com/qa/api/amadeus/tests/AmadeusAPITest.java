package com.qa.api.amadeus.tests;

import java.util.Map;

import org.apache.groovy.util.Maps;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{

	private String token;
	
	@BeforeMethod
	public void getOAuth2Token() {
		Response response = restClient.post(BASE_URL_OAUTH2_AMADEUS, AMADEUS_OAUTH2_TOKEN_ENDPOINT, ConfigManager.get("clientid"), ConfigManager.get("clientsecret"), ConfigManager.get("granttype"), ContentType.URLENC);
	
		token = response.jsonPath().getString("access_token");
		System.out.println("Token: "+ token);
		ConfigManager.set("bearertoken", token);
	}
	
	@Test(enabled = false)
	public void getFlightDetailsTest() {
		
		Map<String, String> queryParams = Maps.of("origin", "PAR", "maxPrice", "200");
		
		Response response = restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_DEST_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
	}
	
}
