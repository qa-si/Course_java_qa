package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactsAndGroupsTest extends TestBase{

	@Test()
	public void testContactAddToGroup() {
		Groups groups = app.db().groups();
		Contacts contacts = app.db().contacts();
		contacts = app.contact().setGroupsToContacts(contacts, groups);
		ContactData contact = contacts.iterator().next();
		GroupData addGroup = new GroupData();
		for (GroupData group: groups){
			if (!contact.getGroups().contains(group)){
				app.contact().openContactListInGroup(contact.getGroups().iterator().next());
				app.contact().selectContactById(contact.getId());
				app.contact().addContactToGroup(group);
				contact.inGroup(group);
				Contacts groupContacts = group.getContacts();
				group.withContacts(groupContacts);
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
		Groups groups = app.db().groups();
		for (GroupData group: groups) {
			app.contact().openContactListInGroup(group);
			if (app.contact().all().size() > 0) {
				ContactData contact = app.contact().all().iterator().next();
				app.contact().removeContactFromGroup(contact, group);
				app.goTo().contactPage();
				app.contact().openContactListInGroup(group);
				assertThat(false, equalTo(app.contact().checkContactIsVisible(contact)));
				break;
			}
		}
	}
}
