package com.test.jira;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class GetSessionCookie {
	
	
	@Test
	public void generateSessionCookie() {
		
		RestAssured.baseURI = "http://localhost:8080/";
		
		String response = given().log().all().contentType("application/json").body("{ \"username\": \"harika\", \"password\": \"Test@123\" }")
				.when().post("/rest/auth/1/session")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String cookie = js.getString("session.value");
		System.out.println("cookie----------->"+cookie);
		
	}

}
