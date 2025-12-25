package com.framework.core.driverFactory;

import org.openqa.selenium.WebDriver;

public class DriverManager {
	private static volatile DriverManager dManager;
	private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	private DriverManager() {
	}

	private void initDriver(String browserType, boolean isHeadless) {
		BrowserDriver bDriver = DriverFactory.getDriverInstance(browserType);
		threadLocalDriver.set(bDriver.createDriver(isHeadless));
	}

	public static DriverManager getInstance(String browserType, boolean isHeadless) {
		if (dManager == null) {
			synchronized (DriverManager.class) {
				if (dManager == null) {
					dManager = new DriverManager();
				}
			}
		}
		if (threadLocalDriver.get() == null) {
			dManager.initDriver(browserType, isHeadless);
		}
		return dManager;
	}

	public WebDriver getDriver() {
		return threadLocalDriver.get();
	}

	public static void quitDriver() {
		if (threadLocalDriver.get() != null) {
			threadLocalDriver.get().quit();
			threadLocalDriver.remove();
		}
	}
}
