package com.framework.tests.api;

import static com.framework.core.api.builder.RequestBuilderFactory.request;

import org.testng.annotations.Test;

import com.framework.core.api.manager.ScenarioContext;
import com.framework.core.api.validator.ResponseValidator;

public class UserTest {
	@Test
	public void testFlow() {

		// Step 1: Create User
		var createRes = request().header("Content-Type", "application/json").body("""
				  {
				    "name": "FlowUser",
				    "job": "QA"
				  }
				""").post("/users");

		ResponseValidator.validate(createRes).statusCode(201);

		String userId = createRes.getJsonValue("id");

		ScenarioContext.set("userId", userId);

		// Step 2: Get User
		var getRes = request().pathParam("id", ScenarioContext.get("userId")).get("/users/{id}");

		ResponseValidator.validate(getRes).statusCode(200);
	}
}
