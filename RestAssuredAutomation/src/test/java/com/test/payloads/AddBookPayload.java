package com.test.payloads;

import org.apache.commons.lang3.RandomStringUtils;

public class AddBookPayload {
	
	public static String addBook(String isbn, String aisle) {
		String addBook = "{\r\n"
				+ "\r\n"
				+ "\"name\":\""+RandomStringUtils.randomAlphabetic(7)+"\",\r\n"
				+ "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n"
				+ "\"author\":\"ABC123\"\r\n"
				+ "}\r\n"
				+ "";
		return addBook;
	}

}
