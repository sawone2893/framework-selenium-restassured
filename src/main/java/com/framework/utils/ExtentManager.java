package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports createInstance() {
        if (extent == null) {
            // 1. Create the reporter (HTML file location)
            String fileName = "test-output/reports/SmartReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);

            // 2. Configure the design
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Regression Suite Results");

            // 3. Attach reporter to ExtentReports engine
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // 4. Add System Info (Good for debugging)
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }
}
