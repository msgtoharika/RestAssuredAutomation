package com.test.maps;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.payloads.AddPlacePayload;
import com.test.utils.TestUtils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;

public class AddPlaceUpdatePlaceAndGetPlaceTest {
	
	
	@Test
	public void addPlaceToMaps() {
		
		//given --> all input details
		//when --> submit the api
		//then --> validate the response
		
		
		//set base url
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		

		//create request specification and add query param and body
		RequestSpecification httpReq = RestAssured.given();
		httpReq.queryParam("key", "qaclick123");
		httpReq.header("Content-Type", "application/json");
		httpReq.body(AddPlacePayload.addPlacePayload());
		httpReq.log().all();
		
		//hit the request
		Response response = httpReq.when().post("/maps/api/place/add/json");
	
		
		//perform validation
		response.then().log().all().assertThat().statusCode(200);
		response.then().assertThat().body("scope", equalTo("APP"));
		response.then().assertThat().header("Server", "Apache/2.4.18 (Ubuntu)");
		
		//Fetch place id from the response
		JsonPath jsonPath = new JsonPath(response.then().extract().asString());
		String place_id = jsonPath.getString("place_id");
		System.out.println("Place id ---------> "+place_id);
		
		
		
		String newAddress = "70 Summer walk, USA";
		//update the place address now 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "    \"place_id\": \""+place_id+"\",\r\n"
				+ "    \"address\": \""+newAddress+"\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}").
		when().put("maps/api/place/update/json").
	    then().log().all().assertThat().statusCode(200);
		
		
		//get address and check the updated address
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",place_id).
		when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js2 = TestUtils.convertJsonToString(getResponse);
		String address = js2.getString("address");
		System.out.println("address---------->"+address);
		Assert.assertEquals(address, newAddress);
		
		
		
		
		
		
	}

}
