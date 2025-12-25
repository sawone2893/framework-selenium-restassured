package com.framework.core.driverFactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager implements BrowserDriver {

	@Override
	public WebDriver createDriver(boolean isHeadless) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setAcceptInsecureCerts(true);
		chromeOptions.addArguments("--incognito");
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		chromeOptions.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
		if (isHeadless) {
			chromeOptions.addArguments("--headless=new");
		}
		return new ChromeDriver(chromeOptions);
	}

}
