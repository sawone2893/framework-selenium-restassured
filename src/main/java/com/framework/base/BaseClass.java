package com.framework.base;

import com.framework.core.driverFactory.DriverManager;
import com.framework.core.ui.WebActions;
import com.framework.core.ui.WebActionsSelenium;

public class BaseClass {

	public static WebActions globalDriver;
	private static int maxTime = 120;

	public static void initiliazeBrowser(String browserType, boolean isHeadless) {
		DriverManager dManager=DriverManager.getInstance();
		dManager.setDriver(browserType, isHeadless);
		globalDriver = new WebActionsSelenium(dManager.getDriver());
	}

	public static void fullScreenMode() {
		globalDriver.maximiumBrowserWindow();
	}

	public static void closeBrowser() {
		DriverManager.quitDriver();
	}

	public static void launchApp(String url) {
		globalDriver.launchBrowser(url);
	}

	public static void clickElement(String locatorType, String locatorValue) {
		globalDriver.click(locatorType, locatorValue, maxTime);
	}

	public static void enterText(String locatorType, String locatorValue, String textToEnter) {
		globalDriver.type(locatorType, locatorValue, textToEnter, maxTime);
	}

	public static void hoverAndClickElement(String locatorType, String srcLocator, String targetLocator) {
		globalDriver.hoverAndClick(locatorType, srcLocator, targetLocator, maxTime);
	}

	public static void applyImplicitWait(long timeInSeconds) {
		globalDriver.settingImplicitWait(timeInSeconds);
	}

	public static String getPageTitle() {
		return globalDriver.getTitle();
	}

	public static void captureSnap(String snapLocation) {
		globalDriver.captureScreenshot(snapLocation);
	}

}
