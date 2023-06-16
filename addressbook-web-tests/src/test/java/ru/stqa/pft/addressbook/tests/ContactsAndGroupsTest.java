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
			app.goTo().groupPage();
			app.group().create(new GroupData().withName(groupName));
		}
		if (app.db().contacts().size() == 0) {
			app.goTo().contactPage();
			app.contact().create(new ContactData().withFirstname(contactName));
		}
		groups = app.db().groups();
		contacts = app.db().contacts();
		app.goTo().contactPage();
		contacts = app.contact().setGroupsToContacts(contacts, groups);
	}

	@Test()
	public void testContactAddToGroup() {
		app.contact().checkContactsInGroupAndCreateNewContactIfAllContactsInAllGroups(contacts, groups, contactName);
		ContactData contact = contacts.iterator().next();
		app.goTo().groupPage();
		app.goTo().contactPage();
		GroupData addGroup = app.contact().addContactToGroupIfItIsNotThere(contact, groups);
		app.goTo().pageByUrl(app.returnContactPageUrl());
		app.contact().openContactListInGroup(addGroup);
		assertThat(true, equalTo(app.contact().checkContactIsVisible(contact)));
	}

	@Test()
	public void testContactRemoveFromGroup() {
		app.contact().checkContactsInGroupsAndAddContactToGroupUfAllContactsWithoutGroups(contacts, groups);
		app.contact().checkContactInGroupAndRemoveIfIsThere(groups);
	}


}
