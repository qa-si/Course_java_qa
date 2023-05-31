package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ry.stqa.pft.mantis.appmanager.ApplicationManager;

import static org.openqa.selenium.remote.Browser.CHROME;


public class TestBase {
	protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", CHROME.browserName()));

	@BeforeSuite
	public void setUp() throws Exception {
		app.init();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		app.stop();
	}
}
