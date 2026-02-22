package com.qa.graphql.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

//mutation = create, modify, delete
public class GraphQLMutationUser {

	private static final String GRAPHQL_ENDPOINT = "https://gorest.co.in/public/v2/graphql";

	@Test
	public void mutateUserWithGraphQLTest() {

		String mutation = "mutation CreateUser {\n" + "  createUser(\n" + "    input: {\n" + "      name: \"prajwal\"\n"
				+ "      email: \"prajwalapi@gmail.com\"\n" + "      gender: \"male\"\n" + "      status: \"active\"\n"
				+ "    }\n" + "  ) {\n" + "    user {\n" + "      email\n" + "      gender\n" + "      id\n"
				+ "      name\n" + "      status\n" + "    }\n" + "  }\n" + "}";

		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("query", mutation);

		given().log().all().contentType(ContentType.JSON)
				.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
				.body(requestBody).when().post(GRAPHQL_ENDPOINT).then().log().all().assertThat().statusCode(200)
				.body("data.createuser.user.id", notNullValue()).body("data.createuser.user.name", equalTo("prajwal"))
				.body("data.createuser.user.gender", equalTo("male"))
				.body("data.createuser.user.status", equalTo("active"));
	}

	private String readGraphQLQuery(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}
	
	@Test
	public void mutateUserWithGraphQLAndFileTest() throws IOException {

		String mutationQuery = readGraphQLQuery("src/test/resources/graphql/createUserMutation.graphql");
		String mutation = String.format(mutationQuery, "prajwal@gmail.com");

		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("query", mutation);

		given().log().all().contentType(ContentType.JSON)
				.header("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
				.body(requestBody).when().post(GRAPHQL_ENDPOINT).then().log().all().assertThat().statusCode(200)
				.body("data.createuser.user.id", notNullValue()).body("data.createuser.user.name", equalTo("prajwal"))
				.body("data.createuser.user.gender", equalTo("male"))
				.body("data.createuser.user.status", equalTo("active"));
	}

}
