package com.zimplistic.rotimatic.testcases;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.pageobjects.LoginPage;
import com.zimplistic.rotimatic.setup.BaseSetupManager;
import com.zimplistic.rotimatic.setup.Constants;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.asserts.*;

public class parallelPOC implements Constants {

	private AndroidDriver<MobileElement> ad;
	LoginPage loginpage = new LoginPage();
	WebElement signIn, email, passkey, login, hammenu;

	// @BeforeSuite
	// public void startServer() throws Exception {
	// // START APPIUM SERVER
	// System.out.println("Starting appium server");
	// Runtime.getRuntime().exec("ssh -t http://172.21.32.70 \"appium &\"");
	// Thread.sleep(20000);
	//
	// }

	@BeforeTest(alwaysRun = true)
	@Parameters({ "platform", "deviceNAME", "systemPort" })
	public void setup(String platform, String deviceNAME, String systemPort) {
		try {

			String[] platformInfo = platform.split(" ");

			// first time declaration
			// Capabilities should be same as declared in Appium settings
			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");

			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android test");

			capabilities.setCapability(MobileCapabilityType.UDID, deviceNAME);

			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformInfo[0]);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformInfo[1]);
			capabilities.setCapability("systemPort", systemPort);

			// Make apk path public accessible
			capabilities.setCapability(MobileCapabilityType.APP, "D:\\Android\\Sdk\\build-tools\\rc-4.6.apk");
			// "/Users/shantanuwagholikar/Documents/shantanu_wagholikar/reskin-1.4.apk");
			// local D:\\Android\\Sdk\\build-tools\\reskin-1.4.apk
			// for remote machine
			// capabilities.setCapability(CapabilityType.PLATFORM, "Windows");

			// application package name
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.zimplistic.rotimaticmobile");

			capabilities.setCapability("noReset", true);
			// capabilities.setCapability("noReset", true);

			capabilities.setCapability("unicodeKeyboard", false);

