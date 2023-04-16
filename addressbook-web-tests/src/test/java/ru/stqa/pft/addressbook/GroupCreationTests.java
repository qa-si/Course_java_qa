package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GroupCreationTests {
	private WebDriver wd;
	LoginData user = new LoginData("admin", "secret");

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		wd = new FirefoxDriver();
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wd.get("http://localhost/addressbook/");
		login(user);
	}

	private void login(LoginData loginData) {
		wd.findElement(By.name("user")).clear();
		wd.findElement(By.name("user")).sendKeys(loginData.getUsername());
		wd.findElement(By.name("pass")).clear();
		wd.findElement(By.name("pass")).sendKeys(loginData.getPassword());
		wd.findElement(By.xpath("//input[@value='Login']")).click();
	}

	@Test
	public void testGroupCreation() throws Exception {
		gotoGroupPage();
		initGroupCreation();
		fillGroupForm(new GroupData("test1", "test2", "test3"));
		submitGroupCreation();
		returnToGroupPage();
	}

	private void returnToGroupPage() {
		wd.findElement(By.linkText("group page")).click();
	}

	private void submitGroupCreation() {
		wd.findElement(By.name("submit")).click();
	}

	private void fillGroupForm(GroupData groupData) {
		wd.findElement(By.name("group_name")).click();
		wd.findElement(By.name("group_name")).clear();
		wd.findElement(By.name("group_name")).sendKeys(groupData.getName());
		wd.findElement(By.name("group_header")).click();
		wd.findElement(By.name("group_header")).clear();
		wd.findElement(By.name("group_header")).sendKeys(groupData.getHeader());
		wd.findElement(By.name("group_footer")).click();
		wd.findElement(By.name("group_footer")).clear();
		wd.findElement(By.name("group_footer")).sendKeys(groupData.getFooter());
	}

	private void initGroupCreation() {
		wd.findElement(By.name("new")).click();
	}

	private void gotoGroupPage() {
		wd.findElement(By.linkText("groups")).click();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		logout();
		wd.quit();
	}

	private void logout() {
		wd.findElement(By.linkText("Logout")).click();
	}
}
