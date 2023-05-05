package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
	private int id;
	private final String name;
	private final String lastname;
	private final String email;

	private final String group;

	public ContactData(int id, String name, String lastname, String email, String group) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.group = group;
	}

	public ContactData(String name, String lastname, String email, String group) {
		this.id = Integer.MAX_VALUE;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getGroup() {
		return group;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ContactData that = (ContactData) o;
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(lastname, that.lastname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, lastname);
	}

	@Override
	public String toString() {
		return "ContactData{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lastname='" + lastname + '\'' +
				'}';
	}
}
