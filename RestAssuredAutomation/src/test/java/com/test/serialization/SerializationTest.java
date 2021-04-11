package com.test.serialization;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {

	@Test
	public void addPlaceUsingPOJO() {

		// set base url
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		Location location = new Location();
		location.setLat(-23.383494);
		location.setLng(33.427362);
		
		List<String> types = new ArrayList();
		types.add("shoe park");
		types.add("shop");
		
		AddPlacePayload ap = new AddPlacePayload();
		ap.setLocation(location);
		ap.setAccuracy(50);
		ap.setName("Harika");
		ap.setPhone_number("(+91) 8333054472");
		ap.setAddress("Bangalore, Karnataka");
		ap.setTypes(types);
		ap.setWebsite("https://abc123.com");
		ap.setLanguage("EN-India");
		

		given().log().all().queryParam("key", "qaclick123").body(ap).
			when().post("/maps/api/place/add/json").then().log().all();
		
		
	
		
		

	}

}
