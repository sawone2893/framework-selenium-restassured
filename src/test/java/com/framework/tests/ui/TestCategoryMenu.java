package com.framework.tests.ui;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.pages.HomePage;

public class TestCategoryMenu extends BaseTest {
	//https://www.youtube.com/watch?v=8YxJv4l-LOo&list=PLFGoYjJG_fqpc4jcqbeXqMIT3-60nfLr6&index=5
	//@Test(retryAnalyzer=RetryAnalyzer.class)
	@Test
	public void testCategoryMenu() {
		HomePage hp = new HomePage();
		hp.selectItemFromCategoryMenu("Makeup", "Cheeks1");
		Assert.assertEquals(hp.getCurrentPageTitle(), "Cheeks", "Page title mismatch");
	}

}
