package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov", "test1");

	@Test
	public void testContactDeletion(){
		app.getNavigationHelper().gotoContactPage();
		if (!app.getContactHelper().isThereAContact(contact.getEmail())){
			app.getContactHelper().createContact(contact);
		}
		app.getNavigationHelper().gotoContactPage();
		app.getContactHelper().selectContactByEmail(contact.getEmail());
		app.getContactHelper().deleteSelectedContacts();
		app.acceptAlert();
	}
}
