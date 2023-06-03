package ry.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.Browser.*;

public class ApplicationManager {
	private final Properties properties;
	private WebDriver wd;
	private final String browser;
	private RegistrationHelper registrationHelper;

	public ApplicationManager(String browser) {
		this.browser = browser;
		properties = new Properties();
	}

	public void init() throws IOException {
		String target = System.getProperty("target", "local");
		properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
	}

	public void stop() {
		if (wd != null) {
			wd.quit();
		}
	}

	public HttpSession newSession() {
		return new HttpSession(this);
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public RegistrationHelper registration() {
		if(registrationHelper == null){
			registrationHelper = new RegistrationHelper(this);
		}
		return registrationHelper;
	}

	public WebDriver getDriver() {
		if (wd == null) {
			if (Objects.equals(browser, FIREFOX.browserName())) {
				wd = new FirefoxDriver();
			} else if (Objects.equals(browser, CHROME.browserName())) {
				wd = new ChromeDriver();
			} else if (Objects.equals(browser, EDGE.browserName())) {
				wd = new EdgeDriver();
			}
			wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wd.get(properties.getProperty("web.baseUrl"));
		}
		return wd;
	}
}
