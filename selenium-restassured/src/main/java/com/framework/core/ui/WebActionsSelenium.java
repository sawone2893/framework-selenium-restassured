package com.framework.core.ui;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.framework.utils.LoggerManager;

public class WebActionsSelenium implements WebActions {

	private WebDriver driver;
	private WebElement element = null;
	private Actions action = null;

	public WebActionsSelenium(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
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
		// TODO Auto-generated method stub
		return this.findElement(locatorType, locatorValue).isDisplayed();
	}

	@Override
	public boolean isElementPresent(String locatorType, String locatorValue) {
		if (this.getElementsCount(locatorType, locatorValue) > 0 && this.isDisplayed(locatorType, locatorValue)) {
			return true;
		}
		return false;
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
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		try {
			while (!(this.isElementPresent(locatorType, locatorValue))) {
				LoggerManager.get().error("Waiting for Element {} to be appear...", locatorValue);
				this.waitForElement(1);
				endTime = System.currentTimeMillis();
				if (endTime - startTime > maxWaitTime * 1000) {
					break;
				}
			}
		} catch (Exception e) {
			status = false;
			LoggerManager.get().error("Element: {} is not appear within the specified timeout", locatorValue);
			e.printStackTrace();
			assert false;
		}
		return status;
	}

	@Override
	public boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime) {
		boolean status = true;
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		try {
			while ((this.isElementPresent(locatorType, locatorValue))) {
				LoggerManager.get().error("Waiting for Element {} to be disappear...", locatorValue);
				this.waitForElement(1);
				endTime = System.currentTimeMillis();
				if (endTime - startTime > maxWaitTime * 1000) {
					break;
				}
			}
		} catch (Exception e) {
			status = false;
			LoggerManager.get().error("Element: {} is not disappear within the specified timeout", locatorValue);
			e.printStackTrace();
			assert false;
		}
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
	public void click(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.click();
		} else {
			LoggerManager.get().error("WebElement {} is not clickable.", locatorValue);
			assert false;
		}

	}

	@Override
	public void type(String locatorType, String locatorValue, String text, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.sendKeys(text);
		} else {
			LoggerManager.get().error("WebElement {} is not enabled.", locatorValue);
			assert false;
		}

	}

	@Override
	public String getText(String locatorType, String locatorValue) {
		return this.findElement(locatorType, locatorValue).getText();
	}

	@Override
	public void hoverAndClick(String locatorType, String srcLocator, String targetLocator, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, srcLocator, maxWaitTime)) {
			action.moveToElement(this.findElement("XPATH", srcLocator))
					.moveToElement(this.findElement("XPATH", targetLocator)).click().build();
		} else {
			LoggerManager.get().error("WebElement {} is not enabled.", srcLocator);
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
		// TODO Auto-generated method stub
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

}
