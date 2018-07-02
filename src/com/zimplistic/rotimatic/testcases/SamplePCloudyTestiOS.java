package com.zimplistic.rotimatic.testcases;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.By;
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

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class SamplePCloudyTestiOS {

	IOSDriver<MobileElement> driver;
	int deviceBookDuration = 5;
	PCloudyAppiumSession pCloudySession;
	Boolean autoSelectDevices = true;

	@BeforeTest
	public void runExecutionOnPCloudy() throws Exception {

		Connector pCloudyCONNECTOR = new Connector("https://device.pcloudy.com/api/");

		// User Authentication over pCloudy
		String authToken = pCloudyCONNECTOR.authenticateUser("shantanu.wagholikar@cuelogic.co.in",
				"7cfp53zy3jw9w5sym3jzjhcz");

		// Select apk in pCloudy Cloud Drive
		File fileToBeUplodrivered = new File("D:\\Cue - Projects\\RATS\\RotimaticMobile.Resigned1530166431.ipa");
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
			selectedDevices.addAll(pCloudyCONNECTOR.chooseDevices(authToken, "iOS", new Version("9.4.*"), new Version("11.3.*"), 1));
		else
			selectedDevices.add(pCloudyCONNECTOR.chooseSingleDevice(authToken, "iOS"));

		System.out.println("Selected device" +selectedDevices.size());
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
		capabilities.setCapability("newCommandTimeout", 900);
		capabilities.setCapability("launchTimeout", 90000);
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability("deviceName", pCloudySession.getDto().capabilities.deviceName);
		capabilities.setCapability("browserName", pCloudySession.getDto().capabilities.deviceName);
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("bundleId", "com.zimple.sg");
		capabilities.setCapability("usePrebuiltWDA", false);
		capabilities.setCapability("acceptAlerts", true);
		capabilities.setCapability("rotatable", false);

		URL appiumEndpoint = pCloudySession.getConnector().AppiumApis()
				.getAppiumEndpoint(pCloudySession.getAuthToken());
		try {
		driver = new IOSDriver<MobileElement>(appiumEndpoint, capabilities);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Driver setup done");
	}

	@Test
	public void testLogin() throws Exception {
		
		System.out.println("Waiting for device to clean up");
		Thread.sleep(20000);
		
		// Push notification and Location alert
		if(!driver.findElementsByName("Allow").isEmpty()) {
		driver.switchTo().alert().dismiss();
		driver.switchTo().alert().dismiss();
		}
		// Check whether user is already logged in or not
		System.out.println("Login test started");
		if (!driver.findElementsByXPath("//*[@label='HOME']").isEmpty()) {

			System.out.println("User is already logged in to app");

		} else {

			System.out.println("Clicking on Sign In button");
			driver.findElement(By.xpath("//*[@label='sign in button']")).click();
			System.out.println("Sign in screen");

			System.out.println("Sending email id");
			driver.findElement(By.xpath("//*[@value='Email Address']")).sendKeys("qa@ios5.com");

			System.out.println("Entering Password ..");
			driver.findElement(By.xpath("//*[@value='Password']")).sendKeys("asdf");

			driver.findElement(By.xpath("//*[@label='Sign in']")).click();
			System.out.println("Clicked on Sign In button");
			Thread.sleep(12000);
		}

		driver.findElement(By.xpath("//*[@label='menu icon']")).click();
		Thread.sleep(2000);

		System.out.println("Sign out started");
		System.out.println("Clicking on settings menu");
		driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Settings\"]").click();

		System.out.println("Clicked on settings menu");
		driver.findElement(By.xpath("//*[@label='Sign out']")).click();

		// driver.findElement(By.xpath("//*[@type='XCUIElementTypeButton' AND
		// @label='Yes']")).click();
		driver.findElement(By.xpath("//*[@label='Yes']")).click();
		Thread.sleep(3000);
		System.out.println("End of login test");

		driver.closeApp();
	}

}
