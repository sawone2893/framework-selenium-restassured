package com.framework.pages;

public class Register {

	private String firstName;
	private String lastName;
	private String email;
	private String telephone;
	private String fax;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private String loginName;
	private String password;
	private String confirmPassword;

	private Register(RegisterBuilder builder) {
		this.firstName=builder.firstName;
		this.lastName=builder.lastName;
		this.email=builder.email;
		this.telephone=builder.telephone;
		this.fax=builder.fax;
		this.company=builder.company;
		this.address1=builder.address1;
		this.address2=builder.address2;
		this.city=builder.city;
		this.state=builder.state;
		this.zipCode=builder.zipCode;
		this.country=builder.country;
		this.loginName=builder.loginName;
		this.password=builder.password;
		this.confirmPassword=builder.confirmPassword;
		
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getFax() {
		return fax;
	}

	public String getCompany() {
		return company;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public static class RegisterBuilder {

		private String firstName;
		private String lastName;
		private String email;
		private String telephone;
		private String fax;
		private String company;
		private String address1;
		private String address2;
		private String city;
		private String state;
		private String zipCode;
		private String country;
		private String loginName;
		private String password;
		private String confirmPassword;

		public RegisterBuilder(String firstName,String lastName,String email) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}

		public RegisterBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public RegisterBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public RegisterBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public RegisterBuilder setTelephone(String telephone) {
			this.telephone = telephone;
			return this;
		}

		public RegisterBuilder setFax(String fax) {
			this.fax = fax;
			return this;
		}

		public RegisterBuilder setCompany(String company) {
			this.company = company;
			return this;
		}

		public RegisterBuilder setAddress1(String address1) {
			this.address1 = address1;
			return this;
		}

		public RegisterBuilder setAddress2(String address2) {
			this.address2 = address2;
			return this;
		}

		public RegisterBuilder setCity(String city) {
			this.city = city;
			return this;
		}

		public RegisterBuilder setState(String state) {
			this.state = state;
			return this;
		}

		public RegisterBuilder setZipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}

		public RegisterBuilder setCountry(String country) {
			this.country = country;
			return this;
		}

		public RegisterBuilder setLoginName(String loginName) {
			this.loginName = loginName;
			return this;
		}

		public RegisterBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public RegisterBuilder setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
			return this;
		}
		
		public Register build() {
			return new Register(this);
		}
	}
}
