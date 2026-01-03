package com.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.framework.utils.LoggerManager;

public class RetryAnalyzer implements IRetryAnalyzer{
	
	private int retryCount=0;
	private static int maxRetryCount=0;//Integer.parseInt(System.getProperty("maxRetryCount"));
	
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount<maxRetryCount) {
			retryCount++;
			LoggerManager.get().info("⚠️ Retrying test " + result.getName() + " for the " + retryCount + " time.");
			return true;
		}
		return false;
	}

}
