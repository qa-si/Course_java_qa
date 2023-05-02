package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactModificationTests extends TestBase {

	GroupData group = new GroupData("test1", null, null);
	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov", group.getName());
	ContactData updatedContact = new ContactData("Ilya", "Petrov", "e@mail.ru", null);

	@Test
	public void testContactModification() {
		app.getNavigationHelper().gotoGroupPage();
		app.getGroupHelper().checkGroupExisting(group);
		app.getNavigationHelper().gotoContactPage();
		if (!app.getContactHelper().isThereAContact(contact.getEmail())) {
			app.getContactHelper().createContact(contact);
		}
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> before = app.getContactHelper().getContactList();
		app.getContactHelper().selectContactByEmail(contact.getEmail());
		app.getContactHelper().initContactModification(contact.getEmail());
		app.getContactHelper().fillContactForm(updatedContact, false);
		app.getContactHelper().submitContactModification();
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> after = app.getContactHelper().getContactList();
		Assert.assertEquals(after.size(), before.size());
	}
}
