package com.qa.google.reports;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	public static ExtentReports extent;

	//getting instance for the reports
	public static ExtentReports getInstance(){
		String curDir = System.getProperty("user.dir");
		String _reportFilePath = curDir + "/reports/ExtentReport.html";
		if(extent==null)
			extent = new ExtentReports(_reportFilePath);
		return extent;
	}

	

}
