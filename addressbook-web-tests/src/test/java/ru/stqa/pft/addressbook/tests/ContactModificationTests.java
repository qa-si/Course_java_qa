package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

	GroupData group = new GroupData().withName("test1");

	@BeforeMethod
	public void ensurePreconditions() {
		app.goTo().groupPage();
		app.group().checkGroupExisting(group.getName());
		app.goTo().contactPage();
		if (app.db().contacts().size() == 0) {
			app.contact().create(new ContactData()
					.withFirstname("Ivan")
					.withLastname("Ivanov")
					.withEmail("iv@an.ov")
					.withGroup(group.getName()));
		}
		app.goTo().contactPage();
	}

	@Test
	public void testContactModification() {
		Contacts before = app.db().contacts();
		ContactData modifyContact = before.iterator().next();
		ContactData contact = new ContactData()
				.withId(modifyContact.getId())
				.withFirstname("Ilya")
				.withLastname("Petrov")
				.withEmail("e@mail.ru");
		app.contact().modify(contact);
		app.goTo().contactPage();
		Contacts after = app.db().contacts();
		assertThat(after, equalTo(before.withoutAdded(modifyContact).withAdded(contact)));
	}
}