			// Application start Activity
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
					"com.zimplistic.rotimaticmobile.activity.SplashActivity");

			try {
				ad = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities);

				// ad = new AndroidDriver<MobileElement>(new URL(
				// "http://shantanuw.cuelogic:9ef959b6-0551-42d1-83ee-a1a54adba778@ondemand.saucelabs.com/wd/hub"
				// ), capabilities);

				// ad = new AndroidDriver<MobileElement>(new
				// URL("https://eu1.appium.testobject.com/wd/hub"),capabilities);

			} catch (Exception e) {

				e.printStackTrace();
			}
			System.out.println("Remote driver connected");

			ad.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail("Appium server setup failed");
		}
	}

	// function to capture screenshots
	public void getScreenshot(AndroidDriver<MobileElement> driver, String outputlocation) throws IOException {

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

	public void unistallApps() {
		// String cmd = "adb shell getprop ro.build.version.release";
		String cmd;
		// String osVersion = executeCommand(cmd);
		// System.out.println("Current device os version is :- " + osVersion);

		// if (osVersion.contains("7") || osVersion.contains("8")) {
		// uninstall io.appium.settings
		cmd = "adb uninstall io.appium.settings";
		executeCommand(cmd);
		System.out.println("settings unistalled");

		// uninstall io.appium.unlock
		cmd = "adb uninstall io.appium.unlock";
		executeCommand(cmd);
		System.out.println("unlock unistalled");

		// }

	}

	public String executeCommand(String cmd) {
		String commandresponse = "";
		try {
			Runtime run = Runtime.getRuntime();
			Process proc = run.exec(cmd);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String response = null;
			while ((response = stdInput.readLine()) != null) {
				if (response.length() > 0) {
					commandresponse = commandresponse + response;
				}
			}

			while ((response = stdError.readLine()) != null) {
				commandresponse = commandresponse + response;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return commandresponse;
	}

	@Test(priority = 0)
	public void loginCheck() throws Exception {
		// unlock device
		// Runtime.getRuntime().exec("adb shell am start -n io.appium.unlock/.Unlock");

		System.out.println("Invalid Login test Started");

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/ivSignIn").isEmpty()) {
			signIn("qa@android.com", "12345");
			Thread.sleep(5000);
			getScreenshot(ad, Constants.FOLDER_LOGIN);
			ad.findElementById("android:id/button1").click();
			ad.navigate().back();
		} else {

			// check whether user is associated or not

			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
				System.out.println("User is not associated to any Rotimatic");

				System.out.println("Skipping connection");
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();
				getScreenshot(ad, Constants.FOLDER_LOGIN);

				// Skip setup link
				if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
					System.out.println("Skipping setup");
					ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();

					// Yes
					ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

					// Got It
					ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

					Thread.sleep(2000);

				}

			}

			System.out.println("User is logged in to app");
		}
	}

	@Parameters({ "username", "password" })
	@Test(priority = 1)
	public void testLogin(String username, String password) throws Exception {

		System.out.println("Login test Started");

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/ivSignIn").isEmpty()) {
			signIn(username, password);

			getScreenshot(ad, Constants.FOLDER_LOGIN);
		} else if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
			// check whether user is associated or not
			System.out.println("User is not associated to any Rotimatic");

			System.out.println("Skipping connection");
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();
			getScreenshot(ad, Constants.FOLDER_LOGIN);

			// Skip setup link
			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
				System.out.println("Skipping setup");
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();

				// Yes
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

				// Got It
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

				Thread.sleep(2000);

			}

		} else if (ad.findElementById("com.zimplistic.rotimaticmobile:id/toolbar_title").getText().contains("Home"))
			System.out.println("User is logged in to app");
	}

	public void signIn(String username, String password) throws Exception {

		signIn = loginpage.getSignIn(ad);

		signIn.click();

		Thread.sleep(2000);
		email = loginpage.getUsername(ad);

		email.sendKeys(username);

		passkey = loginpage.getPassword(ad);
		passkey.sendKeys(password);

		login = loginpage.getLoginButton(ad);
		login.click();
		System.out.println("Clicked on Sign In button");
		Thread.sleep(12000);

	}

	public void signOut() throws InterruptedException {

		System.out.println("Sign out test started");
		Thread.sleep(3000);

		// hamburger menu icon
		ad.findElementByClassName("android.widget.ImageButton").click();

		// ad.navigate().back();
		// ad.findElementByClassName("android.widget.ImageButton").click();
		// settings tab
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		// sign out option
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSignOut").click();

		// sign out alert popup
		ad.findElementById("android:id/button1").click();
		Thread.sleep(2000);
		ad.navigate().back();

	}

	@Test(priority = 2)
	private void settingsScreen() throws Exception {

		System.out.println("Inside settings screen");
		Thread.sleep(5000);
		ad.findElementByClassName("android.widget.ImageButton").click();
		ad.navigate().back();

		ad.findElementByClassName("android.widget.ImageButton").click();

		// settings tab
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		String username = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserName").getText();
		System.out.println("Welcome - " + username);

		String emailID = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserEmail").getText();
		System.out.println("Email ID - " + emailID);
		getScreenshot(ad, Constants.FOLDER_LOGIN);

		String serialNumber = "N/A";
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").isEmpty())
			serialNumber = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();
		System.out.println("Serial Number - " + serialNumber);

		String machineName = "N/A";
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvMachineName").isEmpty())
			machineName = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvMachineName").getText();
		System.out.println("Machine name - " + machineName);

		ad.navigate().back();
	}

	@AfterTest(alwaysRun = true)
	public void teardown() throws Exception {

		signOut();
		// Unistalling apks on OS 7
		// unistallApps();

		System.out.println("End of test suite");

		// uninstall app
		Runtime.getRuntime().exec("adb uninstall com.zimplistic.rotimaticmobile"); 
		// ad.close();
	}

}
