package com.test.authentication;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class BearerTokenAuthentication {
	
	@Test
	public void verifyBearerToken() {
		
		
		RestAssured.baseURI = "https://api.github.com/";
		
		String token = "f78def4ffe3ed5b3ce0547f7659fdd9d8e081c08";
		
		//Bearer f78def4ffe3ed5b3ce0547f7659fdd9d8e081c08
		given().contentType("application/json").header("Authorization", "Bearer "+token).body("{\r\n"
				+ "    \"name\":\"sampleRepo4\",\r\n"
				+ "    \"description\": \"creating my fourth repository through api\",\r\n"
				+ "    \"homepage\": \"https://github.com\",\r\n"
				+ "    \"private\": false, \r\n"
				+ "    \"has_projects\": true,\r\n"
				+ "    \"has_issue\":true,\r\n"
				+ "    \"has_wiki\": true\r\n"
				+ "}").
		when().post("/user/repos").then().log().all().assertThat().statusCode(201);
	}

}
