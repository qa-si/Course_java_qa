package ru.stqa.pft.mantis.tests;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import static org.openqa.selenium.remote.Browser.CHROME;


public class TestBase {
	protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", CHROME.browserName()));

	@BeforeSuite
	public void setUp() throws Exception {
		app.init();
		app.ftp().upload(new File("src/test/resources/config_inc.php"),
				"config_inc.php",
				"config_inc.php.bac");
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		app.ftp().restore("config_inc.php.bac", "config_inc.php");
		app.stop();
	}

	boolean isIssueOpen(int issueId) throws RemoteException, MalformedURLException, ServiceException {
		return !app.soap().getIssue(issueId).getStatus().getName().equals("resolved");
	}

	@BeforeTest(groups = "SoapTest")
	public void skipIfNotFixed(int issueId) throws RemoteException, MalformedURLException, ServiceException {
		if (isIssueOpen(issueId)) {
			throw new SkipException("Ignored because of issue " + issueId);
		}
	}
}
