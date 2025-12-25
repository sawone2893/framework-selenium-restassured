package com.framework.core.driverFactory;

public class DriverFactory {

	public static BrowserDriver getDriverInstance(String browserType) {
		switch (browserType.toUpperCase()) {
		case "CHROME":
			return new ChromeDriverManager();
		case "FIREFOX":
			return new FireFoxDriverManager();
		case "EDGE":
			return new EdgeDriverManager();
		default:
			throw new IllegalArgumentException("Invalid Browser Type: " + browserType);
		}
	}

}
