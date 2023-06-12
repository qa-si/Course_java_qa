package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactsAndGroupsTest extends TestBase {

	private String groupName = "newTestGroup";
	private String contactName = "newTestContact";
	Groups groups;
	Contacts contacts;

	@BeforeTest
	public void preparation() {
		if (app.db().groups().size() == 0) {
			app.group().create(new GroupData().withName(groupName));
		}
		if (app.db().contacts().size() == 0) {
			app.contact().create(new ContactData().withFirstname(contactName));
		}
		groups = app.db().groups();
		contacts = app.db().contacts();
		contacts = app.contact().setGroupsToContacts(contacts, groups);
	}

	@Test()
	public void testContactAddToGroup() {
		for (int i = 0; i < contacts.size(); i++) {
			ContactData contact = contacts.iterator().next();
			if (contact.getGroups().size() != groups.size()) {
				break;
			} else if (i == contacts.size() - 1) {
				app.contact().create(new ContactData().withFirstname(contactName));
			}
		}
		ContactData contact = contacts.iterator().next();
		GroupData addGroup = new GroupData();
		app.goTo().groupPage();
		app.goTo().contactPage();
		for (GroupData group : groups) {
			if (!contact.getGroups().contains(group)) {
				app.contact().selectContactById(contact.getId());
				app.contact().addContactToGroup(group);
				contact.inGroup(group);
				addGroup = group;
				break;
			}
		}
		app.goTo().contactPage();
		app.contact().openContactListInGroup(addGroup);
		assertThat(true, equalTo(app.contact().checkContactIsVisible(contact)));
	}

	@Test()
	public void testContactRemoveFromGroup() {
		GroupData groupForRemoveContact;
		for (int i = 0; i < contacts.size(); i++) {
			ContactData contact = contacts.iterator().next();
			if (contact.getGroups().size() != 0) {
				break;
			} else if (i == contacts.size() - 1) {
				groupForRemoveContact = groups.iterator().next();
				app.goTo().contactPage();
				app.contact().selectContactById(contact.getId());
				app.contact().addContactToGroup(groupForRemoveContact);
			}
		}
		for (int i = 0; i < groups.size(); i++) {
			groupForRemoveContact = groups.iterator().next();
			app.contact().openContactListInGroup(groupForRemoveContact);
			if (app.contact().all().size() > 0) {
				ContactData contact = app.contact().all().iterator().next();
				app.contact().removeContactFromGroup(contact, groupForRemoveContact);
				app.contact().returnToGroupClick(groupForRemoveContact);
				assertThat(false, equalTo(app.contact().checkContactIsVisible(contact)));
				break;
			}
		}
	}
}
