package com.framework.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.framework.core.api.auth.AuthManager;
import com.framework.core.api.manager.ApiManager;
import com.framework.core.api.manager.ScenarioContext;

public class ApiTestHooks {
	@BeforeMethod
	public void setup() {
		System.out.println("Thread: " + Thread.currentThread().getName() + " START");
	}

	@AfterMethod
	public void tearDown() {

		ApiManager.unload();
		ScenarioContext.clear();
		AuthManager.clear();

		System.out.println("Thread: " + Thread.currentThread().getName() + " END");
	}
}
