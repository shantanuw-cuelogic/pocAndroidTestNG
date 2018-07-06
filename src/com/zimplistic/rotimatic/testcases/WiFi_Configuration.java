package com.zimplistic.rotimatic.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.pageobjects.LoginPage;
import com.zimplistic.rotimatic.setup.BaseSetupManager;
import com.zimplistic.rotimatic.setup.Constants;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

// Assuming Remote Control build
public class WiFi_Configuration implements Constants {

	LoginPage loginpage = new LoginPage();
	AndroidDriver<MobileElement> ad;
	String state;
	String srNumber = "N/A";
	Dimension size;
	String ssid = "Cuelogic";
	String password = "asdf";

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

		System.out.println("Checking for user Login status");

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

		// No internet alert

		if (!ad.findElementsById("android:id/alertTitle").isEmpty()) { // check for title
			System.out.println("No internet alert displayed");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
			// WiFi settings
			ad.findElementById("android:id/button1").click();
			ad.findElementByXPath("//*[@text='" + ssid + "']").click();
			Thread.sleep(5000);
			// Connect
			if (!ad.findElementsById("android:id/button1").isEmpty())
				ad.findElementById("android:id/button1").click();
			ad.navigate().back();
			ad.navigate().back();
		}

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

		System.out.println("Clicking on settings screen");
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
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_SETTINGS);

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").isEmpty()) {
			srNumber = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();
			System.out.println("Serial Number - " + srNumber);

			/*
			 * if (serialNumber.equalsIgnoreCase("N/A") ||
			 * serialNumber.equalsIgnoreCase("")) {
			 * System.out.println("User is not associated with any Rotimatic");
			 * ad.navigate().back(); }
			 */
		} else
			System.out.println("Serial Number - N/A");

		String machineName = "N/A";
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvMachineName").isEmpty())
			machineName = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvMachineName").getText();
		System.out.println("Machine name - " + machineName);

		ad.navigate().back();
	}

	@Parameters({ "serialNumber" })
	@Test(priority = 3)
	public void testRemoteControlState(String serialNumber) throws Exception {

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

		if (state.contains("Not configured")) {
			System.out.println("User is not associated to any Rotimatic");

			configureToRotimaticFromHomeScreen(serialNumber);
		}

		System.out.println("End of RC widget test");

	}

	private void configureToRotimaticFromHomeScreen(String serialNumber) throws Exception {
		System.out.println("WiFi configuration started from Home screen");

		System.out.println("Setup Rotimatic");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSetupRotimatic").click();
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

		System.out.println("On sad smiley screen, click on Next option");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/frameNext").click();
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

		System.out.println("Connect to Rotimatic user guide screen");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnNext").click();
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

		System.out.println("Checking for Location alert");
		// Location allow popup two types - System default and Custom
		if (!ad.findElementsById("com.android.packageinstaller:id/permission_allow_button").isEmpty()) {
			System.out.println("Location permission system alert displayed");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			ad.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		}

		// To verify location is enabled or not for O.S. 6 and above
		if (!ad.findElementsById("android:id/button1").isEmpty()) {
			System.out.println("Device location is OFF");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			ad.findElementById("android:id/button1").click();
			ad.findElementById("com.android.settings:id/switch_widget").click();
			ad.navigate().back();
			System.out.println("Device location is ON");
		}

		// Check available Rotimatic WiFi nearby
		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvConnecting")).isEmpty()) {
			System.out.println("No Rotimatic WiFi Available");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/action_refresh").isEmpty()) {
				System.out.println("Trying to click on Refresh icon 1st time");
				ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
				Thread.sleep(5000);

				System.out.println("Trying to click on Refresh icon 2nd time");
				ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
				Thread.sleep(5000);
			}
		}

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvConnecting")).isEmpty()) {
			System.out.println("After retrying, No Rotimatic WiFi Available");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

			System.out.println("Skipping connection");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

			// Skip setup link
			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
				System.out.println("Skipping setup");
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();

				// Yes
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

				// Got It
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

				Thread.sleep(2000);

				System.out.println("No Rotimatic is in AP mode");
				// Fail test case
				Assert.fail("No Rotimatic is in AP mode");
			}

		}

		// Rotimatic WiFi available

		System.out.println("Rotimatic WiFi available ..");
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
		/*
		 * String RT_WiFi =
		 * ad.findElementById("com.zimplistic.rotimaticmobile:id/tvWifiName").getText();
		 * System.out.println("WiFi names " + RT_WiFi);
		 * 
		 * String RT_list =
		 * ad.findElementById("com.zimplistic.rotimaticmobile:id/listView").getText();
		 * System.out.println("List text " + RT_list);
		 */

		String rtSerialNumber = "Rotimatic " + serialNumber;

		ad.findElementByXPath("//*[@text='" + rtSerialNumber + "']").click();
		System.out.println("Waiting to connect with Rotimatic WiFi");
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
		Thread.sleep(22000);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
		// handle unable to connect selected network toast

		// com.zimplistic.rotimaticmobile:id/tvChangeNetwork

		// Checking for select WiFi alert is present or not ?

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/action_refresh").isEmpty()) {
			System.out.println("Trying to click on Refresh icon again");
			ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
			Thread.sleep(5000);

			ad.findElementByXPath("//*[@text='" + rtSerialNumber + "']").click();
			System.out.println("Waiting to connect with Rotimatic WiFi");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			Thread.sleep(20000);
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);

		}

		connectToRT();

		System.out.println("Rotimatic trying to connect to server");

		if (!ad.findElementsById("android:id/button1").isEmpty()) {
			System.out.println("Alert! You are not connected to Rotimatic WiFi.");
			ad.findElementById("android:id/button1").click();
			ad.findElementByClassName("android.widget.ImageButton").click(); // back icon

			System.out.println("retrying process");
			connectToRT();
		}

		Thread.sleep(40000);
		System.out.println("Check WiFI config status");
		Thread.sleep(10000);
		BaseSetupManager.getScreenshot(ad, FOLDER_WIFI_CONFIG);

		// Check success or failure result here

		// Reconnect screen on failure
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvRetryConnect").isEmpty()) {
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			System.out.println("oops, something went wrong screen displayed");
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvRetryConnect").click();
			System.out.println("Clicked on Reconnect screen");
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();
			System.out.println("Clicked on Okay alert");

			// Skip setup link
			if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvBottomText").isEmpty()) {
				System.out.println("Skipping setup");
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvBottomText").click();

				// Yes
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

				// Got It
				ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();

				Thread.sleep(2000);
				signOut();

			}

			Assert.fail("WiFi configuration failed,oops, something went wrong screen displayed for Rotimatic "
					+ serialNumber);

		}

		// In RC build, // Check (RC user guide screens)
		Thread.sleep(5000);
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/image").isEmpty()) {
			System.out.println("User guide screens displayed");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			tapUserGuideScreen = ad.findElementById("com.zimplistic.rotimaticmobile:id/image");
			tapUserGuideScreen.click();
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			tapUserGuideScreen.click();
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
			ad.navigate().back();
			System.out.println("Home screen displayed");
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_WIFI_CONFIG);
		} else
			System.out.println("User guide screens not displayed");

		// Auto update enabled popup ?? in normal build
		// Check for Got it alert
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvPositive").isEmpty()) {
			System.out.println("Got it alert displayed");
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPositive").click();
		}

		// Check on settings screen

		settingsScreen();
		if (srNumber.contains(serialNumber))
			System.out.println("WiFi Configuration done successfully for Rotimatic " + serialNumber);
		else
			Assert.fail("WiFi configuration fails, took time more than 1 minute");

	}

	private void connectToRT() throws Exception {
		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/tvChangeNetwork").isEmpty())
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvChangeNetwork").click();

		// ad.findElementById("com.zimplistic.rotimaticmobile:id/tvNetworkName")
		Thread.sleep(2000);

		// Need to use scroll if Given WiFi not found in list
		ad.findElementByXPath("//*[@text='" + ssid + "']").click();
		Thread.sleep(1000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifiPassword").sendKeys(password);

		// Keyboard button OK is at right bottom corner

		size = ad.manage().window().getSize();
		int x = size.getWidth() - 20;
		int y = size.getHeight() - 40;
		// System.out.println("X = " + x + "Y = " + y);

		TouchAction a = new TouchAction(ad);
		a.tap(x, y).perform();

		Thread.sleep(2000);
		BaseSetupManager.getScreenshot(ad, FOLDER_WIFI_CONFIG);

	}

	private void powerOn() throws Exception {
		System.out.println("Clicking on power on icon");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPowerOn").click();
		System.out.println("Powering on ..");
		Thread.sleep(20000);
		state = getCurrentRTState();
		System.out.println("RT state is :- " + state);

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
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
			ad.findElementById("android:id/button1").click();
			System.err.println("Error occured while signing in");
			Assert.fail("Error occured while signing in");
		}

	}

	public void signOut() throws InterruptedException {

		System.out.println("Sign out started");
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
		// ad.navigate().back();
		// ad.navigate().back();

		System.out.println("End of test suite");

		// uninstall app
		// Runtime.getRuntime().exec("adb uninstall com.zimplistic.rotimaticmobile");
		// System.out.println("App uninstalled successfully");
		// ad.close();
	}

}
