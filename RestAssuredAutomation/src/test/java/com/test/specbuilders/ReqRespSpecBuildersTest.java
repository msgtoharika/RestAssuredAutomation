package com.test.specbuilders;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.test.payloads.AddPlacePayload;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ReqRespSpecBuildersTest {
	
	@Test
	public void verifyReqAndRespSpecBuilders() {
		
		
		
		RequestSpecification reqSpec = new RequestSpecBuilder().
				setBaseUri("https://rahulshettyacademy.com/")
				.setContentType(ContentType.JSON).
				addQueryParam("key", "qaclick123").build();
		
		ResponseSpecification respSpec = new ResponseSpecBuilder().expectStatusCode(200).
				expectContentType(ContentType.JSON).
				expectStatusLine("HTTP/1.1 200 OK").build();
		
		//add place
		given().log().all().spec(reqSpec).
		body(AddPlacePayload.addPlacePayload()).
		when().post("/maps/api/place/add/json").then().log().all().spec(respSpec);
		
		
		//get Place
		given().log().all().spec(reqSpec).queryParam("place_id", "195e883b10d958079dcf26a83f202b27").
		when().get("/maps/api/place/get/json").then().log().all().spec(respSpec);
		
		
	
		
	}

}
