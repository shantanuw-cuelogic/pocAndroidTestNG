package com.zimplistic.rotimatic.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.setup.BaseSetup;

import io.appium.java_client.TouchAction;

public class ConfigureToRotimatic extends BaseSetup {

	String wifiName;
	String wifiPassword;

	Dimension size;

	@Test(priority = 0)
	private void failConfigureAutomatically() throws Exception {
		setup();
		wifiName = "Swapnil_Network";
		wifiPassword = "roti";

		signIn("qa@android.com", "asdf");
		Thread.sleep(10000);
		ad.findElementByClassName("android.widget.ImageButton").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();
		Thread.sleep(2000);
		getScreenshot(ad, FOLDER_CONFIGURATION);

		// Configure link
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConfigureRotimatic").click();

		// Tutorial I
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Tutorial II
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Allow location permission access for O.S. 6 and above
		System.out.println("Checking location service");
		if (!ad.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).isEmpty())
			ad.findElementByName("Allow").click();

		// To verify location is enabled or not for O.S. 6 and above
		if (!ad.findElements(By.name("Turn on location services")).isEmpty()) {

			ad.findElementById("android:id/button1").click();
			ad.findElementById("com.android.settings:id/switch_widget").click();
			ad.navigate().back();
		}

		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();

		Thread.sleep(3000);
		getScreenshot(ad, FOLDER_CONFIGURATION);

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvConnecting")).isEmpty()) {
			System.out.println("No Rotimatic WiFi Available");
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			signOut();
		} else {
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvWifiName").click();
			Thread.sleep(9000);

			// Add 5Ghz alert check here
			if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvFreqErrorRetry")).isEmpty())
				ad.findElementByName("RETRY").click();

			// Set Home WiFi SSID
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifi").clear();
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifi").sendKeys(wifiName);

			// Set Home WiFi password
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifiPassword").sendKeys(wifiPassword);

			// ad.navigate().back(); // To hide keyboard
			getScreenshot(ad, FOLDER_CONFIGURATION);

			size = ad.manage().window().getSize();
			int x = size.getWidth() - 20;
			int y = size.getHeight() - 40;
			// System.out.println("X = " + x + "Y = " + y);

			TouchAction a = new TouchAction(ad);
			a.tap(x, y).perform();

			// ad.findElementById("com.zimplistic.rotimaticmobile:id/tvButtonText").click();
			Thread.sleep(15000);
			getScreenshot(ad, FOLDER_CONFIGURATION);
			System.out.println("Rotimatic trying to connect to server");
			Thread.sleep(55000);

			String expected = "Oops, something went wrong!";
			String actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvError1").getText();
			getScreenshot(ad, FOLDER_CONFIGURATION);

			// System.out.println("Actual message = " + actual);

			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Configuration fail Test Passed");
			} else
				System.out.println("Configuration fail Test Failed");

			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvRetryConnect").click();
			System.out.println("----------------" + "\n");
		}

	}// End of function

	@Test(priority = 1)
	private void successConfigureAutomatically() throws Exception {

		wifiName = "Swapnil_Network";
		wifiPassword = "rotimatic";

		// Tutorial II
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Allow location permission access for O.S. 6 and above
		System.out.println("Checking location service");
		if (!ad.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).isEmpty())
			ad.findElementByName("Allow").click();

		// To verify location is enabled or not for O.S. 6 and above
		if (!ad.findElements(By.name("Turn on location services")).isEmpty()) {

			ad.findElementById("android:id/button1").click();
			ad.findElementById("com.android.settings:id/switch_widget").click();
			ad.navigate().back();
		}

		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();

		Thread.sleep(3000);
		getScreenshot(ad, FOLDER_CONFIGURATION);

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvConnecting")).isEmpty()) {

			System.out.println("No Rotimatic WiFi Available");
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			signOut();
		} else {
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvWifiName").click();

			Thread.sleep(9000);

			// Add 5Ghz alert check here
			if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvFreqErrorRetry")).isEmpty())
				ad.findElementByName("RETRY").click();

			// Set Home WiFi SSID
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifi").clear();
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifi").sendKeys(wifiName);

			// Set Home WiFi password
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifiPassword").sendKeys(wifiPassword);

			// ad.navigate().back(); // To hide keyboard
			getScreenshot(ad, FOLDER_CONFIGURATION);
			// ad.findElementByName("Done").click();
			// ad.sendKeyEvent(66);
			// ad.sendKeyEvent(AndroidKeyCode.ENTER);

			size = ad.manage().window().getSize();

			int x = size.getWidth() - 20;

			int y = size.getHeight() - 40;

			// System.out.println("X = " + x + "Y = " + y);

			TouchAction a = new TouchAction(ad);
			a.tap(x, y).perform();

			// ad.findElementById("com.zimplistic.rotimaticmobile:id/tvButtonText").click();
			Thread.sleep(2000);
			getScreenshot(ad, FOLDER_CONFIGURATION);
			System.out.println("Rotimatic trying to connect to server");
			Thread.sleep(50000);
			getScreenshot(ad, FOLDER_CONFIGURATION);

			String actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConnected").getText();

			// System.out.println("Actual =" + actual);

			String expected = "Connected";

			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Configuration success Test Passed");
			} else
				System.out.println("Configuration success Test Failed");

			ad.findElementByClassName("android.widget.ImageButton").click();
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();
			Thread.sleep(2000);
			getScreenshot(ad, FOLDER_CONFIGURATION);

			String serialNumber = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();

			System.out.println("Machine Serial # = " + serialNumber);

			// sign out option
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSignOut").click();

			// sign out alert popup
			ad.findElementByName("Sign Out").click();
			System.out.println("----------------" + "\n");
		}
	}

	@Test(priority = 2)
	private void configureManually() throws Exception {

		wifiName = "Swapnil_Network";
		wifiPassword = "rotimatic";

		signIn("qa@android.com", "asdf");
		Thread.sleep(10000);
		ad.findElementByClassName("android.widget.ImageButton").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();
		Thread.sleep(2000);
		getScreenshot(ad, FOLDER_CONFIGURATION);

		// Configure link
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConfigureRotimatic").click();

		// Tutorial I
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Tutorial II
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Allow location permission access for O.S. 6 and above
		System.out.println("Checking location service");
		if (!ad.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).isEmpty())
			ad.findElementByName("Allow").click();

		// To verify location is enabled or not for O.S. 6 and above
		if (!ad.findElements(By.name("Turn on location services")).isEmpty()) {

			ad.findElementById("android:id/button1").click();
			ad.findElementById("com.android.settings:id/switch_widget").click();
			ad.navigate().back();
		}

		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();

		Thread.sleep(3000);
		getScreenshot(ad, FOLDER_CONFIGURATION);

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvConnecting")).isEmpty()) {
			System.out.println("No Rotimatic WiFi Available");
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			signOut();
		} else {
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvWifiName").click();
			Thread.sleep(9000);

			// Add 5Ghz alert check here
			if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvFreqErrorRetry")).isEmpty())
				ad.findElementByName("RETRY").click();

			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConnectManual").click();
			getScreenshot(ad, FOLDER_CONFIGURATION);

			// Set Home WiFi SSID
			// ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifiManual").clear();
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifiManual").sendKeys(wifiName);

			// Set Home WiFi password
			ad.findElementById("com.zimplistic.rotimaticmobile:id/edtHomeWifiPasswordManual").sendKeys(wifiPassword);

			getScreenshot(ad, FOLDER_CONFIGURATION);

			size = ad.manage().window().getSize();

			int x = size.getWidth() - 20;

			int y = size.getHeight() - 40;

			// System.out.println("X = " + x + "Y = " + y);

			TouchAction a = new TouchAction(ad);
			a.tap(x, y).perform();

			// ad.findElementById("com.zimplistic.rotimaticmobile:id/tvButtonText").click();
			Thread.sleep(2000);
			getScreenshot(ad, FOLDER_CONFIGURATION);
			System.out.println("Rotimatic trying to connect to server");
			Thread.sleep(50000);
			getScreenshot(ad, FOLDER_CONFIGURATION);

			String actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConnected").getText();

			// System.out.println("Actual =" + actual);

			String expected = "Connected";

			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Configuration success Test Passed");
			} else
				System.out.println("Configuration success Test Failed");

			ad.findElementByClassName("android.widget.ImageButton").click();
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();
			Thread.sleep(2000);
			getScreenshot(ad, FOLDER_CONFIGURATION);

			String serialNumber = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();

			System.out.println("Machine Serial # = " + serialNumber);
			System.out.println("----------------" + "\n");
		}

	}// End of function

	@Test(priority = 3)
	private void configureContinuetoApp() throws Exception {

		// Configure link
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConfigureRotimatic").click();

		// Tutorial I
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Tutorial II
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnOk").click();

		// Allow location permission access for O.S. 6 and above
		System.out.println("Checking location service");
		if (!ad.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).isEmpty())
			ad.findElementByName("Allow").click();

		// To verify location is enabled or not for O.S. 6 and above
		if (!ad.findElements(By.name("Turn on location services")).isEmpty()) {

			ad.findElementById("android:id/button1").click();
			ad.findElementById("com.android.settings:id/switch_widget").click();
			ad.navigate().back();
		}

		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/action_refresh").click();

		Thread.sleep(3000);
		getScreenshot(ad, FOLDER_CONFIGURATION);

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvConnecting")).isEmpty()) {
			System.out.println("No Rotimatic WiFi Available");
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			ad.navigate().back();
			signOut();
		} else {
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvWifiName").click();
			Thread.sleep(9000);

			// Add 5Ghz alert check here
			if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvFreqErrorRetry")).isEmpty())
				ad.findElementByName("RETRY").click();

			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvConnectManual").click();

			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvContinueApp").click();
			Thread.sleep(5000);

			String actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/toolbar_title").getText();
			String expect = "Home";

			if (actual.equalsIgnoreCase(expect)) {
				System.out.println("Continue to App link test Passed");
			} else
				System.out.println("Continue to App link test Failed");

			signOut();
			System.out.println("----------------" + "\n");
		}

	}// End of function

}// End of class
