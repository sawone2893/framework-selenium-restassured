package com.framework.core.ui;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WebActions {

	WebElement findElement(String locatorType, String locatorValue);

	List<WebElement> findElements(String locatorType, String locatorValue);

	int getElementsCount(String locatorType, String locatorValue);

	boolean isDisplayed(String locatorType, String locatorValue);

	boolean isElementPresent(String locatorType, String locatorValue);

	void waitForElement(int timeInSeconds);

	boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime);

	boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime);

	void launchBrowser(String url);

	void maximiumBrowserWindow();

	void click(String locatorType, String locatorValue, int maxWaitTime);

	void type(String locatorType, String locatorValue, String text, int maxWaitTime);

	String getText(String locatorType, String locatorValue);

	void hoverAndClick(String locatorType,String srcLocator, String targetLocator,int maxWaitTime);

	void settingImplicitWait(long timeInSeconds);
	String getTitle();
	void captureScreenshot(String snapLocation);

}
