package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase{

	@Test

	public void testGroupModification(){
		app.getNavigationHelper().gotoGroupPage();
		if (!app.getGroupHelper().isThereAGroup()){
			app.getGroupHelper().createGroup(new GroupData("test10", null, null));
		}
		int before = app.getGroupHelper().getGroupCount();
		app.getGroupHelper().selectGroup();
		app.getGroupHelper().initGroupModification();
		app.getGroupHelper().fillGroupForm(new GroupData("test100000", "test20000", "test300000"));
		app.getGroupHelper().submitGroupModification();
		app.getGroupHelper().returnToGroupPage();
		int after = app.getGroupHelper().getGroupCount();
		Assert.assertEquals(after, before);
	}
}
