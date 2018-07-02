package com.zimplistic.rotimatic.testcases;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.Version;
import com.ssts.pcloudy.appium.PCloudyAppiumSession;
import com.ssts.pcloudy.dto.appium.booking.BookingDtoDevice;
import com.ssts.pcloudy.dto.device.MobileDevice;
import com.ssts.pcloudy.dto.file.PDriveFileDTO;
import com.ssts.pcloudy.exception.ConnectError;
import com.zimplistic.rotimatic.pageobjects.LoginPage;
import com.zimplistic.rotimatic.setup.Constants;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class SamplePCloudyTestAndroid {
	AndroidDriver<MobileElement> driver;
	int deviceBookDuration = 5;
	PCloudyAppiumSession pCloudySession;
	Boolean autoSelectDevices = true;
	LoginPage loginpage = new LoginPage();
	WebElement signIn, email, passkey, login, hammenu;

	@BeforeTest
	public void runExecutionOnPCloudy() throws Exception {

		Connector pCloudyCONNECTOR = new Connector("https://device.pcloudy.com/api/");

		// User Authentication over pCloudy
		String authToken = pCloudyCONNECTOR.authenticateUser("shantanu.wagholikar@cuelogic.co.in",
				"7cfp53zy3jw9w5sym3jzjhcz");

		// Select apk in pCloudy Cloud Drive
		File fileToBeUplodrivered = new File("D:\\Android\\Sdk\\build-tools\\reskin-1.4.apk");
		PDriveFileDTO alredriveryUplodriveredApp = pCloudyCONNECTOR.getAvailableAppIfUploaded(authToken,
				fileToBeUplodrivered.getName());
		if (alredriveryUplodriveredApp == null) {
			System.out.println("Uploading App: " + fileToBeUplodrivered.getAbsolutePath());
			PDriveFileDTO uplodriveredApp = pCloudyCONNECTOR.uploadApp(authToken, fileToBeUplodrivered, false);
			System.out.println("App uploaded");
			alredriveryUplodriveredApp = new PDriveFileDTO();
			alredriveryUplodriveredApp.file = uplodriveredApp.file;
		} else {
			System.out.println("App already present. Not uploading... ");
		}

		ArrayList<MobileDevice> selectedDevices = new ArrayList<>();
		if (autoSelectDevices)
			selectedDevices.addAll(pCloudyCONNECTOR.chooseDevices(authToken, "android", new Version("6.*.*"),
					new Version("8.*.*"), 1));
		else
			selectedDevices.add(pCloudyCONNECTOR.chooseSingleDevice(authToken, "android"));

		// Book the selected devices in pCloudy
		String sessionName = "Appium Session " + new Date();
		BookingDtoDevice bookedDevice = pCloudyCONNECTOR.AppiumApis().bookDevicesForAppium(authToken, selectedDevices,
				deviceBookDuration, sessionName)[0];
		System.out.println("Devices booked successfully");

		pCloudyCONNECTOR.AppiumApis().initAppiumHubForApp(authToken, alredriveryUplodriveredApp);

		pCloudySession = new PCloudyAppiumSession(pCloudyCONNECTOR, authToken, bookedDevice);
	}

	@BeforeMethod
	public void prepareTest() throws IOException, ConnectError, InterruptedException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("newCommandTimeout", 600);
		capabilities.setCapability("launchTimeout", 90000);
		capabilities.setCapability("deviceName", pCloudySession.getDto().capabilities.deviceName);
		capabilities.setCapability("browserName", pCloudySession.getDto().capabilities.deviceName);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", "com.zimplistic.rotimaticmobile");
		capabilities.setCapability("appActivity", "com.zimplistic.rotimaticmobile.activity.SplashActivity");
		capabilities.setCapability("rotatable", false);

		URL appiumEndpoint = pCloudySession.getConnector().AppiumApis()
				.getAppiumEndpoint(pCloudySession.getAuthToken());
		driver = new AndroidDriver<MobileElement>(appiumEndpoint, capabilities);
	}

	@Test(priority = 0)
	public void testLogin() throws Exception {

		System.out.println("Waiting for device to clean up");
		Thread.sleep(20000);
		System.out.println("Login test Started");
		signIn("qa@android.com", "asdf");
		System.out.println("On home screen");
		signOut();

	}

	public void signIn(String username, String password) throws Exception {

		signIn = loginpage.getSignIn(driver);

		signIn.click();

		Thread.sleep(2000);
		email = loginpage.getUsername(driver);

		email.sendKeys(username);

		passkey = loginpage.getPassword(driver);
		passkey.sendKeys(password);

		login = loginpage.getLoginButton(driver);
		login.click();
		System.out.println("Clicked on Sign In button");
		Thread.sleep(12000);

	}

	// @Test(priority = 1)
	public void signOut() throws Exception {

		System.out.println("Sign out test started");
		Thread.sleep(3000);

		// hamburger menu icon
		driver.findElementByClassName("android.widget.ImageButton").click();
		driver.navigate().back();
		driver.findElementByClassName("android.widget.ImageButton").click();

		// settings tab
		driver.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		String username = driver.findElementById("com.zimplistic.rotimaticmobile:id/tvUserName").getText();
		System.out.println("Welcome - " + username);

		String emailID = driver.findElementById("com.zimplistic.rotimaticmobile:id/tvUserEmail").getText();
		System.out.println("Email ID - " + emailID);

		String serialNumber = "N/A";
		if (!driver.findElementsById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").isEmpty())
			serialNumber = driver.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();
		System.out.println("Serial Number - " + serialNumber);

		String machineName = "N/A";
		if (!driver.findElementsById("com.zimplistic.rotimaticmobile:id/tvMachineName").isEmpty())
			machineName = driver.findElementById("com.zimplistic.rotimaticmobile:id/tvMachineName").getText();
		System.out.println("Machine name - " + machineName);

		// sign out option
		driver.findElementById("com.zimplistic.rotimaticmobile:id/tvSignOut").click();

		// sign out alert popup
		driver.findElementById("android:id/button1").click();
		Thread.sleep(2000);
		driver.navigate().back();

	}

}
