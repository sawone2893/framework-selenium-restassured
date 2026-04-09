package com.framework.utils;

import org.testng.Assert;

import com.framework.core.api.ApiResponse;

public class ResponseValidator {
	public static void validateStatusCode(ApiResponse response, int expected) {
        Assert.assertEquals(response.getStatusCode(), expected,
                "Status code mismatch!");
    }
	
	public static void validateResponseTime(ApiResponse response, long maxTime) {
        Assert.assertTrue(response.getResponseTime() <= maxTime,
                "Response time exceeded!");
    }
	
	public static void validateContains(ApiResponse response, String expected) {
        Assert.assertTrue(response.getBody().contains(expected),
                "Response does not contain expected value");
    }

}
