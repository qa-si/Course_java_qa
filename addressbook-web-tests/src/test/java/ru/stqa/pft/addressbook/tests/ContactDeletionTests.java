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
			.withFirstname("Ivan")
			.withLastname("Ivanov")
			.withEmail("iv@an.ov").
			withGroup(group.getName());

	@BeforeMethod
	public void ensurePreconditions() {
		if (app.db().contacts().size() == 0) {
			app.goTo().groupPage();
			app.group().checkGroupExisting(group.getName());
			app.goTo().contactPage();
			app.contact().create(contact);
			app.goTo().contactPage();
			contact.withId(app.contact().returnIdContact(contact));
		}
	}

	@Test
	public void testContactDeletion() {
		Contacts before = app.db().contacts();
		ContactData deletedContact = before.iterator().next();
		app.contact().delete(deletedContact);
		app.acceptAlert();
		app.goTo().contactPage();
		Contacts after = app.db().contacts();
		assertThat(after, equalTo(before.withoutAdded(deletedContact)));
	}
}
