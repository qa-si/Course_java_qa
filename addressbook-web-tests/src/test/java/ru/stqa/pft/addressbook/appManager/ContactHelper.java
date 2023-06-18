package ru.stqa.pft.addressbook.appManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactHelper extends HelperBase {

	public ContactHelper(WebDriver wd) {
		super(wd);
	}

	public void submitContactCreation() {
		wd.findElement(By.name("submit")).click();
	}

	public void fillContactForm(ContactData contactData, boolean creation) {
		type(By.name("firstname"), contactData.getFirstname());
		type(By.name("lastname"), contactData.getLastname());
		type(By.name("email"), contactData.getEmail());

		if (creation) {
			if (contactData.getGroups().size() > 0) {
				Assert.assertTrue(contactData.getGroups().size() == 1);
				new Select(wd.findElement(By.name("new_group")))
						.selectByVisibleText(contactData.getGroups().iterator().next().getName());
			}
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
		wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
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
					.withFirstname(name)
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
					.withFirstname(firstName)
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
				.withFirstname(firstName)
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

	public String selectRandomGroup() {
		wd.findElement(By.xpath("//select[@name='new_group']")).click();
		int randomGroupNumber = (int) (Math.random() * wd.findElements(By.xpath("//select[@name='new_group']/option")).size());
		String group = wd.findElements(By.xpath("//select[@name='new_group']/option")).get(randomGroupNumber).getText();
		wd.findElements(By.xpath("//select[@name='new_group']/option")).get(randomGroupNumber).click();
		return group;
	}

	public void openContactListInGroup(GroupData group) {
		new Select(wd.findElement(By.name("group"))).selectByValue(group.getId().toString());
	}

	public void selectContact(ContactData contact) {
		wd.findElement(By.id(String.valueOf(contact.getId()))).click();
	}

	public void addContactToGroup(GroupData group) {
		new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
		wd.findElement(By.name("add")).click();
	}

	public boolean checkContactIsVisible(ContactData contact) {
		return wd.findElements(By.cssSelector("input[value='" + contact.getId() + "']")).size() > 0;
	}

	public Contacts setGroupsToContacts(Contacts contacts, Groups groups) {
		for (GroupData group : groups) {
			openContactListInGroup(group);
			for (ContactData contact : contacts) {
				if (checkContactIsVisible(contact)) {
					contact.inGroup(group);
				}
			}
		}
		return contacts;
	}

	public ContactData setContactForAddingToGroup(Contacts contacts, Groups groups) {
		ContactData contactForAddingToGroup = null;
		for (GroupData group : groups) {
			openContactListInGroup(group);
			if (!all().equals(contacts)) {
				for (ContactData contact : contacts) {
					if (!checkContactIsVisible(contact)) {
						contactForAddingToGroup = contact;
						break;
					}
				}
			}
			if (contactForAddingToGroup != null) {
				break;
			}
		}
		if (contactForAddingToGroup == null) {
			contactForAddingToGroup = new ContactData()
					.withFirstname("new name " + System.currentTimeMillis());
			create(contactForAddingToGroup);
			click(By.xpath("//*[text()='home']"));
			contactForAddingToGroup.withId(getIdContactByName(contactForAddingToGroup));
		}
		return contactForAddingToGroup;
	}

	public int getIdContactByName(ContactData contact) {
		return Integer.parseInt(wd.findElement(By.xpath(String.format("//td[text()=%s]..//td[1]/input", contact.getFirstname()))).getAttribute("value"));
	}

	public GroupData setGroupForAddingContact(ContactData contact, Groups groups) {
		GroupData groupForAdding = null;
		for (GroupData group : groups) {
			openContactListInGroup(group);
			if (!checkContactIsVisible(contact)) {
				groupForAdding = group;
				break;
			}
		}
		return groupForAdding;
	}

	public void removeContactFromGroup(ContactData contact, GroupData group) {
		openContactListInGroup(group);
		selectContactById(contact.getId());
		wd.findElement(By.name("remove")).click();
	}

	public void returnToGroupClick(GroupData group) {
		click(By.xpath(String.format("//*[text()='group page \"%s\"']", group.getName())));
	}

	public void checkContactsInGroupAndCreateNewContactIfAllContactsInAllGroups(Contacts contacts, Groups groups, String contactName) {
		for (int i = 0; i < contacts.size(); i++) {
			ContactData contact = contacts.iterator().next();
			if (contact.getGroups().size() < groups.size()) {
				break;
			} else if (i == contacts.size() - 1) {
				create(new ContactData().withFirstname(contactName));
			}
		}
	}


	public GroupData addContactToGroupIfItIsNotThere(ContactData contact, Groups groups) {
		GroupData addGroup = new GroupData();
		for (GroupData group : groups) {
			if (!contact.getGroups().contains(group)) {
				selectContactById(contact.getId());
				addContactToGroup(group);
				contact.inGroup(group);
				addGroup = group;
				break;
			}
		}
		return addGroup;
	}

	public void addContactToGroupIfItIsNotThere(ContactData contact, GroupData group) {
		selectContactById(contact.getId());
		addContactToGroup(group);
	}

	public void checkContactInGroupAndRemoveIfIsThere(Groups groups) {
		GroupData groupForRemoveContact;
		for (int i = 0; i < groups.size(); i++) {
			groupForRemoveContact = groups.iterator().next();
			openContactListInGroup(groupForRemoveContact);
			if (all().size() > 0) {
				ContactData contact = all().iterator().next();
				removeContactFromGroup(contact, groupForRemoveContact);

				returnToGroupClick(groupForRemoveContact);
				assertThat(false, equalTo(checkContactIsVisible(contact)));
				break;
			}
		}
	}

	public void checkContactsInGroupsAndAddContactToGroupUfAllContactsWithoutGroups(Contacts contacts, Groups groups) {
		NavigationHelper goTo = new NavigationHelper(wd);
		GroupData groupForRemoveContact;
		for (int i = 0; i < contacts.size(); i++) {
			ContactData contact = contacts.iterator().next();
			if (contact.getGroups().size() != 0) {
				break;
			} else if (i == contacts.size() - 1) {
				groupForRemoveContact = groups.iterator().next();
				goTo.contactPage();
				selectContactById(contact.getId());
				addContactToGroup(groupForRemoveContact);
			}
		}
	}
}
