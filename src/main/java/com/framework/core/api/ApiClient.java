package com.framework.core.api;

public interface ApiClient {
	ApiResponse get(String endpoint, RequestData requestData);

    ApiResponse post(String endpoint, RequestData requestData);

    ApiResponse put(String endpoint, RequestData requestData);

    ApiResponse delete(String endpoint, RequestData requestData);

    ApiResponse patch(String endpoint, RequestData requestData);

}
