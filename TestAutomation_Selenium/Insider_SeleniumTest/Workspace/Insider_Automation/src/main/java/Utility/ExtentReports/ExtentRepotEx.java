package Utility.ExtentReports;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import Utility.configuration.ConfigClass;
import Utility.configuration.SendEmailConfiguration;

public class ExtentRepotEx extends ConfigClass {

	public static ExtentReports extent;
	public static ExtentTest test;
	public static String timestamp = new SimpleDateFormat("MM-dd-yyyy_HH:mm").format(Calendar.getInstance().getTime())
			.replaceAll(":", "-");

	public void makedir() {
		File dir = new File(properties.getProperty("reportPath"));
		String foldername = timestamp;
		String finalPath = properties.getProperty("reportPath") + foldername;
		File f1 = new File(finalPath);
		System.out.println(f1.mkdir());
	}

	@BeforeSuite
	public void beforesuitreport() {

		ConfigClass.ConfigFileReader();
		String foldername = properties.getProperty("reportPath") + "\\Report_" + timestamp;
		File f1 = new File(foldername);
		String newfolder = Boolean.toString(f1.mkdir());

		extent = new ExtentReports(properties.getProperty("reportPath") + "\\Report_" + timestamp + "\\Insider_Test_"
				+ timestamp + ".html", false);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		extent.addSystemInfo("Application Version", "Insider Web 1.0");
	}

	@BeforeMethod
	public void reportconfiguration(Method method) {
		test = extent.startTest(method.getName());
	}

	@AfterMethod
	public void endTest(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		extent.endTest(test);
		extent.flush();
	}

	@AfterSuite
	public void teanDown() throws Exception {
		extent.close();
		SendEmailConfiguration sec = new SendEmailConfiguration();
	 // sec.sendEmail();
	}
}
