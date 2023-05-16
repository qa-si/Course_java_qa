package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {
	GroupData group = new GroupData().withName("test1");
	private File photo = new File("src/test/resources/OK.png");
	ContactData contact = new ContactData()
			.withName("Fill")
			.withLastname("Jones")
			.withEmail("email")
			.withGroup(group.getName())
			.withPhoto(photo);

	@Test
	public void testContactCreation() throws Exception {
		app.goTo().groupPage();
		app.group().checkGroupExisting(group);
		app.goTo().contactPage();
		Contacts before = app.contact().all();
		app.contact().initContactCreation();
		app.contact().fillContactForm(contact, true);
		app.contact().submitContactCreation();
		app.goTo().contactPage();
		Contacts after = app.contact().all();
		assertEquals(after.size(), before.size() + 1);
		assertThat(app.contact().count(), equalTo(before.size() + 1));

		contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
		assertThat(after, equalTo(before.withAdded(contact)));
	}
}