package ry.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class UserHelper extends HelperBase {

	public UserHelper(ApplicationManager app) {
		super(app);
		wd = app.getDriver();
	}

	public void openUserControl() {
		wd.get(app.getProperty("web.baseUrl") + "manage_overview_page.php");
		wd.get(app.getProperty("web.baseUrl") + "manage_user_page.php");
	}

	public void openUser(String userName) {
		click(By.xpath(String.format("//*[text()='%s']", userName)));
	}

	public void resetPasswordClick() {
		click(By.xpath("//input[@value='—бросить пароль']"));
	}

	public void confirmResetPassword(String confirmationLink, String userName, String password) {
		wd.get(confirmationLink);
		type(By.name("realname"), userName);
		type(By.name("password"), password);
		type(By.name("password_confirm"), password);
		click(By.cssSelector(".submit-button button"));
	}

	public void loginByAdmin() {
		type(By.name("username"), "administrator");
		click(By.cssSelector("input[value='¬ход']"));
		type(By.name("password"), "root");
		click(By.cssSelector("input[value='¬ход']"));
	}

}
