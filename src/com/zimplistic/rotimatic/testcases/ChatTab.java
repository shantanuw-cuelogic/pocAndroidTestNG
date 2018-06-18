package com.zimplistic.rotimatic.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.zimplistic.rotimatic.setup.BaseSetup;

public class ChatTab extends BaseSetup {

	@Test(priority = 0)
	private void chatScreen() throws Exception {
		setup();
		signIn("qa@android.com", "asdf");
		Thread.sleep(15000);

		// hamburger menu icon
		ad.findElementByClassName("android.widget.ImageButton").click();

		// settings tab
		ad.findElementById("com.zimplistic.rotimaticmobile:id/tvSettings").click();

		String username = ad.findElementById("com.zimplistic.rotimaticmobile:id/tvUserName").getText();
		System.out.println("Welcome - " + username);

		String actual = "Hello " + username + ", How may I help you today?";

		ad.navigate().back();
		
		// Chat tab

		ad.findElementByName("Chat").click();
		Thread.sleep(10000);
		getScreenshot(ad, FOLDER_CHAT);

		// Hello Android user, How may I help you today?

		String expected = ad.findElementById("com.zimplistic.rotimaticmobile:id/chat_item_content_text").getText();

		//String expected = ad.findElementById("com.zimplistic.rotimaticmobile:id/chat_item_layout_content").getText();
		
		
		System.out.println("Auto wc = " + expected);

		if (actual.equalsIgnoreCase(expected)) {
			System.out.println("Auto WC message test Passed");
		} else
			System.out.println("Auto WC message test Failed");

		// Thank you for reaching out to Rotimatic Support Team. Our support
		// hours are weekdays from 9 am - 9 pm, and weekends from 11 am - 1 pm
		// (SGT). We will get back to you as soon as possible.

		swipingVerticallyUp();
		swipingVerticallyUp();
		swipingVerticallyUp();

		getScreenshot(ad, FOLDER_CHAT);

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvLoadEarlierMessages")).isEmpty())
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvLoadEarlierMessages").click();

		getScreenshot(ad, FOLDER_CHAT);

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/tvLoadEarlierMessages")).isEmpty())
			ad.findElementById("com.zimplistic.rotimaticmobile:id/tvLoadEarlierMessages").click();

		getScreenshot(ad, FOLDER_CHAT);

		swipingVerticallyDown();

		swipingVerticallyDown();

		ad.navigate().back();
		
		signOut();
	}

	/*
	@Test(priority = 1)
	private void uploadMedia() throws Exception {

		ad.findElementByName("Chat").click();
		
		// Upload media attach button
		ad.findElementById("com.zimplistic.rotimaticmobile:id/menuAttach").click();

		// Camera

		if (!ad.findElements(By.id("com.zimplistic.rotimaticmobile:id/linCamera")).isEmpty())
			ad.findElementById("com.zimplistic.rotimaticmobile:id/linCamera").click();

		// com.asus.camera:id/button_capture

		// Permission Allow access required in O.S. 6 and above
		if (!ad.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).isEmpty())
			ad.findElementByName("Allow").click();

		if (!ad.findElements(By.id("com.android.packageinstaller:id/permission_allow_button")).isEmpty())
			ad.findElementByName("Allow").click();

		ad.navigate().back();
		
		ad.navigate().back();
		
		signOut();

		// Send new text message
		// ad.findElementById("com.zimplistic.rotimaticmobile:id/etChatMessage").sendKeys("Hello
		// test");

		// ad.findElementById("com.zimplistic.rotimaticmobile:id/btnSend").click();
	}
	*/
}
