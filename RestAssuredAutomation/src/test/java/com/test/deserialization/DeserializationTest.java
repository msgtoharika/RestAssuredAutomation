package com.test.deserialization;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class DeserializationTest {

	@Test
	public void deserializationTest() throws InterruptedException {

		// fetch code from google sign in

		String getCodeUrl = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/"
				+ "userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&"
				+ "client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&"
				+ "response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=haVweo";
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\haa\\eclipse-workspace\\RestAssuredAutomation\\driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(getCodeUrl);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("t83784374837@gmail.com");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test@123#$");
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(6000);

		String codeUrl = driver.getCurrentUrl();
		System.out.println("codeUrl----------------->" + codeUrl);
		driver.close();

		String code = codeUrl.substring(codeUrl.indexOf("code=") + 5, codeUrl.indexOf("&scope"));
		System.out.println("code-------------->" + code);

		// get access token
		RestAssured.baseURI = "https://www.googleapis.com";

		String response = given().log().all().urlEncodingEnabled(false).queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().post("/oauth2/v4/token").then().log().all()
				.extract().response().asString();

		JsonPath js = new JsonPath(response);
		String access_token = js.getString("access_token");
		System.out.println("access_token----------->" + access_token);

		// get courses
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		GetCourses courses = given().queryParam("access_token", access_token)
				.expect().defaultParser(Parser.JSON).
				when().get("/getCourse.php").as(GetCourses.class);
		
		
		
		//Fetch linkedIn Url
		String linkedInUrl = courses.getLinkedIn();
		System.out.println("linkedin url ---> "+linkedInUrl);
		
		//Fetch instructor name
		String instructor = courses.getInstructor();
		System.out.println("instructor ---> "+instructor);
		
		//get the amount of SoapUI Webservices testing
		List<API> apiCourses = courses.getCourses().getApi();
		for(API api: apiCourses) {
			if(api.getCourseTitle().equals("SoapUI Webservices testing")) {
				System.out.println("Price of SoapUI Webservices testing course is -->"+api.getPrice());
				break;
			}
		}	
		
		//print all the course titles of WebAutomation
		List<WebAutomation> allWeb = courses.getCourses().getWebAutomation();
		System.out.println("The courses under web automation are :");
		for(WebAutomation wa : allWeb) {
			System.out.println(wa.getCourseTitle());
		}
		
		
	}

}
