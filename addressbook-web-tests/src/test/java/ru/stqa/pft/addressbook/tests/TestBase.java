package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appManager.ApplicationManager;

import static org.openqa.selenium.remote.Browser.CHROME;

public class TestBase {

	protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", CHROME.browserName()));

	@BeforeSuite
	public void setUp() throws Exception {
		app.init();
	}

	@AfterSuite
	public void tearDown() throws Exception {
		app.stop();
	}

}
