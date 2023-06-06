package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

	@BeforeMethod
	public void startMailServer() {
		app.mail().start();
	}

	@Test
	public void testRegistration() throws MessagingException, IOException {
		long now = System.currentTimeMillis();
		String user = String.format("user%s", now);
		String password = "password";
		String email = String.format("user%s@localhost.localdomain", now);
		app.registration().start(user, email);
		List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
		String confirmationLink = findConfirmationLink(mailMessages, email);
		app.registration().finish(confirmationLink, password);
		assertTrue(app.newSession().login(user, password));
	}

	private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
		MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
		VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
		return regex.getText(mailMessage.text);
	}

	private String findConfirmationLinkInMessage(String message) {
		VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
		return regex.getText(message);
	}

	@Test
	public void resetPassword() throws MessagingException, IOException, InterruptedException {
		long now = System.currentTimeMillis();
		String user = String.format("user%s", now);
		String password = "password";
		String newUserName = String.format("user%s", now);
		String newPassword = "password1";
		String email = String.format("user%s@localhost.localdomain", now);
		app.registration().start(user, email);
		List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
		String confirmationLink = findConfirmationLink(mailMessages, email);
		app.registration().finish(confirmationLink, password);

		Thread.sleep(3000);
		app.user().loginByAdmin();
		app.user().openUserControl();
		app.user().openUser(user);
		app.user().resetPasswordClick();

		String subject = "[MantisBT] Password Reset";
		String resetPasswordMailMessage = app.mail().waitForMailBySubject(subject, 10000);

		confirmationLink = findConfirmationLinkInMessage(resetPasswordMailMessage);
		app.user().confirmResetPassword(confirmationLink, user, newPassword);
		assertTrue(app.newSession().loginNewPassword(newUserName, newPassword, user));
	}

	@AfterMethod(alwaysRun = true)
	public void stopMailServer() {
		app.mail().stop();
	}
}
