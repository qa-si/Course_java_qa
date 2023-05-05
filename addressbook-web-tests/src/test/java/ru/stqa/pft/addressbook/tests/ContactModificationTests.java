package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

	GroupData group = new GroupData("test1", null, null);
	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov", group.getName());

	@Test
	public void testContactModification() {
		app.getNavigationHelper().gotoGroupPage();
		app.getGroupHelper().checkGroupExisting(group);
		app.getNavigationHelper().gotoContactPage();
		if (!app.getContactHelper().isThereAContact(contact.getEmail())) {
			app.getContactHelper().createContact(contact);
		}
		app.getNavigationHelper().gotoContactPage();
		contact.setId(app.getContactHelper().returnIdContact(contact));
		List<ContactData> before = app.getContactHelper().getContactList();
		ContactData updatedContact = new ContactData(contact.getId(), "Ilya", "Petrov", "e@mail.ru", null);
		app.getContactHelper().selectContactByEmail(contact.getEmail());
		app.getContactHelper().initContactModification(contact.getEmail());
		app.getContactHelper().fillContactForm(updatedContact, false);
		app.getContactHelper().submitContactModification();
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> after = app.getContactHelper().getContactList();
		Assert.assertEquals(after.size(), before.size());

		before.remove(contact);
		before.add(updatedContact);
		Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
		before.sort(byId);
		after.sort(byId);
		Assert.assertEquals(before, after);
	}
}
