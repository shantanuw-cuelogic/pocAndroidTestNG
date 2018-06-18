package com.zimplistic.rotimatic.testcases;

import org.testng.annotations.Test;

import com.zimplistic.rotimatic.setup.BaseSetup;

public class FacebookLogin extends BaseSetup {

	String fbname = "zimplisticandrotest@gmail.com";
	String fbpassword = "NewGr8Apps!";

	@Test(priority = 0)
	private void facebookLogin() throws Exception {
		setup();

		ad.findElementById("com.zimplistic.rotimaticmobile:id/ivFbSignUp").click();
		Thread.sleep(4000);
		getScreenshot(ad, FOLDER_FACEBOOK);

		// android:id/alertTitle // location turn On alert
		// android:id/button1 // SET
		// android:id/button2 // Cancel

		if (!ad.findElementsById("com.facebook.katana:id/login_username").isEmpty()) {

			ad.findElementById("com.facebook.katana:id/login_username").clear();
			ad.findElementById("com.facebook.katana:id/login_username").sendKeys(fbname);

			ad.findElementById("com.facebook.katana:id/login_password").sendKeys(fbpassword);

			ad.findElementById("com.facebook.katana:id/login_login").click();

			Thread.sleep(10000);

			getScreenshot(ad, FOLDER_FACEBOOK);

			Thread.sleep(15000);

		}

		// hamburger menu icon
		ad.findElementByClassName("android.widget.ImageButton").click();
		getScreenshot(ad, FOLDER_FACEBOOK);
		ad.navigate().back();
		
		ad.findElementByName("Chat").click();
		Thread.sleep(10000);
		getScreenshot(ad, FOLDER_FACEBOOK);
		ad.navigate().back();
		signOut();
	}

}
