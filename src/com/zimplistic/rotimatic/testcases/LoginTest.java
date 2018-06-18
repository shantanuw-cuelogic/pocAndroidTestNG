package com.zimplistic.rotimatic.testcases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.pageobjects.LoginPage;

import com.zimplistic.rotimatic.setup.BaseSetupManager;
import com.zimplistic.rotimatic.setup.Constants;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class LoginTest {

	LoginPage loginpage = new LoginPage();
	AndroidDriver<MobileElement> ad;

	WebElement signIn, email, passkey, login, hammenu;

	@Test(priority = 0)
	@Parameters({"platform", "deviceNAME", "systemPort"})
	private void testInvalidLogin(String platform, String deviceNAME, String systemPort) throws Exception {

		ad = BaseSetupManager.getInstance(platform,deviceNAME,systemPort).getAndroidDriver();
		
		
		// unlock device
		Runtime.getRuntime().exec("adb shell am start -n io.appium.unlock/.Unlock");
		
		
		System.out.println("Invalid Login test Started");

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/ivSignIn").isEmpty()) {
			signIn("qa@android.com", "12345");
			Thread.sleep(5000);
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
			ad.findElementById("android:id/button1").click();
			ad.navigate().back();
		} else
			System.out.println("User is already logged in to app");

	}

	@Test(priority = 1)
	public void testLogin() throws Exception {

		// AppiumDriverLocalService service =
		// AppiumDriverLocalService.buildDefaultService();
		// service.start();

		System.out.println("Login test Started");

		if (!ad.findElementsById("com.zimplistic.rotimaticmobile:id/ivSignIn").isEmpty()) {
			signIn("qa@android.com", "asdf");
			
			BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
		} else
			System.out.println("User is already logged in to app");

	}

	public void signIn(String username, String password) throws Exception {

		signIn = loginpage.getSignIn(ad);

		signIn.click();

		email = loginpage.getUsername(ad);

		email.sendKeys(username);

		passkey = loginpage.getPassword(ad);
		passkey.sendKeys(password);

		login = loginpage.getLoginButton(ad);
		login.click();
		System.out.println("Clicked on Sign In button");
		Thread.sleep(15000);

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

	@Test(priority = 2)
	private void settingsScreen() throws Exception {

		/*hammenu = loginpage.getHamburgerMenu();
		hammenu.click();*/
		System.out.println("Inside settings screen");
		Thread.sleep(5000);
		ad.findElementByClassName("android.widget.ImageButton").click();

		// settings tab
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		String username = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserName").getText();
		System.out.println("Welcome - " + username);

		String emailID = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserEmail").getText();
		System.out.println("Email ID - " + emailID);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

		String serialNumber = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSerialNumber").getText();
		System.out.println("Serial Number - " + serialNumber);
		
		String machineName = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvMachineName").getText();
		System.out.println("Machine name - " + machineName);

		ad.navigate().back();
	}

	/*
	 * com.zimplistic.rotimaticmobile:id/menuFWNotfn
	 * 
	 * Your Rotimatic is up-to-date
	 * 
	 * com.zimplistic.rotimaticmobile:id/tvPositive
	 * 
	 * 
	 * com.zimplistic.rotimaticmobile:id/tileConnectRotimatic
	 * 
	 * if com.zimplistic.rotimaticmobile:id/tvMessage user is associated to
	 * Rotimatic
	 * 
	 */

	@Test(priority = 3)
	private void updatePhoneNumber() throws Exception {

		System.out.println("Updating phone number");
		Thread.sleep(7000);
		ad.findElementByClassName("android.widget.ImageButton").click();

		// Settings menu
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		// Phone number edit icon
		ad.findElementById("com.zimplistic.rotimaticmobile:id/relEditPhoneNumber").click();

		// Blank phone number validation
		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtRobLightPhoneNumber").clear();
		ad.navigate().back();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnSave").click();
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

		// Phone number field validation
		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtRobLightPhoneNumber").sendKeys("1234");
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnSave").click();
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

		// Country List
		ad.findElementById("com.zimplistic.rotimaticmobile:id/spCountries").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvCountry").click();

		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtRobLightPhoneNumber").clear();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/edtRobLightPhoneNumber").sendKeys("98901");

		ad.navigate().back();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnSave").click();

		Thread.sleep(8000);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);
		
		ad.navigate().back();

	}

	@Test(priority = 4)
	private void testRecipe() throws Exception {

		System.out.println("Inside recipe");

		Thread.sleep(2000);
		// See more
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSeeMore").click();
		
		ad.findElementByName("HAPPY KIDS").click();
		
		// Check for Spicy Bhuchakra later
		
		ad.findElementByName("Spicy Bhuchakra").click();
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_RECIPE);
		
		// Share
		
		// ad.findElementById("com.zimplistic.rotimaticmobile:id/ivShare").click();
		
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvRecipes").click();
		Thread.sleep(2000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvHome").click();
		Thread.sleep(2000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvCategory").click();
		Thread.sleep(2000);
		ad.navigate().back();
		Thread.sleep(2000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPlayFullScreen").click();
		Thread.sleep(5000);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_RECIPE);
		ad.navigate().back();
	}
	
	
	
	@Test(priority = 5)
	private void quickHelp() throws Exception {
		ad.navigate().back();

		System.out.println("Inside Quick Help");
		Thread.sleep(5000);
		ad.findElementByClassName("android.widget.ImageButton").click();
		
		// Quick Help

		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSupport").click();
		Thread.sleep(15000);

		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

		ad.findElementByClassName("android.view.View").click();
		Thread.sleep(10000);
		BaseSetupManager.getScreenshot(ad, Constants.FOLDER_LOGIN);

		ad.navigate().back();
		
		ad.navigate().back();
		ad.navigate().back();

		/*signOut();

		System.out.println("User is signed out from the app");*/
		System.out.println("----------------" + "\n");

	
	}
	
	
	
	@Test(priority = 6)
	private void tearDown() {
		// Unistalling apks on OS 7
		BaseSetupManager.unistallApps();
		
		ad.close();
		
	}
}
