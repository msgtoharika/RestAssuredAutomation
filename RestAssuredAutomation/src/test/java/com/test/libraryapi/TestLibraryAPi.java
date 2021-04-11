package com.test.libraryapi;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import com.test.payloads.AddBookPayload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

public class TestLibraryAPi {
	
	
	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166/";
		
		
		//add book
		String response = given().log().all().header("Content-Type", "application/json").
				body(AddBookPayload.addBook(RandomStringUtils.randomAlphabetic(4), "456")).
			when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		System.out.println("id----->"+id);
		
		
		//delete book
		given().log().all().body("{\"ID\" : \""+id+"\"} ")
		.when().post("/Library/DeleteBook.php").
		then().log().all().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));
		
		
		
	}

}
