package com.zimplistic.rotimatic.setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseSetupManager implements Constants {

	private static final String TAG = BaseSetupManager.class.getSimpleName();
	private static BaseSetupManager manager;
	private AndroidDriver<MobileElement> ad;

	private BaseSetupManager(String platform, String deviceNAME, String systemPort) {
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

			// application package name
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.zimplistic.rotimaticmobile");

			capabilities.setCapability("noReset", true);

			capabilities.setCapability("unicodeKeyboard", false);

			// Application start Activity
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
					"com.zimplistic.rotimaticmobile.activity.SplashActivity");

			try {
				ad = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities);

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

	public static BaseSetupManager getInstance(String platform, String deviceNAME, String systemPort) {
		if (null == manager) {
			manager = new BaseSetupManager(platform, deviceNAME, systemPort);
		}
		return manager;
	}

	public AndroidDriver<MobileElement> getAndroidDriver() {
		return ad;
	}

	// function to capture screenshots
	public static void getScreenshot(AndroidDriver<MobileElement> driver, String outputlocation) throws IOException {

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

	public static void unistallApps() {
		String cmd = "adb shell getprop ro.build.version.release";

		String osVersion = executeCommand(cmd);
		System.out.println("Current device os version is :- " + osVersion);

		if (osVersion.contains("7") || osVersion.contains("8")) {
			// uninstall io.appium.settings
			cmd = "adb uninstall io.appium.settings";
			executeCommand(cmd);
			System.out.println("settings unistalled");

			// uninstall io.appium.unlock
			cmd = "adb uninstall io.appium.unlock";
			executeCommand(cmd);
			System.out.println("unlock unistalled");

		}

	}

	public static String executeCommand(String cmd) {
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
		// System.out.println(commandresponse);
		return commandresponse;
	}

}
