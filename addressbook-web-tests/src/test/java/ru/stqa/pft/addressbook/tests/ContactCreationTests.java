package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{
	ContactData contact = new ContactData("name", "lastname", "email", "test1");

	@Test
	public void testContactCreation() throws Exception {
		app.getContactHelper().initContactCreation();
		app.getContactHelper().fillContactForm(contact, true);
		app.getContactHelper().submitContactCreation();
	}
}