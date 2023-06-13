package ru.srqa.pft.rest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.*;

public class RestTests {

	@Test
	public void testCreateIssue() throws IOException {
		Set<Issue> oldIssues = getIssues();
		Issue newIssue = new Issue().withSubject("Test issue").withDescription("Test description");
		int issueId = createIssue(newIssue);
		oldIssues.add(newIssue.withId(issueId));
		Set<Issue> newIssues = getIssues();
		assertEquals(newIssues, oldIssues);
	}

	private int createIssue(Issue issue) throws IOException {
		String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
						.bodyForm(new BasicNameValuePair("subject", issue.getSubject()),
								new BasicNameValuePair("description", issue.getDescription())))
				.returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		return parsed.getAsJsonObject().get("issue_id").getAsInt();
	}

	private Set<Issue> getIssues() throws IOException {
		String json = getExecutor()
				.execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
				.returnContent()
				.asString();
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement issues = parsed.getAsJsonObject().get("issues");
		return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
		}.getType());
	}

	private Executor getExecutor() {
		return Executor.newInstance().auth("b31e382ca8445202e66b03aaf31508a3", "");
	}

	public void skipIfNotFixed(int issueId) throws IOException {
		if (isIssueOpen(issueId)) {
			throw new SkipException("Ignored because of issue " + issueId);
		}
	}

	private boolean isIssueOpen(int issueId) throws IOException {
		String json = getExecutor()
				.execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", 300)))
				.returnContent()
				.asString();
		JsonElement parsed = new JsonParser().parse(json);
		JsonArray issue = parsed.getAsJsonObject().get("issues").getAsJsonArray();
		String state = issue.get(0).getAsJsonObject().get("state_name").getAsString();
		boolean isClosed = state.equals("Closed");
		return !isClosed;
	}

}
