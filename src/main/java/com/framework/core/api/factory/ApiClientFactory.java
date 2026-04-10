package com.framework.core.api.factory;

import com.framework.core.api.client.ApiClient;
import com.framework.core.api.client.impl.RestAssuredClient;

public class ApiClientFactory {

	public static ApiClient getClient(String clientType) {

		switch (clientType.toUpperCase()) {
		case "RESTASSURED":
			return new RestAssuredClient();
		default:
			throw new RuntimeException("Invalid client type");
		}
	}

}
