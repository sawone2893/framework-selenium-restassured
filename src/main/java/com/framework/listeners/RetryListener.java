package com.framework.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
	    // Print to console to prove this method is called
	    System.out.println("Transforming test: " + testMethod.getName()); 
	    
	    annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

}
