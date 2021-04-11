package com.test.maps;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.payloads.CoursePrice;

import io.restassured.path.json.JsonPath;

public class ParseComplexJson {
	
	
	@Test
	public void parseJson() {
		
		JsonPath js = new JsonPath(CoursePrice.courses());
		
		//print no. of courses
		List<String> arr = js.get("courses");
		int size = arr.size();
		System.out.println("size---->"+size);
		
		
		//print purchaseAmt
		int purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println("purchaseAmt------>"+purchaseAmt);
		
		
		//print first course
		String title = js.get("courses[0].title");
		System.out.println("title------>"+title);
		
		//print all courses and respective prices
		List<String> allCourses = js.get("courses");
		for(int i=0; i<allCourses.size(); i++) {
			System.out.println(js.get("courses["+i+"].title") + "---> "+js.get("courses["+i+"].price"));
		}
		
		
		//print copies sold by RPA
		for(int i=0; i<allCourses.size(); i++) {
			if((js.get("courses["+i+"].title").equals("RPA"))){
				System.out.println("Copies sold by RPA ---> "+js.get("courses["+i+"].copies"));
		}
		
		}
		
		
		int sum = 0;
		//check if the purchase amt is same as the prices of all courses
		for(int i=0; i<allCourses.size(); i++) {
			
			int price = js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
			sum = sum + price;
		}
		
		System.out.println("sum---->"+sum);
		Assert.assertEquals(sum, purchaseAmt);
		
		
	}

}
