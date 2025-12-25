package com.framework.core.driverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FireFoxDriverManager implements BrowserDriver{
	
	@Override
	public WebDriver createDriver(boolean isHeadless) {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setAcceptInsecureCerts(true);
		firefoxOptions.addArguments("--incognito");
		firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		if (isHeadless) {
			firefoxOptions.addArguments("-headless");
		}
		return new FirefoxDriver(firefoxOptions);
	}

}
