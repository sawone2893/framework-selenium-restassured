package com.framework.base;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.framework.utils.LoggerManager;

public class BaseTest {

	private final String BROWSER_TYPE = "CHROME";
	private final boolean IS_HEADLESS = false;
	private final String APP_URL = "https://automationteststore.com/";
	private final long implicitWaitInSeconds = 20;

	@BeforeClass
	public void setUp() {
		LoggerManager.get().info("Setting up browser...............................................");
		BaseClass.initiliazeBrowser(this.BROWSER_TYPE, this.IS_HEADLESS);
		BaseClass.fullScreenMode();
	}

	@BeforeMethod
	public void appLaunch(Method method) {
		LoggerManager.get().info("Open App Url...............................................");
		BaseClass.launchApp(this.APP_URL);
		BaseClass.applyImplicitWait(implicitWaitInSeconds);
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
