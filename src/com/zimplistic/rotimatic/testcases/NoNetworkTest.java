package com.zimplistic.rotimatic.testcases;

import org.testng.annotations.Test;

import com.zimplistic.rotimatic.setup.BaseSetup;

public class NoNetworkTest extends BaseSetup {

	String expected;

	String actual;

	@Test(priority = 0)
	private void noNetworkOnLandingScreen() throws Exception {

		setup();

		networkDisable();
		
		// No network video play
		expected = "No Internet found. Check your connection.";
		
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnPlay").click();

		actual = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvError").getText();
		
		//System.out.println(actual);
		
		if (actual.equalsIgnoreCase(expected)) {
			System.out.println("No Network Video Play Test Passed");
		} else
			System.out.println("No Network Video play Test Failed");

		getScreenshot(ad, FOLDER_NETWORKTEST);
		ad.findElementById("com.zimplistic.rotimaticmobile:id/btnClose").click();
		

		// Facebook no network
		expected = "Network not available";
		
		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivFbSignUp").click();
		
		actual = ad.findElementById("android:id/message").getText();
		//System.out.println(actual);
		
		if (actual.equalsIgnoreCase(expected)) {
			System.out.println("No Network Facebook Test Passed");
		} else
			System.out.println("No Network Facebook Test Failed");

		getScreenshot(ad, FOLDER_NETWORKTEST);

		ad.findElementByName("OK").click();
		
		networkEnable();

	}

}
