package com.test.libraryapi;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.payloads.AddBookPayload;
import com.test.utils.ReadExcelData;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

public class TestLibraryAPiWithDataProvider {
	
	
	@DataProvider(name="getBookdata")
	public Object[][] fetchDataFromCSV() {
		Object[][] data = ReadExcelData.getData("addBook");
		return data;
	}
	
	@Test(dataProvider = "getBookdata" )
	public void addBook(String isbn, String aisle) {
		if(aisle.contains(".0")) {
			aisle =aisle.replace(".0", "");
		}
		RestAssured.baseURI = "http://216.10.245.166/";
		
		
		//add book
		String response = given().log().all().header("Content-Type", "application/json").
		body(AddBookPayload.addBook(isbn, aisle)).
		when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		System.out.println("id----->"+id);
		
		//delete book
		given().log().all().body("{\"ID\" : \""+id+"\"} ").
		when().post("/Library/DeleteBook.php").
		then().log().all().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));
		
	}

}
