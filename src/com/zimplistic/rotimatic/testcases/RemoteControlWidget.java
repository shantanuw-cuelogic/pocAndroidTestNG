package com.zimplistic.rotimatic.testcases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.pageobjects.LoginPage;
import com.zimplistic.rotimatic.setup.BaseSetupManager;
import com.zimplistic.rotimatic.setup.Constants;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

// Assuming Remote Control build
public class RemoteControlWidget implements Constants {

	LoginPage loginpage = new LoginPage();
	AndroidDriver<MobileElement> ad;
	String state;
	String serialNumber = "N/A";

	WebElement signIn, email, passkey, login, hammenu, tapUserGuideScreen;

	@Test(priority = 0)
	@Parameters({ "platform", "deviceNAME", "systemPort" })
	public void setup(String platform, String deviceNAME, String systemPort) throws Exception {

		// unlock device
		// Runtime.getRuntime().exec("adb shell am start -n io.appium.unlock/.Unlock");

		try {
			ad = BaseSetupManager.getInstance(platform, deviceNAME, systemPort).getAndroidDriver();
		} catch (Exception e) {
			System.out.println("Android setup error");
			e.printStackTrace();
		}
	}

	@Parameters({ "username", "password" })
	@Test(priority = 1)
	public void testLogin(String username, String password) throws Exception {

		System.out.println("Login test Started");

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/ivSignIn").isEmpty()) {
			signIn(username, password);

			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
		} 
		
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
			// check whether user is associated or not
			System.out.println("User is not associated to any Rotimatic");
			
			System.out.println("Skipping connection");
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

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

		} else
			System.out.println("User is on Home screen");

		// Check (RC user guide screens)

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/image").isEmpty()) {
			System.out.println("User guide screens displayed");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);
			tapUserGuideScreen = ad.findElementById("com.zimplistic.rotimaticmobile:id/image");
			tapUserGuideScreen.click();
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);
			tapUserGuideScreen.click();
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);
			ad.navigate().back();
			System.out.println("Home screen displayed");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);
		} else
			System.out.println("User guide screens not displayed");

	}

	@Test(priority = 2)
	private void settingsScreen() throws Exception {

		System.out.println("Inside settings screen");
		Thread.sleep(5000);
		// ad.findElementByClassName("android.widget.ImageButton").click();
		// ad.navigate().back();

		ad.findElementByClassName("android.widget.ImageButton").click();

		// settings tab
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		String username = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserName").getText();
		System.out.println("Welcome - " + username);

		String emailID = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserEmail").getText();
		System.out.println("Email ID - " + emailID);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").isEmpty()) {
			serialNumber = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();
			System.out.println("Serial Number - " + serialNumber);

			if (serialNumber.equalsIgnoreCase("N/A") || serialNumber.equalsIgnoreCase(""))
				System.out.println("User is not associated with any Rotimatic");
			ad.navigate().back();
				Assert.fail("User is not associated with any Rotimatic");
		} else {
			System.out.println("Serial Number - " + serialNumber);
			ad.navigate().back();
			Assert.fail("User is not associated with any Rotimatic");
		}
		String machineName = "N/A";
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvMachineName").isEmpty())
			machineName = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvMachineName").getText();
		System.out.println("Machine name - " + machineName);

		ad.navigate().back();
	}

	@Test(priority = 3)
	public void testRemoteControlState() throws Exception {

		System.out.println("RC test started");
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);

		state = getCurrentRTState();
		System.out.println("Current state of Rotimatic :- " + state);

		// Home screen verify RC widget with following main conditions

		// if (RT is online but powered off)
		// com.zimplistic.rotimaticmobile:id/ivPowerOn

		// if (RT is online and powered on)
		// com.zimplistic.rotimaticmobile:id/tvRemoteStatus => Online

		// if (RT is offline)
		// com.zimplistic.rotimaticmobile:id/tvRemoteStatus => Offline
		// then click on Try again

		if (state.contains("Offline")) {
			System.out.println("Rotimatic is Offline");

			System.out.println("Clicking on Retry option");

			ad.findElementByXPath("//*[@text='Try again']").click();
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);
			Thread.sleep(3000);
			System.out.println("Retrying ..");
			state = getCurrentRTState();

			System.out.println("Rotimatic is Offline, please bring it online" + serialNumber);
		}

		if (state.contains("Online")) {
			System.out.println("Rotimatic is Online");
			// Check whether RT is powered ON/OFF
			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/ivPowerOn").isEmpty()) {
				System.out.println("RT is online but powered off");
				powerOn();
			}

			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/togglePowerOn").isEmpty()) {
				System.out.println("RT is Online and powered on");

			}

		}

		if (state.contains("Napping")) {
			System.out.println("Rotimatic is in Napping state");
			powerOff();
		}

		System.out.println("End of RC widget test");

	}
	
	@Test(priority = 4)
	public void testWiFiConfig() throws Exception {
		
		
		
	}

	private void powerOn() throws Exception {
		System.out.println("Clicking on power on icon");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPowerOn").click();
		System.out.println("Powering on ..");
		Thread.sleep(20000);
		state = getCurrentRTState();
		System.out.println("RT state is :- "+state);

		
	}

	private void powerOff() throws Exception {
		System.out.println("Clicking on power off icon");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/togglePowerOn").click();

		// yes option
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();
		System.out.println("Powering off");
		Thread.sleep(50000);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_REMOTE_CONTROL);

	}

	public String getCurrentRTState() {

		return state = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvRemoteStatus").getText();

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
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
		Thread.sleep(12000);

		// Check for any error alert
		if (!ad.findElementsById("android:id/button1").isEmpty()) {
			ad.findElementById("android:id/button1").click();
			System.err.println("Error occured while signing in");
			Assert.fail("Error occured while signing in");
		}

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

	@AfterTest(alwaysRun = true)
	public void teardown() throws Exception {

		// signOut();

		System.out.println("End of test suite");

		// uninstall app
		// Runtime.getRuntime().exec("adb uninstall com.zimplistic.rotimaticmobile");
		// System.out.println("App uninstalled successfully");
		// ad.close();
	}

}
