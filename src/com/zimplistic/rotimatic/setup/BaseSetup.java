package com.zimplistic.rotimatic.setup;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.zimplistic.rotimatic.pageobjects.LoginPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseSetup implements Constants {

	public AndroidDriver ad;
	Dimension size;
	
	LoginPage loginpage = new LoginPage();

	/*
	 * @BeforeTest public void newReport(ITestContext ctx) { TestRunner runner =
	 * (TestRunner) ctx; String targetFolder = ROOT_DIR + TEST_FOLDER_NAME;
	 * runner.setOutputDirectory(targetFolder); }
	 */
	public void setup() throws Exception {

		// Capabilities should be same as declared in Appium settings
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "c0c05fa8");
		//capabilities.setCapability("platformName", "Android");
		capabilities.setCapability(CapabilityType.PLATFORM, "Android");
		
		capabilities.setCapability(CapabilityType.VERSION, "7.1.1");
		//capabilities.setCapability(CapabilityType.PLATFORM, "Windows");

		// application package name
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.zimplistic.rotimaticmobile");
		// Application start Activity
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.zimplistic.rotimaticmobile.activity.SplashActivity");
		
		capabilities.setCapability("noReset", true);
		//capabilities.setCapability("noReset", true);
		
		capabilities.setCapability("unicodeKeyboard", false);

		ad = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);

		ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void signIn(String username, String password) throws Exception {

		WebElement signIn = loginpage.getSignIn(ad);
		
		signIn.click();

		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtEmailAddress").sendKeys(username);

		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtPassword").sendKeys(password);

	//	ad.findElementByName("Sign In").click();
		ad.findElementByXPath("//android.widget.TextView[@text='Sign In']").click();
		System.out.println("Clicked on Sign In button");
		
		//Thread.sleep(5000);
		
		if(!ad.findElements(By.name("Rotimatic Support")).isEmpty())
			ad.findElementByName("Great!").click();

	}

	public void signOut() throws InterruptedException {

		Thread.sleep(5000);

		// hamburger menu icon
		ad.findElementByClassName("android.widget.ImageButton").click();

		// settings tab
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		// sign out option
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSignOut").click();

		// sign out alert popup
		ad.findElementById("android:id/button1").click();

		// ad.navigate().back();

	}

	// function to capture screenshots
	public void getScreenshot(AndroidDriver driver, String outputlocation) throws IOException {

		String baseFolderName = TEST_FOLDER_NAME;
		File baseFolder = new File(ROOT_DIR, baseFolderName);
		if (!baseFolder.exists() && baseFolder.isDirectory())
			baseFolder.mkdir();

		String filename = new SimpleDateFormat("d_MMM_yyyy_HH_mm_ss'.png'").format(new Date());

		File folderName = new File(baseFolder, outputlocation);
		if (!folderName.exists() && folderName.isDirectory())
			folderName.mkdir();

		// System.out.println("Capturing screenshot");
		File srcFiler = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFiler, new File(folderName, filename));
		// System.out.println("Screenshot captured and saved");

	}

	// Swiping Right

	public void swipingHorizontalRight() throws InterruptedException {

		System.out.println("Swiping right");

		Thread.sleep(5000);
		// Get the size of screen.

		size = ad.manage().window().getSize();

		// Find startx point which is at right side of screen.
		int startx = (int) (size.width * 0.85);

		// Find endx point which is at left side of screen.
		int endx = (int) (size.width * 0.10);

		// Find vertical point where you wants to swipe. It is in middle of
		// screen height.
		int starty = size.height / 4;
		// System.out.println("startx = " + startx + " ,endx = " + endx + " ,
		// starty = " + starty);

		// Swipe from Right to Left.
		ad.swipe(startx, starty, endx, starty, 2000);

		ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// Swiping Left

	public void swipingHorizontalLeft() throws InterruptedException {

		System.out.println("Swiping Left");

		Thread.sleep(5000);
		// Get the size of screen.

		size = ad.manage().window().getSize();

		// Find startx point which is at right side of screen.
		int startx = (int) (size.width * 0.10);

		// Find endx point which is at left side of screen.
		int endx = (int) (size.width * 0.85);

		// Find vertical point where you wants to swipe. It is in middle of
		// screen height.
		int starty = size.height / 4;
		// System.out.println("startx = " + startx + " ,endx = " + endx + " ,
		// starty = " + starty);

		// Swipe from Right to Left.
		ad.swipe(startx, starty, endx, starty, 2000);

		ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// Swiping UP
	public void swipingVerticallyUp() throws Exception {

		System.out.println("Swiping Up");

		Thread.sleep(5000);
		// Get the size of screen.

		size = ad.manage().window().getSize();

		// Find startx point which is at right side of screen.
		int startx = (int) (size.width * 0.40);

		int starty = size.height / 6;

		int endy = (int) (size.height / 1.5);

		//System.out.println("startx = " + startx + " ,starty = " + starty + " ,endx =" + startx + " , endy =" + endy);

		// Swipe Up from down.
		ad.swipe(startx, starty, startx, endy, 2000);

		ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// Swiping Down
	public void swipingVerticallyDown() throws Exception {

		System.out.println("Swiping Down");

		Thread.sleep(5000);
		// Get the size of screen.

		size = ad.manage().window().getSize();

		// Find startx point which is at right side of screen.
		int startx = (int) (size.width * 0.40);

		int starty = (int) (size.height / 1.5);

		int endy = (int) (size.height / 6);

		//System.out.println("startx = " + startx + " ,starty = " + starty + " ,endx =" + startx + " , endy =" + endy);

		// Swipe Down from Up.
		ad.swipe(startx, starty, startx, endy, 2000);

		ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void networkDisable() {
		// Network - Sequence(Airplane mode, Wifi, Data)

		System.out.println("Disabling Network");
//		/ad.setNetworkConnection(new NetworkConnectionSetting(false, false, false));

	}

	public void networkEnable() {
		// Network - Sequence(Airplane mode, Wifi, Data)
		System.out.println("Enabling Network");
		//ad.setNetworkConnection(new NetworkConnectionSetting(false, true, false));

	}

	/*
	 * @AfterSuite public void saveTestReport() throws Exception {
	 * 
	 * TestListenerAdapter tla = new TestListenerAdapter(); TestNG testng = new
	 * TestNG();
	 * 
	 * String testFolder = ROOT_DIR + TEST_FOLDER_NAME; File reportFolder = new
	 * File(testFolder, "report");
	 * testng.setOutputDirectory(reportFolder.getPath()); //
	 * testng.setTestClasses(new Class[] { BeginTest.class,LoginTest.class });
	 * testng.addListener(tla); testng.run();
	 * 
	 * }
	 */

	/*
	 * @AfterSuite public void saveTestReport() throws InterruptedException {
	 * 
	 * 
	 * String testFileFolder =
	 * "D:\\Cue - Projects\\Automation_Workspace\\RotimaticLogin\\test-output\\emailable-report.html"
	 * ;
	 * 
	 * String targetFolder = ROOT_DIR + TEST_FOLDER_NAME;
	 * 
	 * //System.out.println("Test report = " + testFileFolder);
	 * 
	 * //System.out.println("Test report to copy  = " + targetFolder);
	 * 
	 * File source = new File(testFileFolder);
	 * 
	 * File destination = new File(targetFolder);
	 * 
	 * //Thread.sleep(20000);
	 * 
	 * try { FileUtils.copyFileToDirectory(source, destination);
	 * 
	 * } catch (Exception e) { }
	 * 
	 * //ad.close(); }
	 */
}
