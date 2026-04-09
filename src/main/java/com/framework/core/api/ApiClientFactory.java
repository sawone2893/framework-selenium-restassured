package com.framework.core.api;

public class ApiClientFactory {

	public static ApiClient getClient(String clientType) {

		switch (clientType.toLowerCase()) {
		case "restassured":
			return new RestAssuredClient();
		default:
			throw new RuntimeException("Invalid client type");
		}
	}

}
