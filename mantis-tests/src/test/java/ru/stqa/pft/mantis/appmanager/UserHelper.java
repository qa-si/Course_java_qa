package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class UserHelper extends HelperBase {

	public UserHelper(ApplicationManager app) {
		super(app);
		wd = app.getDriver();
	}

	public UserData registrationNewUser() throws MessagingException, IOException {
		long now = System.currentTimeMillis();
		String user = String.format("user%s", now);
		String password = "password";
		String email = String.format("user%s@localhost.localdomain", now);
		app.registration().start(user, email);
		List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
		String confirmationLink = findConfirmationLink(mailMessages, email);
		app.registration().finish(confirmationLink, password);
		return new UserData().withName(user).withPassword(password).withEmail(email);
	}

	public void openUserControl() {
		wd.get(app.getProperty("web.baseUrl") + "manage_overview_page.php");
		wd.get(app.getProperty("web.baseUrl") + "manage_user_page.php");
	}

	public UserData getRandomUser() throws MessagingException, IOException {
		int userCount = wd.findElements(By.xpath("//tbody/tr")).size();
		if (userCount < 2) {
			return registrationNewUser();
		} else {
			int rnd = (int) (Math.random() * (userCount - 1)) + 2;
			UserData user = new UserData()
					.withName(wd.findElement(By.xpath(String.format("//tbody/tr[%s]/td[1]/a", rnd))).getText())
					.withEmail(wd.findElement(By.xpath(String.format("//tbody/tr[%s]/td[3]", rnd))).getText());
			System.out.println(user);
			return user;
		}
	}

	public void openUser(String userName) {
		click(By.xpath(String.format("//*[text()='%s']", userName)));
	}

	public void resetPasswordClick() {
		click(By.xpath("//input[@value='�������� ������']"));
	}

	public void confirmResetPassword(String confirmationLink, String userName, String password) {
		wd.get(confirmationLink);
		type(By.name("realname"), userName);
		type(By.name("password"), password);
		type(By.name("password_confirm"), password);
		click(By.cssSelector(".submit-button button"));
	}

	public void loginByAdmin() {
		if (wd.findElements(By.cssSelector(".user-info")).size() == 0 || !wd.findElement(By.cssSelector(".user-info")).getText().equals("administrator")) {
			type(By.name("username"), "administrator");
			click(By.cssSelector("input[value='����']"));
			type(By.name("password"), "root");
			click(By.cssSelector("input[value='����']"));
		}
	}

	public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
		MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
		VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
		return regex.getText(mailMessage.text);
	}

	public String findConfirmationLinkInMessage(String message) {
		VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
		return regex.getText(message);
	}

}
