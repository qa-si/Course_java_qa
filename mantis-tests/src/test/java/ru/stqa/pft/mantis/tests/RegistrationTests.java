package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

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
		String confirmationLink = app.user().findConfirmationLink(mailMessages, email);
		app.registration().finish(confirmationLink, password);
		assertTrue(app.newSession().login(user, password));
	}


	@Test
	public void resetPassword() throws MessagingException, IOException, InterruptedException {
		String newUserName;
		String newPassword;
		String oldUserName;
		String confirmationLink;
		app.user().loginByAdmin();
		app.user().openUserControl();
		UserData user = app.user().getRandomUser();
		Thread.sleep(3000);

		app.user().loginByAdmin();
		app.user().openUserControl();
		app.user().openUser(user.getName());
		app.user().resetPasswordClick();

		String subject = "[MantisBT] Password Reset";
		String resetPasswordMailMessage = app.mail().waitForMailBySubject(subject, 10000);

		confirmationLink = app.user().findConfirmationLinkInMessage(resetPasswordMailMessage);
		newUserName = String.format("user%s", System.currentTimeMillis());
		newPassword = "password1";
		oldUserName = user.getName();
		app.user().confirmResetPassword(confirmationLink, newUserName, newPassword);
		assertTrue(app.newSession().loginNewPassword(newUserName, newPassword, oldUserName));
	}

	@AfterMethod(alwaysRun = true)
	public void stopMailServer() {
		app.mail().stop();
	}
}
