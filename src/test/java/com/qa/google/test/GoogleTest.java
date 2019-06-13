package com.qa.google.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.google.base.TestBase;

public class GoogleTest extends TestBase {

	@Test
	public void getTitle(){
		_report = _startReport.startTest("GoogleTest");
		String title = driver.getTitle();
		System.out.println("Title is : "+title);
		Assert.assertEquals(title, "Google");
		
	}
}
