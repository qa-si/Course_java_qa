package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov", "test1");
	ContactData updatedContact = new ContactData("Ilya", "Petrov", "e@mail.ru", null);

	@Test
	public void testContactModification(){
		app.getContactHelper().initContactCreation();
		app.getContactHelper().fillContactForm(contact, true);
		app.getContactHelper().submitContactCreation();

		app.getNavigationHelper().gotoContactPage();
		app.getContactHelper().initContactModification(contact.getEmail());
		app.getContactHelper().fillContactForm(updatedContact, false);
		app.getContactHelper().submitContactModification();
	}
}
