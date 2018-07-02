package com.zimplistic.rotimatic.testcases;

import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.pageobjects.LoginPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

public class SauceLabsAndroidTest {

	private AndroidDriver<MobileElement> driver;
	LoginPage loginpage = new LoginPage();
	WebElement signIn, email, passkey, login, hammenu;

	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.8.1");
		caps.setCapability("deviceName","Samsung Galaxy S7 HD GoogleAPI Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "");
		caps.setCapability("platformVersion","7.1");
		caps.setCapability("platformName","Android");
		caps.setCapability("app","sauce-storage:Rotimatic.apk");
		caps.setCapability("testobjectApiKey", "4B5CAFB320EB405281242153A85DA959");
		caps.setCapability("appPackage", "com.zimplistic.rotimaticmobile");
		// Dynamic device allocation of an iPhone 7, running iOS 10.3 device
		//capabilities.setCapability("automationName", "uiautomator2");
		
		

		try {
			// Set Appium end point
			driver = new AndroidDriver<MobileElement>(new URL(
					"https://shantanuw.cuelogic:9ef959b6-0551-42d1-83ee-a1a54adba778@ondemand.saucelabs.com/wd/hub"),
					caps);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 0)
	public void testLogin() throws Exception {

		System.out.println("Waiting for device to clean up");
		Thread.sleep(10000);
		System.out.println("Login test Started");
		signIn("qa@android.com", "asdf");
		System.out.println("On home screen");
		signOut();
		System.out.println("End of test");

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
