package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

	@DataProvider
	public Iterator<Object[]> validGroupsFromXml() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"))) {
			String line = reader.readLine();
			String xml = "";
			while (line != null) {
				xml += line;
				line = reader.readLine();
			}
			XStream xstream = new XStream();
			xstream.processAnnotations(GroupData.class);
			xstream.allowTypes(new Class[]{GroupData.class});
			List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
			return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
		}
	}

	@DataProvider
	public Iterator<Object[]> validGroupsFromJson() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.json"))) {
			String line = reader.readLine();
			String json = "";
			while (line != null) {
				json += line;
				line = reader.readLine();
			}
			Gson gson = new Gson();
			List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
			}.getType());
			return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
		}
	}

	@Test(dataProvider = "validGroupsFromJson")
	public void testGroupCreation(GroupData group) {
		app.goTo().groupPage();
		Groups before = app.db().groups();
		app.group().create(group);
		Groups after = app.db().groups();
		assertThat(after, equalTo(
				before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
	}

	@Test
	public void testBadGroupCreation() {
		app.goTo().groupPage();
		Groups before = app.db().groups();
		GroupData group = new GroupData().withName("test25");
		app.group().create(group);
		Groups after = app.db().groups();
		assertThat(after, equalTo(before));
	}
}