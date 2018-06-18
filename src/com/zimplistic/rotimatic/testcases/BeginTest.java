package com.zimplistic.rotimatic.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.zimplistic.rotimatic.setup.BaseSetup;

public class BeginTest extends BaseSetup {

	@Test(priority = 0)
	private void splashScreen() throws Exception {
		setup();
		
		System.out.println("Splash screen test Started");
		
		signUp();
		
		// video play icon
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnPlay").click();

		// ad.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Thread.sleep(40000);

		getScreenshot(ad, FOLDER_SPLASH);

		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnClose").click();

	}

	public void signUp() throws Exception {
		
		String countryname = "India";
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivSignUp").click();
		Thread.sleep(2000);
		
		ad.navigate().back();
		
		ad.findElementByName("Country").click();
		
		//ad.scrollToExact(countryname).click();
		
		ad.findElementByXPath("//[@id='com.zimplistic.rotimaticmobile:id/tvCountry' and @text='Angola']").click();
		
		System.out.println("India selected");
		
		
		
	}
	
	@Test(priority = 1)
	private void webViewTest() throws Exception {
		// sign up
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivSignUp").click();
		ad.navigate().back(); // To close keyboard

		// privacy policy
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvPrivacyPolicy").click();
		Thread.sleep(15000);

		getScreenshot(ad, FOLDER_SPLASH);

		String actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/toolbar_title").getText();
		String expect = "Privacy Policy";

		if (actual.equalsIgnoreCase(expect)) {
			System.out.println("Privacy policy Web view Test Passed");
		} else
			System.out.println("Privacy policy Web view Test Failed");

		// Navigate back to sign up
		ad.findElementByClassName("android.widget.ImageButton").click();

		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvTermsConditions").click();
		Thread.sleep(15000);

		getScreenshot(ad, FOLDER_SPLASH);

		String actualTest = ad.findElementById("com.zimplistic.rotimaticmobile:id/toolbar_title").getText();
		String expectTest = "Terms & Conditions";

		if (actualTest.equalsIgnoreCase(expectTest)) {
			System.out.println("Terms & Conditions Web view Test Passed");
		} else
			System.out.println("Terms & Conditions Web view Test Failed");

		// Navigate back to Sign Up
		ad.findElementByClassName("android.widget.ImageButton").click();

		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnBack").click();
	}

	@Test(priority = 2)
	private void skipLater() throws Exception {

		// Skip later link
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSkipLogin").click();

		// Quick Help
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tileSupport").click();
		Thread.sleep(15000);
		swipingVerticallyUp();
		getScreenshot(ad, FOLDER_SPLASH);

		ad.navigate().back();

		// Settings - Sign In
		ad.findElementByClassName("android.widget.ImageButton").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSignIn").click();

		getScreenshot(ad, FOLDER_SPLASH);

	}

	@Test(priority = 3)
	private void skipLaterVideosPlay() throws Exception {

		// Skip later link
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSkipLogin").click();

		Thread.sleep(10000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPlayFullScreen").click();

		if (ad.findElements(By.name("Home")).isEmpty()) {
			System.out.println("Playing video #1");
			Thread.sleep(15000);
			getScreenshot(ad, FOLDER_SPLASH);
			ad.navigate().back();
		}

		swipingHorizontalRight();
		Thread.sleep(10000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPlayFullScreen").click();

		if (ad.findElements(By.name("Home")).isEmpty()) {
			System.out.println("Playing video #2");
			Thread.sleep(15000);
			getScreenshot(ad, FOLDER_SPLASH);
			ad.navigate().back();
		}

		swipingHorizontalRight();
		Thread.sleep(10000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPlayFullScreen").click();

		if (ad.findElements(By.name("Home")).isEmpty()) {
			System.out.println("Playing video #3");
			Thread.sleep(15000);
			getScreenshot(ad, FOLDER_SPLASH);
			ad.navigate().back();
		}

		swipingHorizontalRight();
		Thread.sleep(10000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPlayFullScreen").click();

		if (ad.findElements(By.name("Home")).isEmpty()) {
			System.out.println("Playing video #4");
			Thread.sleep(15000);
			getScreenshot(ad, FOLDER_SPLASH);
			ad.navigate().back();
		}

		swipingHorizontalRight();
		Thread.sleep(10000);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivPlayFullScreen").click();

		if (ad.findElements(By.name("Home")).isEmpty()) {
			System.out.println("Playing video #5");
			Thread.sleep(15000);
			getScreenshot(ad, FOLDER_SPLASH);
			ad.navigate().back();
		}

		swipingHorizontalLeft();
		swipingHorizontalLeft();
		
		ad.findElementByClassName("android.widget.ImageButton").click();
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvLogout").click();

	}

	@Test(priority = 4)
	private void skipLaterChat() throws Exception {

		// Skip later link
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSkipLogin").click();

		Thread.sleep(5000);

		ad.findElementByName("Chat").click();
		ad.findElementById("android:id/button2").click();

		ad.findElementByName("Chat").click();
		ad.findElementById("android:id/button1").click();

		signIn("user@android.com", "asdf");
		Thread.sleep(15000);

		getScreenshot(ad, FOLDER_SPLASH);

		String actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/toolbar_title").getText();
		String expect = "Chat";

		if (actual.equalsIgnoreCase(expect)) {
			System.out.println("Chat screen load test Passed");
		} else
			System.out.println("Chat screen load test Failed");

		ad.navigate().back();

		signOut();
		
		System.out.println("End of Splash screen test");
		System.out.println("----------------" + "\n");

	}

}