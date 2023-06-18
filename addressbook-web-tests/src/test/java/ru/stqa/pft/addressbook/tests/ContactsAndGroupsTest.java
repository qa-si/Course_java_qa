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

	ContactData contactForAddingToGroup;
	GroupData groupForAddingContact;
	Groups groups;
	Contacts contacts;

	@BeforeTest
	public void preparation() {
		String groupName = "newTestGroup " + System.currentTimeMillis();
		String contactName = "newTestContact " + System.currentTimeMillis();
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
		contactForAddingToGroup = app.contact().setContactForAddingToGroup(contacts, groups);
		groupForAddingContact = app.contact().setGroupForAddingContact(contactForAddingToGroup, groups);
		app.goTo().pageByUrl(app.returnContactPageUrl());
	}

	@Test()
	public void testContactAddToGroup() {
		app.contact().addContactToGroupIfItIsNotThere(contactForAddingToGroup, groupForAddingContact);
		app.goTo().pageByUrl(app.returnContactPageUrl());
		app.contact().openContactListInGroup(groupForAddingContact);
		assertThat(true, equalTo(app.contact().checkContactIsVisible(contactForAddingToGroup)));
	}

	@Test()
	public void testContactRemoveFromGroup() {
		app.contact().checkContactsInGroupsAndAddContactToGroupUfAllContactsWithoutGroups(contacts, groups);
		app.contact().checkContactInGroupAndRemoveIfIsThere(groups);
	}


}
