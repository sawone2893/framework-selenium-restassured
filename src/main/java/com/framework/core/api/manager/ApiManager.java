package com.framework.core.api.manager;

import com.framework.core.api.client.ApiClient;
import com.framework.core.api.factory.ApiClientFactory;
import com.framework.utils.EnvConfig;

public class ApiManager {
	
	private static ThreadLocal<ApiClient> client = new ThreadLocal<>();

    public static ApiClient getClient() {

        if (client.get() == null) {
            client.set(ApiClientFactory.getClient(EnvConfig.get("API.CLIENTTYPE")));
        }

        return client.get();
    }

    public static void unload() {
        client.remove(); // VERY IMPORTANT
    }

}
