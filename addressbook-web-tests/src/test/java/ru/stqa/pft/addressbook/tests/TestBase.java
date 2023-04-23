package ru.stqa.pft.addressbook.tests;


import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.appManager.ApplicationManager;

import static org.openqa.selenium.remote.Browser.*;

public class TestBase {

	protected final ApplicationManager app = new ApplicationManager(EDGE.browserName());

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		app.init();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		app.stop();
	}

}
