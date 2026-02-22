package com.qa.graphql.api.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GraphQLQueryUser {

	private static final String GRAPHQL_ENDPOINT = "https://gorest.co.in/public/v2/graphql";
	
	@Test
	public void getUserQueryWithGraphQL() {
		
		String query = "query User($userId:ID!) {\n"
	            + "    user(id: $userId) {\n"
	            + "        email\n"
	            + "        gender\n"
	            + "        id\n"
	            + "        name\n"
	            + "        status\n"
	            + "    }\n"
	            + "}";

	    Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put("userId", 7516106);

	    Map<String, Object> requestBody = new HashMap<String, Object>();
	    requestBody.put("query", query);
	    requestBody.put("variables", variables);

	    given().log().all()
	            .contentType(ContentType.JSON)
	            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
	            .body(requestBody)
	    .when()
	            .post(GRAPHQL_ENDPOINT)
	    .then().log().all()
	            .assertThat()
	            .statusCode(200)
	            .body("data.user.id", equalTo(7516106))
	            .body("data.user.name", equalTo("prajwal"))
	            .body("data.user.status", equalTo("active"))
	            .body("data.user.email", equalTo("prajwal1@gmail.com"));
	}
	
	private String readGraphQLQuery(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}
	
	@Test
	public void getUserQueryWithFileTest() throws IOException {
		
		String query = readGraphQLQuery("src/test/resources/graphql/getUserQuery.graphql");

	    Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put("userId", 7516106);

	    Map<String, Object> requestBody = new HashMap<String, Object>();
	    requestBody.put("query", query);
	    requestBody.put("variables", variables);

	    given().log().all()
	            .contentType(ContentType.JSON)
	            .header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
	            .body(requestBody)
	    .when()
	            .post(GRAPHQL_ENDPOINT)
	    .then().log().all()
	            .assertThat()
	            .statusCode(200)
	            .body("data.user.id", equalTo(7516106))
	            .body("data.user.name", equalTo("prajwal"))
	            .body("data.user.status", equalTo("active"))
	            .body("data.user.email", equalTo("prajwal1@gmail.com"));
	}
}
