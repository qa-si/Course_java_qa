package ru.stqa.pft.addressbook.appManager;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.LoginData;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
	private NavigationHelper navigationHelper;
	private GroupHelper groupHelper;
	private SessionHelper sessionHelper;
	private ContactHelper contactHelper;
	LoginData user = new LoginData("admin", "secret");
	WebDriver wd;

	public void init() {
		wd = new FirefoxDriver();
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
}
