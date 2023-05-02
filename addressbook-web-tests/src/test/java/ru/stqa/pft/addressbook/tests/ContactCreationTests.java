package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
	GroupData group = new GroupData("test1", null, null);
	ContactData contact = new ContactData("Fill", "Jones", "email", group.getName());

	@Test
	public void testContactCreation() throws Exception {
		app.getNavigationHelper().gotoGroupPage();
		app.getGroupHelper().checkGroupExisting(group);
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> before = app.getContactHelper().getContactList();
		app.getContactHelper().initContactCreation();
		app.getContactHelper().fillContactForm(contact, true);
		app.getContactHelper().submitContactCreation();
		app.getNavigationHelper().gotoContactPage();
		List<ContactData> after = app.getContactHelper().getContactList();
		Assert.assertEquals(after.size(), before.size() + 1);

		contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
		before.add(contact);
		Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
		before.sort(byId);
		after.sort(byId);
		Assert.assertEquals(before, after);
	}
}