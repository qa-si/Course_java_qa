package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

	GroupData group = new GroupData("test1", null, null);
	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov", group.getName());

	@Test
	public void testContactDeletion() {
		app.getNavigationHelper().gotoGroupPage();
		app.getGroupHelper().checkGroupExisting(group);
		app.getNavigationHelper().gotoContactPage();
		if (!app.getContactHelper().isThereAContact(contact.getEmail())) {
			app.getContactHelper().createContact(contact);
		}
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> before = app.getContactHelper().getContactList();
		app.getContactHelper().selectContactByEmail(contact.getEmail());
		app.getContactHelper().deleteSelectedContacts();
		app.acceptAlert();
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> after = app.getContactHelper().getContactList();
		Assert.assertEquals(after.size(), before.size() - 1);
	}
}
