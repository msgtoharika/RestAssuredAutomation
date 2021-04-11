package com.test.utils;

import io.restassured.path.json.JsonPath;

public class TestUtils {
	
	public static JsonPath convertJsonToString(String responseString)
	{
		JsonPath jp = new JsonPath(responseString);
		return jp;
	}

}
