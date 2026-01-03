package com.framework.core.ui;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WebActions {

	WebElement findElement(String locatorType, String locatorValue);

	List<WebElement> findElements(String locatorType, String locatorValue);

	int getElementsCount(String locatorType, String locatorValue);

	boolean isDisplayed(String locatorType, String locatorValue);

	boolean isElementPresent(String locatorType, String locatorValue);

	public void waitForPageLoad(int time);

	void waitForElement(int timeInSeconds);

	boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime);

	boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime);

	void launchBrowser(String url);

	void maximiumBrowserWindow();

	void highlight(String locatorType, String locatorValue);

	void click(String locatorType, String locatorValue);

	void type(String locatorType, String locatorValue, String text);

	String getText(String locatorType, String locatorValue);

	void hover(String locatorType, String locator);

	void settingImplicitWait(long timeInSeconds);

	String getTitle();

	void captureScreenshot(String snapLocation);

	void doubleClick(String locatorType, String locator);

	void rightClick(String locatorType, String locator);

	void controlClick(String locatorType, String locator);

	void dragAndDrop(String locatorType, String srcLocator, String targetLocator);

	public Object executeJSAction(String script, Object... args);

	void scrollVertically();

	void scrollHorizontally();

	void scrollIntoView(String locatorType, String locator);

	void acceptAlert();

	void dismissAlert();

	void typeInAlert(String text);

	String getAlertText();

	void switchIframe(String name);

	void switchIframe(int index);

	void switchIframe(String locatorType, String locator);
	String getBase64Screenshot();

}
