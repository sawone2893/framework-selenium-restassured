package com.framework.core.api;

import java.util.Map;

public class ApiResponse {

	private int statusCode;
	private String body;
	private Map<String, String> headers;
	private long responseTime;

	public ApiResponse(int statusCode, String body, Map<String, String> headers, long responseTime) {
		this.statusCode = statusCode;
		this.body = body;
		this.headers = headers;
		this.responseTime = responseTime;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	public boolean hasHeader(String key) {
		return headers.containsKey(key);
	}
	
	public void prettyPrint() {
        System.out.println("========== API RESPONSE ==========");
        System.out.println("Status Code : " + statusCode);
        System.out.println("Response Time : " + responseTime + " ms");
        System.out.println("Headers : " + headers);
        System.out.println("Body : ");
        System.out.println(body);
        System.out.println("==================================");
    }

}
