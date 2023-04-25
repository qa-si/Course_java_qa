package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov", "group1");

	@Test
	public void testContactDeletion(){
		app.getContactHelper().initContactCreation();
		app.getContactHelper().fillContactForm(contact, true);
		app.getContactHelper().submitContactCreation();

		app.getNavigationHelper().gotoContactPage();
		app.getContactHelper().selectContactByEmail(contact.getEmail());
		app.getContactHelper().deleteSelectedContacts();
		app.acceptAlert();
	}
}
