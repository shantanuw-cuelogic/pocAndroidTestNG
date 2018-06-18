package com.zimplistic.rotimatic.pageobjects;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;

import com.zimplistic.rotimatic.setup.BaseSetupManager;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class LoginPage {
	
	//AndroidDriver driver = BaseSetupManager.getInstance().getAndroidDriver();
	WebElement element = null;

	//Sign In button
	public WebElement getSignIn(AndroidDriver<MobileElement> driver) {
		try {
			return driver.findElementById("com.zimplistic.rotimaticmobile:id/ivSignIn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public WebElement getUsername(AndroidDriver<MobileElement> driver) {
		try {
			return driver.findElementById("com.zimplistic.rotimaticmobile:id/edtEmailAddress");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public WebElement getPassword(AndroidDriver<MobileElement> driver) {
		try {
			return driver.findElementById("com.zimplistic.rotimaticmobile:id/edtPassword");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Login button
	public WebElement getLoginButton(AndroidDriver<MobileElement> driver) {
		try {
			return driver.findElementByXPath("//android.widget.TextView[@text='Sign In']");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// hamburger menu icon
	public WebElement getHamburgerMenu(AndroidDriver<MobileElement> driver) {
		try {
			return driver.findElementByClassName("android.widget.ImageButton");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	
	
	

}
