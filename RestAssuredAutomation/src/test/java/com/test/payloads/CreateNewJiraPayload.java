package com.test.payloads;

import org.apache.commons.lang3.RandomStringUtils;

public class CreateNewJiraPayload {
	
	public static String addPayload() {
		String x = "{\r\n"
				+ "\r\n"
				+ "\"fields\": {\r\n"
				+ "    \"project\": {\r\n"
				+ "        \"key\": \"HAR\"\r\n"
				+ "        },\r\n"
				+ "    \"summary\": \"Rest assured test 2 \",\r\n"
				+ "    \"description\": \"creating new issue through rest api 2\",\r\n"
				+ "    \"issuetype\":{\r\n"
				+ "        \"name\": \"Bug\"\r\n"
				+ "    }\r\n"
				+ "}\r\n"
				+ "}";
		return x;
	}
	
	public static String addComment() {
		return "{\r\n"
				+ "    \"body\": \"Hey!! i have commented from REST API. "+RandomStringUtils.randomAlphabetic(5)+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
	}

}
