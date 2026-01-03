package com.framework.listeners;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.framework.base.BaseClass;
import com.framework.utils.ExtentManager;

public class TestListener implements ITestListener {

	// 1. Initialize the ExtentReports engine
	private static ExtentReports extent = ExtentManager.createInstance();

	// 2. ThreadLocal storage for the test (The "Smart" part for Parallel execution)
	private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

	// 1. History Keeper: Stores logs for retried tests (Thread-Safe)
	// Key = Test Name, Value = List of failure messages
	private static ConcurrentHashMap<String, List<String>> retryHistory = new ConcurrentHashMap<>();

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String fullTestName = result.getTestClass().getName() + "." + testName;

		// Create a new entry in the report for this specific test case
		ExtentTest test = extent.createTest(testName);
		// Save this test object into the ThreadLocal storage
		testNode.set(test);

		// Check if this is a RETRY execution (Count > 0)
		/*
		 * int currentAttempt = result.getMethod().getCurrentInvocationCount(); if
		 * (currentAttempt > 1) { testNode.get().log(Status.INFO,
		 * "⚠️ This is Retry Attempt #" + (currentAttempt - 1)); }
		 */
		// 2. CHECK HISTORY: Does this test have previous failures?
		if (retryHistory.containsKey(fullTestName)) {
			List<String> previousErrors = retryHistory.get(fullTestName);

			for (String error : previousErrors) {
				testNode.get().log(Status.WARNING, "⚠️ PREVIOUS ATTEMPT FAILED: " + error);
			}
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// Log success
		testNode.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// 1. Log the error text
		testNode.get().fail(result.getThrowable());

		// 2. Capture and Attach Screenshot
		try {
			// Convert screenshot to Base64 string (Efficient for HTML reports)
			String base64Screenshot = BaseClass.getBase64Snap();

			// Attach to report
			testNode.get().addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot");

		} catch (Exception e) {
			testNode.get().info("Failed to capture screenshot: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// Check if the skip was caused by the RetryAnalyzer
		if (result.wasRetried()) {
			// Log it as a warning/info instead of a skip, so we know it was a flake
			// testNode.get().log(Status.WARNING, "⚠️ Test Failed but is being RETRIED.
			// Previous attempt failed.");
			// Optional: Remove this "Skipped" node from the final report stats if you want
			// a clean "Pass/Fail" only report.
			// (ExtentReports doesn't easily support deleting nodes, so marking as WARNING
			// is the best practice).
			// Identify the test uniquely
            String fullTestName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
            
            // 3. CAPTURE FAILURE: Save the error before deleting the node
            String failureMessage = "Unknown error";
            if (result.getThrowable() != null) {
                failureMessage = result.getThrowable().getMessage();
            }

            // Add to history (Initialize list if it's the first retry)
            retryHistory.computeIfAbsent(fullTestName, k -> new CopyOnWriteArrayList<>()).add(failureMessage);

            // 4. DELETE NODE: Remove the "Skipped" entry from the report
            extent.removeTest(testNode.get());
            
            System.out.println("♻️ Retrying " + fullTestName + ". History saved, report entry removed.");

		} else {
			// Actual skipped test (e.g., due to dependency failure)
			testNode.get().log(Status.SKIP, "Test Skipped");
			// Optional: Log the reason
			if (result.getThrowable() != null) {
				testNode.get().log(Status.SKIP, result.getThrowable());
			}
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		// Write everything to the HTML file
		if (extent != null) {
			extent.flush();
		}
	}
}
