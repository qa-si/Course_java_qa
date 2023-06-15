package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.stqa.pft.addressbook.model.LoginData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.Browser.*;

public class ApplicationManager {
	private final Properties properties;
	WebDriver wd;
	private NavigationHelper navigationHelper;
	private GroupHelper groupHelper;
	private SessionHelper sessionHelper;
	private ContactHelper contactHelper;
	LoginData user = new LoginData("admin", "secret");
	private final String browser;
	private DbHelper dbHelper;

	public ApplicationManager(String browser) {
		this.browser = browser;
		properties = new Properties();
	}

	public void init() throws IOException {
		String target = System.getProperty("target", "local");
		properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

		dbHelper = new DbHelper();

		if ("".equals(properties.getProperty("selenium.server"))) {
			if (Objects.equals(browser, FIREFOX.browserName())) {
				wd = new FirefoxDriver();
			} else if (Objects.equals(browser, CHROME.browserName())) {
				wd = new ChromeDriver();
			} else if (Objects.equals(browser, EDGE.browserName())) {
				wd = new EdgeDriver();
			}
		} else {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win11")));
			wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
		}

		wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		wd.get(properties.getProperty("web.baseUrl"));
		groupHelper = new GroupHelper(wd);
		navigationHelper = new NavigationHelper(wd);
		sessionHelper = new SessionHelper(wd);
		contactHelper = new ContactHelper(wd);
		sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
	}

	public void stop() {
		wd.quit();
	}

	public void logout() {
		wd.findElement(By.linkText("Logout")).click();
	}

	public NavigationHelper goTo() {
		return navigationHelper;
	}

	public GroupHelper group() {
		return groupHelper;
	}

	public ContactHelper contact() {
		return contactHelper;
	}

	public DbHelper db() {
		return dbHelper;
	}

	public void acceptAlert() {
		wd.switchTo().alert().accept();
	}
}
