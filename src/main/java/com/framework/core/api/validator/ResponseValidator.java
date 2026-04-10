package com.framework.core.api.validator;

import org.testng.Assert;

import com.framework.core.api.model.ApiResponse;

public class ResponseValidator {
	private ApiResponse res;

	public ResponseValidator(ApiResponse res) {
		this.res = res;
	}

	public ResponseValidator statusCode(int code) {
		Assert.assertEquals(res.getStatusCode(), code);
		return this;
	}

	public ResponseValidator contains(String val) {
		Assert.assertTrue(res.getBody().contains(val));
		return this;
	}

	public ResponseValidator timeLessThan(long ms) {
		Assert.assertTrue(res.getResponseTime() < ms);
		return this;
	}

	public static ResponseValidator validate(ApiResponse res) {
		return new ResponseValidator(res);
	}

}
