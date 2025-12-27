package com.framework.pages;

import com.framework.base.BaseClass;
import com.framework.utils.LocatorGenerator;

public class HomePage extends BaseClass {

	private String NAV_BAR_LINK = "//ul[@id='main_menu_top']/li/a/span[text()='#0#']";
	private String CATEGORY_MENU = "//ul[contains(@class,'categorymenu')]/li/a[contains(text(), '#0#')]";
	private String LINK = "//a[text()='#0#']";

	public void clickLoginRegisterLink() {
		clickElement("XPATH", LocatorGenerator.generateLocator(this.LINK, "Login or register"));
	}

	public void clickNavBarLinks(String linkName) {
		clickElement("XPATH", LocatorGenerator.generateLocator(this.NAV_BAR_LINK, linkName));
	}

	public void selectItemFromCategoryMenu(String categoryName, String itemName) {
		hoverAndClickElement("XPATH",LocatorGenerator.generateLocator(this.CATEGORY_MENU, categoryName),
				LocatorGenerator.generateLocator(this.LINK, itemName));
	}

	public String getCurrentPageTitle() {
		return getPageTitle();

	}

}
