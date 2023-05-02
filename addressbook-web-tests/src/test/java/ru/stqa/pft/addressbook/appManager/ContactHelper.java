package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

	public ContactHelper(WebDriver wd) {
		super(wd);
	}

	public void submitContactCreation() {
		wd.findElement(By.name("submit")).click();
	}

	public void fillContactForm(ContactData contactData, boolean creation) {
		type(By.name("firstname"), contactData.getName());
		type(By.name("lastname"), contactData.getLastname());
		type(By.name("email"), contactData.getEmail());

		if (creation) {
			new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
		} else {
			Assert.assertFalse(isElementPresent(By.name("new_group")));
		}
	}

	public void initContactCreation() {
		click(By.linkText("add new"));
	}

	public void submitContactModification() {
		click(By.name("update"));
	}

	public void selectContactByEmail(String email) {
		click(By.xpath("//*[@accept='" + email + "']"));
	}

	public void deleteSelectedContacts() {
		click(By.xpath("//input[@value='Delete']"));
	}

	public void initContactModification(String email) {
		click(By.xpath("//*[@accept='" + email + "']/../..//*[@title='Edit']"));
	}

	public void createContact(ContactData contact) {
		initContactCreation();
		fillContactForm(contact, true);
		submitContactCreation();
	}

	public boolean isThereAContact(String email) {
		return (isElementPresent(By.xpath("//*[@accept='" + email + "']")));
	}

	public List<ContactData> getContactList() {
		List<ContactData> contacts = new ArrayList<ContactData>();
		List<WebElement> elements = wd.findElements(By.name("entry"));
		for (WebElement element : elements) {
			String name = element.findElement(By.xpath("td[3]")).getText();
			String secondName = element.findElement(By.xpath("td[2]")).getText();
			int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
			ContactData contact = new ContactData(id, name, secondName, null, null);
			contacts.add(contact);
		}
		return contacts;
	}
}
