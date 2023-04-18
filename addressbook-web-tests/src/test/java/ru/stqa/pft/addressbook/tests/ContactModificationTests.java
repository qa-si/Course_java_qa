package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

	ContactData updatedContact = new ContactData("Ivan", "Petrov", "Petro", "QA", "test company", "address", "9999", "e@mail.ru");

	@Test
	public void testContactModification(){
		app.getContactHelper().initContactModification(2);
		app.getContactHelper().fillContactForm(updatedContact);
		app.getContactHelper().submitContactModification();
	}
}
