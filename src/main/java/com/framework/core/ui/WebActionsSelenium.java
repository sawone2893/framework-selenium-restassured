package com.framework.core.ui;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.utils.LoggerManager;

public class WebActionsSelenium implements WebActions {

	private WebDriver driver = null;
	private WebElement element = null;
	private Actions action = null;
	private JavascriptExecutor js = null;
	private int maxWaitTime = 0;

	public WebActionsSelenium(WebDriver driver, int maxWaitTime) {
		this.driver = driver;
		this.maxWaitTime = maxWaitTime;
	}

	@Override
	public WebElement findElement(String locatorType, String locatorValue) {
		LoggerManager.get().info("Find element: " + locatorValue);
		switch (locatorType.toUpperCase()) {
		case "ID":
			return driver.findElement(By.id(locatorValue));
		case "XPATH":
			return driver.findElement(By.xpath(locatorValue));
		case "LINKTEXT":
			return driver.findElement(By.linkText(locatorValue));
		case "CSS":
			return driver.findElement(By.cssSelector(locatorValue));
		case "NAME":
			return driver.findElement(By.name(locatorValue));
		case "TAG":
			return driver.findElement(By.tagName(locatorValue));
		case "PARTIALLINKTEXT":
			return driver.findElement(By.partialLinkText(locatorValue));
		default:
			return driver.findElement(By.className(locatorValue)); // Fallback to class name
		}
	}

	@Override
	public List<WebElement> findElements(String locatorType, String locatorValue) {
		switch (locatorType.toUpperCase()) {
		case "ID":
			return driver.findElements(By.id(locatorValue));
		case "XPATH":
			return driver.findElements(By.xpath(locatorValue));
		case "LINKTEXT":
			return driver.findElements(By.linkText(locatorValue));
		case "CSS":
			return driver.findElements(By.cssSelector(locatorValue));
		case "NAME":
			return driver.findElements(By.name(locatorValue));
		case "TAG":
			return driver.findElements(By.tagName(locatorValue));
		case "PARTIALLINKTEXT":
			return driver.findElements(By.partialLinkText(locatorValue));
		default:
			return driver.findElements(By.className(locatorValue)); // Fallback to class name
		}
	}

	@Override
	public int getElementsCount(String locatorType, String locatorValue) {
		return findElements(locatorType, locatorValue).size();
	}

	@Override
	public boolean isDisplayed(String locatorType, String locatorValue) {
		return this.findElement(locatorType, locatorValue).isDisplayed();
	}

	@Override
	public boolean isElementPresent(String locatorType, String locatorValue) {
		if (this.getElementsCount(locatorType, locatorValue) > 0 && this.isDisplayed(locatorType, locatorValue)) {
			return true;
		}
		return false;
	}

