package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

	GroupData group = new GroupData().withName("test1");
	ContactData contact = new ContactData()
			.withName("Ivan")
			.withLastname("Ivanov")
			.withEmail("iv@an.ov").
			withGroup(group.getName());

	@BeforeMethod
	public void ensurePreconditions() {
		app.goTo().groupPage();
		app.group().checkGroupExisting(group);
		app.goTo().contactPage();
		if (!app.contact().list().contains(contact)) {
			app.contact().create(contact);
		}
		app.goTo().contactPage();
	}

	@Test
	public void testContactDeletion() {
		contact.withId(app.contact().returnIdContact(contact));
		Contacts before = app.contact().all();
		ContactData deletedContact = before.iterator().next();
		app.contact().delete(contact);
		app.acceptAlert();
		app.goTo().contactPage();
		Contacts after = app.contact().all();
		assertThat(after.size(), equalTo(before.size() - 1));
		assertThat(after, equalTo(before.withoutAdded(deletedContact)));
	}
}
