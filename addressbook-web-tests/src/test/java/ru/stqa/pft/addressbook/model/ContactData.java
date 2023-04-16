package ru.stqa.pft.addressbook.model;

public class ContactData {
	private final String name;
	private final String lastname;
	private final String nickname;
	private final String title;
	private final String company;
	private final String address;
	private final String mobileNumber;
	private final String email;

	public ContactData(String name, String lastname, String nickname, String title, String company, String address, String mobileNumber, String email) {
		this.name = name;
		this.lastname = lastname;
		this.nickname = nickname;
		this.title = title;
		this.company = company;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTitle() {
		return title;
	}

	public String getCompany() {
		return company;
	}

	public String getAddress() {
		return address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmail() {
		return email;
	}
}
