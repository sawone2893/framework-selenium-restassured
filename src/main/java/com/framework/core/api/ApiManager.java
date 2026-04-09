package com.framework.core.api;

import com.framework.utils.EnvConfig;

public class ApiManager {
	
	private static ThreadLocal<ApiClient> client = new ThreadLocal<>();

    public static ApiClient getClient() {

        if (client.get() == null) {
            client.set(ApiClientFactory.getClient(EnvConfig.get("restassured")));
        }

        return client.get();
    }

    public static void unload() {
        client.remove(); // VERY IMPORTANT
    }

}
