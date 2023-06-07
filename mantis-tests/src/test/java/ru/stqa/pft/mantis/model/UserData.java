package ru.stqa.pft.mantis.model;

public class UserData {

	public String name;
	public String password;
	public String email;

	public UserData() {

	}

	public UserData withName(String name) {
		this.name = name;
		return this;
	}

	public UserData withPassword(String password) {
		this.password = password;
		return this;
	}

	public UserData withEmail(String email) {
		this.email = email;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public UserData(String name, String password, String email){
		this.name = name;
		this.password = password;
		this.email = email;
	}
}
