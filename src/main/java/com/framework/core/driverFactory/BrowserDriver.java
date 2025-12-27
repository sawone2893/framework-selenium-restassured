package com.framework.core.driverFactory;

import org.openqa.selenium.WebDriver;

public interface BrowserDriver {
	WebDriver createDriver(boolean isHeadless);
}
