package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

	ContactData contact = new ContactData("Ivan", "Ivanov", "iv@an.ov");
	ContactData updatedContact = new ContactData("Ilya", "Petrov", "e@mail.ru");

	@Test
	public void testContactModification(){
		app.getContactHelper().initContactCreation();
		app.getContactHelper().fillContactForm(contact);
		app.getContactHelper().submitContactCreation();

		app.getNavigationHelper().gotoContactPage();
		app.getContactHelper().initContactModification(contact.getEmail());
		app.getContactHelper().fillContactForm(updatedContact);
		app.getContactHelper().submitContactModification();
	}
}
