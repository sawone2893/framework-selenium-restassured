package com.framework.base;

import com.framework.core.driverFactory.DriverManager;
import com.framework.core.ui.WebActions;
import com.framework.core.ui.WebActionsSelenium;

public class BaseClass {

	public WebActions globalDriver;
	private int maxTime = 120;

	public void initiliazeBrowser(String browserType, boolean isHeadless) {
		this.globalDriver = new WebActionsSelenium(DriverManager.getInstance(browserType, isHeadless).getDriver());
	}

	public void fullScreenMode() {
		this.globalDriver.maximiumBrowserWindow();
	}

	public void closeBrowser() {
		DriverManager.quitDriver();
	}

	public void launchApp(String url) {
		this.globalDriver.launchBrowser(url);
	}

	public void clickElement(String locatorType, String locatorValue) {
		this.globalDriver.click(locatorType, locatorValue, this.maxTime);
	}

	public void enterText(String locatorType, String locatorValue, String textToEnter) {
		this.globalDriver.type(locatorType, locatorValue, textToEnter, maxTime);
	}

	public void hoverAndClickElement(String locatorType, String srcLocator, String targetLocator) {
		this.globalDriver.hoverAndClick(locatorType, srcLocator, targetLocator, maxTime);
	}

	public void applyImplicitWait(long timeInSeconds) {
		this.globalDriver.settingImplicitWait(timeInSeconds);
	}

	public String getPageTitle() {
		return globalDriver.getTitle();
	}

	public void captureSnap(String snapLocation) {
		this.globalDriver.captureScreenshot(snapLocation);
	}

}
