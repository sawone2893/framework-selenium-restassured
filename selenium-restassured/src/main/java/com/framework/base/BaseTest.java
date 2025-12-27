package com.framework.base;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import java.lang.reflect.Method;
import com.framework.utils.LoggerManager;

public class BaseTest {
	
	protected BaseClass base;
	private final String BROWSER_TYPE="CHROME";
	private final boolean IS_HEADLESS=false;
	private final String APP_URL="https://automationteststore.com/";
	private final long implicitWaitInSeconds=20;
	
	@BeforeTest
	public void setUp() {
		base= new BaseClass();
		LoggerManager.get().info("Setting up browser...............................................");
		base.initiliazeBrowser(this.BROWSER_TYPE, this.IS_HEADLESS);
		base.fullScreenMode();
	}
	
	@BeforeClass
	public void appLaunch() {
		LoggerManager.get().info("Open App Url...............................................");
		base.launchApp(this.APP_URL);
		base.applyImplicitWait(implicitWaitInSeconds);
	}
	
	@AfterTest
	public void tearDown() {
		LoggerManager.get().info("Closing browser...............................................");
		base.closeBrowser();
	}
	
	@BeforeMethod
    public void before(Method method) {
        LoggerManager.startTest(method.getName(), this.getClass());
    }

    @AfterMethod
    public void after(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            LoggerManager.get().error("TEST FAILED", result.getThrowable());
            String snapPath=System.getProperty("user.dir")+"/test-output/reports/screenshots/";
            base.captureSnap(snapPath+result.getName()+".png");
        }
        LoggerManager.endTest();
    }

}
