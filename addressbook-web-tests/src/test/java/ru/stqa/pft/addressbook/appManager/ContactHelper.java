package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

	public ContactHelper(WebDriver wd) {
		super(wd);
	}

	public void submitContactCreation() {
		wd.findElement(By.name("submit")).click();
	}

	public void fillContactForm(ContactData contactData) {
		type(By.name("firstname"), contactData.getName());
		type(By.name("lastname"), contactData.getLastname());
		type(By.name("nickname"), contactData.getNickname());
		type(By.name("title"), contactData.getTitle());
		type(By.name("company"), contactData.getCompany());
		type(By.name("address"), contactData.getAddress());
		type(By.name("mobile"), contactData.getMobileNumber());
		type(By.name("email"), contactData.getEmail());
	}

	public void initContactCreation() {
		click(By.linkText("add new"));
	}
}
