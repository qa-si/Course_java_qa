package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
//		attach(By.name("photo"), contactData.getPhoto());

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

	public void selectContactById(int id) {
		wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
	}

	public void deleteSelectedContacts() {
		click(By.xpath("//input[@value='Delete']"));
	}

	public void initContactModification(int id) {
		wd.findElement(By.cssSelector(String.format("[href='edit.php?id=%s']", id))).click();
	}

	public void create(ContactData contact) {
		initContactCreation();
		fillContactForm(contact, true);
		submitContactCreation();
		contactCash = null;
	}

	public void modify(ContactData contact) {
		selectContactById(contact.getId());
		initContactModification(contact.getId());
		fillContactForm(contact, false);
		submitContactModification();
		contactCash = null;
	}

	public void delete(ContactData contact) {
		selectContactById(contact.getId());
		deleteSelectedContacts();
		contactCash = null;
	}

	public List<ContactData> list() {
		List<ContactData> contacts = new ArrayList<>();
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

	public int count() {
		return wd.findElements(By.name("entry")).size();
	}

	private Contacts contactCash = null;

	public Contacts all() {
		if (contactCash != null) {
			return new Contacts(contactCash);
		}
		contactCash = new Contacts();
		List<WebElement> rows = wd.findElements(By.name("entry"));
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.xpath("//td"));
			int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
			String lastName = cells.get(1).getText();
			String firstName = cells.get(2).getText();
			String address = cells.get(3).getText();
			String allEmails = cells.get(4).getText();
			String allPhones = cells.get(5).getText();
			contactCash.add(new ContactData()
					.withId(id)
					.withName(firstName)
					.withLastname(lastName)
					.withAddress(address)
					.withAllEmails(allEmails)
					.withAllPhones(allPhones));
		}
		return new Contacts(contactCash);
	}

	public ContactData infoFromEditForm(ContactData contact) {
		initContactModification(contact.getId());
		String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
		String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
		String address = wd.findElement(By.name("address")).getAttribute("value");
		String email = wd.findElement(By.name("email")).getAttribute("value");
		String email2 = wd.findElement(By.name("email2")).getAttribute("value");
		String email3 = wd.findElement(By.name("email3")).getAttribute("value");
		String home = wd.findElement(By.name("home")).getAttribute("value");
		String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
		String work = wd.findElement(By.name("work")).getAttribute("value");
		String allEmails = mergeEmails(email, email2, email3);
		wd.navigate().back();
		return new ContactData()
				.withId(contact.getId())
				.withName(firstName)
				.withLastname(lastName)
				.withHomePhone(home)
				.withMobilePhone(mobile)
				.withWorkPhone(work)
				.withAddress(address)
				.withAllEmails(allEmails);
	}

	public int returnIdContact(ContactData contact) {
		WebElement element = wd.findElement(By.xpath("//*[text()='" + contact.getEmail() + "']/../..//input"));
		return Integer.parseInt(element.getAttribute("value"));
	}

	private String mergeEmails(String... email) {
		return Arrays.asList(email)
				.stream().filter((s) -> !s.equals(""))
				.collect(Collectors.joining("\n"));
	}

}
