package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{
	ContactData contact = new ContactData("name", "lastname", "nickname", "title", "company", "address", "mobile number", "email");

	@Test
	public void testContactCreation() throws Exception {
		app.getContactHelper().initContactCreation();
		app.getContactHelper().fillContactForm(contact);
		app.getContactHelper().submitContactCreation();
	}
}