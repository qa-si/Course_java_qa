package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.LoginData;

public class SessionHelper {

	private WebDriver wd;

	public SessionHelper(WebDriver wd) {
		this.wd = wd;
	}


	public void login(LoginData loginData) {
		wd.findElement(By.name("user")).clear();
		wd.findElement(By.name("user")).sendKeys(loginData.getUsername());
		wd.findElement(By.name("pass")).clear();
		wd.findElement(By.name("pass")).sendKeys(loginData.getPassword());
		wd.findElement(By.xpath("//input[@value='Login']")).click();
	}
}
