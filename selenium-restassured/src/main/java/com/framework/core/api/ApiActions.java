package com.framework.core.api;

public interface ApiActions {
	io.restassured.response.Response post(String endpoint, Object body);
    io.restassured.response.Response get(String endpoint);
}
