package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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

	public void selectContactById(int id) {
		wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
	}

	public void deleteSelectedContacts() {
		click(By.xpath("//input[@value='Delete']"));
	}

	public void initContactModification(int id) {
		click(By.cssSelector("[href='edit.php?id=" + id + "']"));
	}

	public void create(ContactData contact) {
		initContactCreation();
		fillContactForm(contact, true);
		submitContactCreation();
	}

	public void modify(ContactData contact) {
		selectContactById(contact.getId());
		initContactModification(contact.getId());
		fillContactForm(contact, false);
		submitContactModification();
	}

	public void delete(ContactData contact) {
		selectContactById(contact.getId());
		deleteSelectedContacts();
	}

	public List<ContactData> list() {
		List<ContactData> contacts = new ArrayList<ContactData>();
		List<WebElement> elements = wd.findElements(By.name("entry"));
		for (WebElement element : elements) {
			String name = element.findElement(By.xpath("td[3]")).getText();
			String secondName = element.findElement(By.xpath("td[2]")).getText();
			int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
			ContactData contact = new ContactData()
					.withId(id)
					.withName(name)
					.withLastname(secondName);
			contacts.add(contact);
		}
		return contacts;
	}

	public Contacts all() {
		Contacts contacts = new Contacts();
		List<WebElement> elements = wd.findElements(By.name("entry"));
		for (WebElement element : elements) {
			String name = element.findElement(By.xpath("td[3]")).getText();
			String secondName = element.findElement(By.xpath("td[2]")).getText();
			int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
			ContactData contact = new ContactData()
					.withId(id)
					.withName(name)
					.withLastname(secondName);
			contacts.add(contact);
		}
		return contacts;
	}

	public int returnIdContact(ContactData contact) {
		WebElement element = wd.findElement(By.xpath("//*[text()='" + contact.getEmail() + "']/../..//input"));
		return Integer.parseInt(element.getAttribute("value"));
	}
}
