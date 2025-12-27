package com.framework.core.driverFactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverManager implements BrowserDriver {
	@Override
	public WebDriver createDriver(boolean isHeadless) {

		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.setAcceptInsecureCerts(true);
		edgeOptions.addArguments("--incognito");
		edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		edgeOptions.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
		if (isHeadless) {
			edgeOptions.addArguments("--headless");
		}
		return new EdgeDriver(edgeOptions);
	}

}
