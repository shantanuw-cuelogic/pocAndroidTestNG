package com.zimplistic.rotimatic.setup;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface Constants {

	boolean isSmokeTest = false;

	String ROOT_DIR = "D:\\Cue - Projects\\Automation_Workspace\\Screenshots\\";
	String TEST_FOLDER_NAME = new SimpleDateFormat("d_MMM_yyyy_HH_mm").format(new Date());

	String FOLDER_LOGIN = "Login_test";
	String FOLDER_FACEBOOK = "Facebook_login_test";
	String FOLDER_SPLASH = "Splash_screen_test";
	String FOLDER_CONFIGURATION = "Configure_test";
	String FOLDER_CHAT = "Chat_test";
	String FOLDER_NETWORKTEST = "No_Network_test";
	String FOLDER_RECIPE = "Recipe_test";
	String FOLDER_REMOTE_CONTROL = "RC_test";

}
