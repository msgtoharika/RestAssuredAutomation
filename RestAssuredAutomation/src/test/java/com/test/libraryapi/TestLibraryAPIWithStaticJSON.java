package com.test.libraryapi;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import com.test.payloads.AddBookPayload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestLibraryAPIWithStaticJSON {
	
	
	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166/";
		
		String body = "";
		Path path = Paths.get("C:\\Users\\haa\\OneDrive - Adobe\\Documents\\API Stuff\\AddBook.json");
		try {
			 body = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add book
		String response = given().log().all().header("Content-Type", "application/json").body(body).
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
