package com.test.authentication;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class HandleApiKeys {

	@Test
	public void verifyTestUsingAPiKeys() {

		RestAssured.baseURI = "https://api.openweathermap.org/";

		given().log().all().queryParam("q", "Delhi").queryParam("cnt", 2).queryParam("appid", "fe9c5cddb7e01d747b4611c3fc9eaf2c").
		when().get("/data/2.5/forecast/daily").then().log()
				.all().assertThat().statusCode(200);
	}

}
