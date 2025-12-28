package com.framework.base;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.framework.utils.EnvConfig;
import com.framework.utils.LoggerManager;

public class BaseTest {

	@BeforeClass
	public void setUp() {
		// Load config
		EnvConfig.init();
		LoggerManager.get().info("Setting up browser...............................................");
		BaseClass.initiliazeBrowser(EnvConfig.get("BROWSER_TYPE"), EnvConfig.getBool("IS_HEADLESS"));
		BaseClass.fullScreenMode();
	}

	@BeforeMethod
	public void appLaunch(Method method) {
		LoggerManager.get().info("Open App Url...............................................");
		BaseClass.launchApp(EnvConfig.get("APP_URL"));
		BaseClass.applyImplicitWait(EnvConfig.getInt("IMPLICIT_WAIT_IN_SECONDS"));
		LoggerManager.startTest(method.getName(), this.getClass());
	}

	@AfterMethod
	public void after(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			LoggerManager.get().error("TEST FAILED", result.getThrowable());
			String snapPath = System.getProperty("user.dir") + "/test-output/reports/screenshots/";
			BaseClass.captureSnap(snapPath + result.getName() + ".png");
		}
		LoggerManager.endTest();
	}

	@AfterClass
	public void tearDown() {
		LoggerManager.get().info("Closing browser...............................................");
		BaseClass.closeBrowser();
	}

}
