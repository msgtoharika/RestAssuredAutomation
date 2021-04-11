package com.test.jira;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;

import org.testng.annotations.Test;

import com.test.payloads.CreateNewJiraPayload;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class TestJiraApis {

	@Test
	public void createNewJira() {

		RestAssured.baseURI = "http://localhost:8080/";
		
		
		//get session cookie
		SessionFilter session = new SessionFilter();
		String response = given().relaxedHTTPSValidation().log().all().contentType("application/json")
				.body("{ \"username\": \"harika\", \"password\": \"Test@123\" }").filter(session).
				when().post("/rest/auth/1/session")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();


		//create jira
		String response1 = given().log().all().contentType("application/json").filter(session).body(CreateNewJiraPayload.addPayload()).
				when().post("/rest/api/2/issue").
				then().log().all().extract().response().asString();
		JsonPath jp = new JsonPath(response1);
		String id = jp.getString("id");
		System.out.println("id---------->"+id);
		
		
		//add comment
		given().pathParam("key", id).log().all().body(CreateNewJiraPayload.addComment()).filter(session).contentType("application/json").when().post("/rest/api/2/issue/{key}/comment").then().
		assertThat().statusCode(201).log().all();
		
		
		//add comment
		String response2 = given().pathParam("key", id).log().all().body(CreateNewJiraPayload.addComment()).filter(session).contentType("application/json").when().post("/rest/api/2/issue/{key}/comment").then().
				assertThat().statusCode(201).log().all().extract().response().asString();
		JsonPath js2 = new JsonPath(response2);
		String commentId = js2.get("id");
		System.out.println("commentId----------->"+commentId);
		
		//add attachments
		//curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" 
		//http://myhost/rest/api/2/issue/TEST-123/attachments
		given().pathParam("key", id).log().all().
		header("X-Atlassian-Token", "no-check").
		header("Content-Type", "multipart/form-data").
		multiPart("file", new File("sample.txt")).
		filter(session).
		when().post("/rest/api/2/issue/{key}/attachments").
		then().log().all().statusCode(200);
		
		
		//Get issue and restrict the response using query param
		String response3 = given().log().all().filter(session).pathParam("key", id).queryParam("fields", "comment").
		when().get("/rest/api/2/issue/{key}").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		//fetch the comments and check description for a particular message
		JsonPath js3 = new JsonPath(response3);
		List<String> arr = js3.get("fields.comment.comments");
		System.out.println("comments array size is "+arr.size());
		
		for(int i=0; i<arr.size(); i++) {
			String idOfComment = js3.getString("fields.comment.comments["+i+"].id");
			if(idOfComment.equals(commentId)) {
				System.out.println(js3.getString("fields.comment.comments["+i+"].body"));
				break;
			}
		}

	}                                                                 

}
