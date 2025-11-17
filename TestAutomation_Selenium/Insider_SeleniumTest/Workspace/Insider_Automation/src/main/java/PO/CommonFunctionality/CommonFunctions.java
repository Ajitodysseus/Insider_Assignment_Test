package PO.CommonFunctionality;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.LogStatus;
import Utility.ExtentReports.ExtentRepotEx;

//Created By Ajit Nakum (Sr Quality Engineer @Allscripts)

public class CommonFunctions extends ExtentRepotEx {

	public static Properties properties1;
	public static String workingDir = System.getProperty("user.dir");
	public WebDriverWait wait;

	public void TestDataReader(String Filename) {
		BufferedReader reader1;
		try {
			String propertyFilePath = workingDir + "\\src\\main\\java\\TestData\\" + Filename;
			System.out.println("file  " + propertyFilePath);
			reader1 = new BufferedReader(new FileReader(propertyFilePath));
			properties1 = new Properties();
			try {
				properties1.load(reader1);
				reader1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	public void Info(String detail) {
		test.log(LogStatus.INFO, detail);
	}

	public void pass(String detail) {
		test.log(LogStatus.PASS, detail);
	}

	public void fail(String detail) {
		test.log(LogStatus.FAIL, detail);
	}

	public void skip(String detail) {
		test.log(LogStatus.SKIP, detail);
	}

	public void TestStepStatus(String step, boolean sucess) throws InterruptedException {
		if (sucess) {
			test.log(LogStatus.PASS, step);
		} else {
			test.log(LogStatus.FAIL, step + test.addScreenCapture(ScreenshotCapture()));
		}
	}

	public String ScreenshotCapture() {
		try {
			Thread.sleep(120);
			Robot robot = new Robot();

			// for full image capture
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			String path = properties.getProperty("reportPath") + "\\Report_" + ExtentRepotEx.timestamp + "\\Screenshot_"
					+ System.currentTimeMillis() + "." + "jpg";
			File output = new File(properties.getProperty("reportPath") + "\\Report_" + ExtentRepotEx.timestamp
					+ "\\Screenshot_" + System.currentTimeMillis() + "." + "jpg");
			ImageIO.write(screenFullImage, "jpg", output);
			Thread.sleep(2000);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
