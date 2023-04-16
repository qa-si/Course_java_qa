package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.LoginData;

public class SessionHelper extends HelperBase{

	public SessionHelper(WebDriver wd) {
		super(wd);
	}

	public void login(LoginData loginData) {
		type(By.name("user"), loginData.getUsername());
		type(By.name("pass"), loginData.getPassword());
		click(By.xpath("//input[@value='Login']"));
	}
}