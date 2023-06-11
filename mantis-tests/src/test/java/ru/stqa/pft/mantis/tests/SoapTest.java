package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTest extends TestBase{

	@Test
	public void testGetProject() throws MalformedURLException, ServiceException, RemoteException {
		final Set<Project> projects = app.soap().getProjects();
		System.out.println(projects.size());
	}

	@Test
	public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
		Set<Project> projects = app.soap().getProjects();
		Project project = projects.iterator().next();
		Issue issue = new Issue()
				.withSummary("test")
				.withDescription("test issue description")
				.withProject(project)
				.withComment("comment")
				.withFixed(false);
		Issue created = app.soap().addIssue(issue);
		assertEquals(issue.getSummary(), created.getSummary());
	}
}
