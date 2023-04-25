package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.LoginData;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.Browser.*;

public class ApplicationManager {
	private NavigationHelper navigationHelper;
	private GroupHelper groupHelper;
	private SessionHelper sessionHelper;
	private ContactHelper contactHelper;
	LoginData user = new LoginData("admin", "secret");
	WebDriver wd;
	private final String browser;

	public ApplicationManager(String browser) {
		this.browser = browser;
	}

	public void init() {
		if (Objects.equals(browser, FIREFOX.browserName())) {
			wd = new FirefoxDriver();
		} else if (Objects.equals(browser, CHROME.browserName())) {
			wd = new ChromeDriver();
		} else if (Objects.equals(browser, EDGE.browserName())){
			wd = new EdgeDriver();
		}
		wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		wd.get("http://localhost/addressbook/");
		groupHelper = new GroupHelper(wd);
		navigationHelper = new NavigationHelper(wd);
		sessionHelper = new SessionHelper(wd);
		contactHelper = new ContactHelper(wd);
		sessionHelper.login(user);
	}

	public void stop() {
		wd.quit();
	}

	public void logout() {
		wd.findElement(By.linkText("Logout")).click();
	}

	public GroupHelper getGroupHelper() {
		return groupHelper;
	}

	public NavigationHelper getNavigationHelper() {
		return navigationHelper;
	}

	public ContactHelper getContactHelper() {
		return contactHelper;
	}

	public void acceptAlert() {
		wd.switchTo().alert().accept();
	}
}
