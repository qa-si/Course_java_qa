package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

	@DataProvider
	public Iterator<Object[]> validContactsFromXml() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"))) {
			String line = reader.readLine();
			String xml = "";
			while (line != null) {
				xml += line;
				line = reader.readLine();
			}
			XStream xstream = new XStream();
			xstream.processAnnotations(ContactData.class);
			xstream.allowTypes(new Class[]{ContactData.class});
			List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
			return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
		}
	}

	@DataProvider
	public Iterator<Object[]> validContactsFromJson() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
			String line = reader.readLine();
			String json = "";
			while (line != null) {
				json += line;
				line = reader.readLine();
			}
			Gson gson = new Gson();
			List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
			}.getType());
			return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
		}
	}

	@Test(dataProvider = "validContactsFromJson")
	public void testContactCreation(ContactData contact) {
		app.goTo().groupPage();
		app.group().checkGroupExisting(contact.getGroup());
		app.goTo().contactPage();
		Contacts before = app.db().contacts();
		app.contact().initContactCreation();
		app.contact().fillContactForm(contact, true);
		app.contact().submitContactCreation();
		app.goTo().contactPage();
		Contacts after = app.db().contacts();

		contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
		assertThat(after, equalTo(before.withAdded(contact)));
	}
}