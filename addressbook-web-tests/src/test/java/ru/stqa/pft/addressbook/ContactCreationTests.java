package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ContactCreationTests {

	LoginData user = new LoginData("admin", "secret");
	private WebDriver wd;
	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		wd = new FirefoxDriver();
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testContactCreationTests() throws Exception {
		login(user);
		initContactCreation();
		fillContactForm(new ContactData("name", "lastname", "nickname", "title", "company", "address", "mobile number", "email"));
		submitContactCreation();
	}

	private void submitContactCreation() {
		wd.findElement(By.name("submit")).click();
	}

	private void fillContactForm(ContactData contactData) {
		wd.findElement(By.name("firstname")).click();
		wd.findElement(By.name("firstname")).clear();
		wd.findElement(By.name("firstname")).sendKeys(contactData.getName());
		wd.findElement(By.name("lastname")).click();
		wd.findElement(By.name("lastname")).clear();
		wd.findElement(By.name("lastname")).sendKeys(contactData.getLastname());
		wd.findElement(By.name("nickname")).click();
		wd.findElement(By.name("nickname")).clear();
		wd.findElement(By.name("nickname")).sendKeys(contactData.getNickname());
		wd.findElement(By.name("title")).click();
		wd.findElement(By.name("title")).clear();
		wd.findElement(By.name("title")).sendKeys(contactData.getTitle());
		wd.findElement(By.name("company")).click();
		wd.findElement(By.name("company")).clear();
		wd.findElement(By.name("company")).sendKeys(contactData.getCompany());
		wd.findElement(By.name("address")).clear();
		wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
		wd.findElement(By.name("mobile")).clear();
		wd.findElement(By.name("mobile")).sendKeys(contactData.getMobileNumber());
		wd.findElement(By.name("email")).clear();
		wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
	}

	private void initContactCreation() {
		wd.findElement(By.linkText("add new")).click();
	}

	private void login(LoginData loginData) {
		wd.get("http://localhost/addressbook/");
		wd.findElement(By.name("user")).clear();
		wd.findElement(By.name("user")).sendKeys(loginData.getUsername());
		wd.findElement(By.name("pass")).clear();
		wd.findElement(By.name("pass")).sendKeys(loginData.getPassword());
		wd.findElement(By.xpath("//input[@value='Login']")).click();
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
