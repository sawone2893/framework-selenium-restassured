package com.framework.core.api.model;

import java.util.HashMap;
import java.util.Map;

public class RequestData {
	
	private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> queryParams = new HashMap<>();
    private Map<String, Object> pathParams = new HashMap<>();
    private Object body;

    // Getters
    public Map<String, String> getHeaders() { return headers; }
    public Map<String, Object> getQueryParams() { return queryParams; }
    public Map<String, Object> getPathParams() { return pathParams; }
    public Object getBody() { return body; }

    // Setters
    public void setBody(Object body) { this.body = body; }

}
