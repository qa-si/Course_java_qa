package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

	@BeforeMethod
	public void ensurePreconditions() {
		app.goTo().groupPage();
		if (app.group().all().size() == 0) {
			app.group().create(new GroupData().withName("test1"));
		}
	}

	@Test
	public void testGroupModification() {
		Groups before = app.group().all();
		GroupData modifyGroup = before.iterator().next();
		GroupData group = new GroupData()
				.withId(modifyGroup.getId())
				.withName("test1")
				.withHeader("test20000")
				.withFooter("test300000");
		app.group().modify(group);
		assertThat(app.group().count(), equalTo(before.size()));
		Groups after = app.group().all();
		assertThat(after, equalTo(before.withoutAdded(modifyGroup).withAdded(group)));
	}
}
