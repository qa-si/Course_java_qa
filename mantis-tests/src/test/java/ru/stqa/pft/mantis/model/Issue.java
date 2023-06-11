package ru.stqa.pft.mantis.model;

public class Issue {

	private int id;
	private String summary;
	private String description;
	private Project project;
	private String comment;
	private boolean fixed;

	public int getId() {
		return id;
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public Project getProject() {
		return project;
	}

	public String getComment() {
		return comment;
	}

	public boolean isFixed() {
		return fixed;
	}

	public Issue withId(int id) {
		this.id = id;
		return this;
	}

	public Issue withSummary(String summary) {
		this.summary = summary;
		return this;
	}

	public Issue withDescription(String description) {
		this.description = description;
		return this;
	}

	public Issue withProject(Project project) {
		this.project = project;
		return this;
	}

	public Issue withComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Issue withFixed(boolean fixed) {
		this.fixed = fixed;
		return this;
	}
}
