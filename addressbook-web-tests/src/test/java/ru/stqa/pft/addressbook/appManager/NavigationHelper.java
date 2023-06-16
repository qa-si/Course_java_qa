package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.System.getProperty;

public class NavigationHelper extends HelperBase {
	public NavigationHelper(WebDriver wd) {
		super(wd);
	}

	public void groupPage() {
		if (isElementPresent(By.tagName("h1"))
				&& wd.findElement(By.tagName("h1")).getText().equals("Groups")
				&& isElementPresent(By.name("new"))) {
			return;
		}
		click(By.linkText("groups"));
	}

	public void contactPage() {
		if (isElementPresent(By.id("maintable"))) {
			return;
		}
		click(By.xpath("//*[text()='home']"));
	}

	public void pageByUrl(String url) {
		if (!wd.getCurrentUrl().equals(url)) {
			wd.get(url);
		}
	}
}