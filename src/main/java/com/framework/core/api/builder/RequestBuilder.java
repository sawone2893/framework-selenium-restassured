package com.framework.core.api.builder;

import com.framework.core.api.manager.ApiManager;
import com.framework.core.api.model.ApiResponse;
import com.framework.core.api.model.RequestData;

public class RequestBuilder {
	
	private RequestData data = new RequestData();

    public RequestBuilder header(String k, String v) {
        data.getHeaders().put(k, v);
        return this;
    }

    public RequestBuilder queryParam(String k, Object v) {
        data.getQueryParams().put(k, v);
        return this;
    }

    public RequestBuilder pathParam(String k, Object v) {
        data.getPathParams().put(k, v);
        return this;
    }

    public RequestBuilder body(Object b) {
        data.setBody(b);
        return this;
    }

    public ApiResponse get(String url) {
        return ApiManager.getClient().get(url, data);
    }

    public ApiResponse post(String url) {
        return ApiManager.getClient().post(url, data);
    }

    public ApiResponse put(String url) {
        return ApiManager.getClient().put(url, data);
    }

    public ApiResponse delete(String url) {
        return ApiManager.getClient().delete(url, data);
    }

}
