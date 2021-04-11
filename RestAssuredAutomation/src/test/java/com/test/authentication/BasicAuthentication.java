package com.test.authentication;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BasicAuthentication {
	
	@Test
	public void verifyBasicAuth() {
		
		RestAssured.baseURI = "https://postman-echo.com";
		
		given().log().all().auth().preemptive().basic("postman", "password").
		when().get("/basic-auth").
		then().log().all().assertThat().body("authenticated", equalTo(true));
	}

}