	/**
	 * Sets a fluent wait for a specified condition to be met.
	 *
	 * @param function    the condition to wait for
	 * @param maxWaitTime the maximum time to wait
	 */
	public void setFluentWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		Wait<WebDriver> wait = new FluentWait<>(driver).pollingEvery(Duration.ofMillis(500))
				.withTimeout(Duration.ofSeconds(maxWaitTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		wait.until(function);
	}

	/**
	 * Sets a WebDriver wait for a specified condition to be met.
	 *
	 * @param function    the condition to wait for
	 * @param maxWaitTime the maximum time to wait
	 */
	public void setWebDriverWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime));
		wait.until(function);
	}

	/**
	 * Waits for the page to load completely within the specified time.
	 *
	 * @param time the maximum time to wait for the page to load
	 */
	@Override
	public void waitForPageLoad(int time) {
		Function<WebDriver, Boolean> function = wDriver -> {
			String readyState = (String) this.executeJSAction("return document.readyState");
			LoggerManager.get().info("Current Window State:{}", readyState);
			return "complete".equals(readyState);
		};
		setWebDriverWait(function, time);
	}

	@Override
	public void waitForElement(int seconds) {
		int time = seconds * 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			/* Clean up whatever needs to be handled before interrupting */
			Thread.currentThread().interrupt();
			e.printStackTrace();
			assert false;
		}
	}

	@Override
	public boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime) {
		boolean status = true;

		try {
			Function<WebDriver, Boolean> function = wDriver -> {
				LoggerManager.get().info("Waiting for Element {} to be appear...", locatorValue);
				return this.isElementPresent(locatorType, locatorValue);
			};
			this.setFluentWait(function, maxWaitTime);
		} catch (TimeoutException e) {
			status = false;
		}

		/*
		 * long startTime; long endTime; startTime = System.currentTimeMillis(); try {
		 * while (!(this.isElementPresent(locatorType, locatorValue))) {
		 * LoggerManager.get().error("Waiting for Element {} to be appear...",
		 * locatorValue); this.waitForElement(1); endTime = System.currentTimeMillis();
		 * if (endTime - startTime > maxWaitTime * 1000) { break; } } } catch (Exception
		 * e) { status = false; LoggerManager.get().
		 * error("Element: {} is not appear within the specified time", locatorValue);
		 * e.printStackTrace(); assert false; }
		 */
		return status;
	}

	@Override
	public boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime) {
		boolean status = true;

		try {
			Function<WebDriver, Boolean> function = wDriver -> {
				LoggerManager.get().info("Waiting for Element {} to be disappear...", locatorValue);
				return !this.isElementPresent(locatorType, locatorValue);
			};
			this.setFluentWait(function, maxWaitTime);
		} catch (TimeoutException e) {
			status = false;
		}
		/*
		 * long startTime; long endTime; startTime = System.currentTimeMillis(); try {
		 * while ((this.isElementPresent(locatorType, locatorValue))) {
		 * LoggerManager.get().error("Waiting for Element {} to be disappear...",
		 * locatorValue); this.waitForElement(1); endTime = System.currentTimeMillis();
		 * if (endTime - startTime > maxWaitTime * 1000) { break; } } } catch (Exception
		 * e) { status = false; LoggerManager.get().
		 * error("Element: {} is not disappear within the specified time",
		 * locatorValue); e.printStackTrace(); assert false; }
		 */
		return status;
	}

	@Override
	public void launchBrowser(String url) {
		driver.get(url);

	}

	@Override
	public void maximiumBrowserWindow() {
		driver.manage().window().maximize();

	}

	@Override
	public void highlight(String locatorType, String locatorValue) {

		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			js = (JavascriptExecutor) driver;
			element = findElement(locatorType, locatorValue);
			js.executeScript("arguments[0].style.border='3px solid red'", element);
		} else {
			LoggerManager.get()
					.error("ELEMENT_IS_NOT_PRESENT_IN_DOM: Unable to highlight WebElement:[" + locatorValue + "]");
			assert false;
		}

	}

	@Override
	public void click(String locatorType, String locatorValue) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.click();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable to click.", locatorValue);
			assert false;
		}

	}

	@Override
	public void type(String locatorType, String locatorValue, String text) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.sendKeys(text);
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable to type text", locatorValue);
			assert false;
		}

	}

	@Override
	public String getText(String locatorType, String locatorValue) {
		String text = null;
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			text = element.getText();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable to fetch text", locatorValue);
			assert false;
		}
		return text;
	}

	@Override
	public void hover(String locatorType, String locator) {
		action = new Actions(driver);
		if (this.waitUntillElementAppear(locatorType, locator, maxWaitTime)) {
			action.moveToElement(this.findElement(locatorType, locator)).perform();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM: Unable to perform hover", locator);
			assert false;
		}

	}

	@Override
	public void settingImplicitWait(long timeInSeconds) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeInSeconds));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	@Override
	public void captureScreenshot(String snapLocation) {
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(snapLocation));
		} catch (Exception e) {
			LoggerManager.get().error("Screenshot failed", e);
		}

	}

	@Override
	public void doubleClick(String locatorType, String locator) {
		action = new Actions(driver);
		if (this.waitUntillElementAppear(locatorType, locator, maxWaitTime)) {
			action.doubleClick(this.findElement(locatorType, locator)).perform();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable to perform double click", locator);
			assert false;
		}

	}

	@Override
	public void rightClick(String locatorType, String locator) {
		action = new Actions(driver);
		if (this.waitUntillElementAppear(locatorType, locator, maxWaitTime)) {
			action.contextClick(this.findElement(locatorType, locator)).perform();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable perform right click", locator);
			assert false;
		}

	}

	@Override
	public void controlClick(String locatorType, String locator) {
		action = new Actions(driver);
		if (this.waitUntillElementAppear(locatorType, locator, maxWaitTime)) {
			action.keyDown(Keys.CONTROL).click(this.findElement(locatorType, locator)).keyUp(Keys.CONTROL).build()
					.perform();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable to perform control click", locator);
			assert false;
		}

	}

	@Override
	public void dragAndDrop(String locatorType, String srcLocator, String targetLocator) {
		action = new Actions(driver);
		if (this.waitUntillElementAppear(locatorType, srcLocator, maxWaitTime)) {
			action.dragAndDrop(this.findElement(locatorType, srcLocator), this.findElement(locatorType, targetLocator))
					.perform();
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable perform drag and drop", srcLocator);
			assert false;
		}

	}

	@Override
	public Object executeJSAction(String script, Object... args) {
		js = (JavascriptExecutor) driver;
		return js.executeScript(script, args);

	}

	@Override
	public void scrollVertically() {
		this.executeJSAction("window.scrollTo(0,document.body.scrollHeight);");
	}

	@Override
	public void scrollHorizontally() {
		this.executeJSAction("window.scrollTo(document.body.scrollWidth,0);");

	}

	@Override
	public void scrollIntoView(String locatorType, String locator) {

		if (this.waitUntillElementAppear(locatorType, locator, maxWaitTime)) {
			this.executeJSAction("arguements[0].scrollIntoView(true);", this.findElement(locatorType, locator));
		} else {
			LoggerManager.get().error("ELEMENT_IS_NOT_PRESENT_IN_DOM:Unable to perform scroll into view", locator);
			assert false;
		}

	}

	@Override
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	@Override
	public void dismissAlert() {
		driver.switchTo().alert().dismiss();
	}

	@Override
	public void typeInAlert(String text) {
		driver.switchTo().alert().sendKeys(text);

	}

	@Override
	public String getAlertText() {
		return driver.switchTo().alert().getText();
	}

	@Override
	public void switchIframe(String name) {
		driver.switchTo().frame(name);
	}

	@Override
	public void switchIframe(int index) {
		driver.switchTo().frame(index);
	}

	@Override
	public void switchIframe(String locatorType, String locator) {
		driver.switchTo().frame(this.findElement(locatorType, locator));
	}

}
