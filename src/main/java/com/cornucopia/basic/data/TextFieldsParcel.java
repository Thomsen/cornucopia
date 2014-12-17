package com.cornucopia.basic.data;

import android.os.Parcel;
import android.os.Parcelable;

public class TextFieldsParcel implements Parcelable {
	
	private String plainText;
	private String personName;
	private String password;
	private String passwordNumeric;
	private String email;
	private String phone;
	private String postalAddress;
	private String multilineText;
	private String time;
	private String date;
	private String number;
	private String numberSigned;
	private String numberDecimal;
	private String autoCompleteText;
	private String multiAutoCompleteText;
	
//	TextFieldsParcel textFieldsParcel;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		// Object是普通的类型或是继承Parcelable接口的对象
//		dest.writeValue(textFieldsParcel);
		
		dest.writeString(numberDecimal);
		dest.writeString(postalAddress);
		dest.writeString(autoCompleteText);
		dest.writeString(date);
		dest.writeString(email);
		dest.writeString(multiAutoCompleteText);
		dest.writeString(multilineText);
		dest.writeString(numberDecimal);
		dest.writeString(number);
		dest.writeString(numberSigned);
		dest.writeString(password);
		dest.writeString(passwordNumeric);
		dest.writeString(personName);
		dest.writeString(phone);
		dest.writeString(plainText);
	}
	
	public static final Parcelable.Creator<TextFieldsParcel> CREATOR = 
		new Parcelable.Creator<TextFieldsParcel>() {

			@Override
			public TextFieldsParcel createFromParcel(Parcel source) {
				return new TextFieldsParcel(source);
			}

			@Override
			public TextFieldsParcel[] newArray(int size) {
				return new TextFieldsParcel[size];
			}
			
		};
		
	public TextFieldsParcel() {
		
	}
	
	public TextFieldsParcel(Parcel p) {
//		mTextFields.setPlainText(p.readString());
		
//		textFieldsParcel = (TextFieldsParcel) p.readValue(TextFieldsParcel.class.getClassLoader());
		
		numberDecimal = p.readString();
		postalAddress = p.readString();
		autoCompleteText = p.readString();
		date = p.readString();
		email = p.readString();
		multiAutoCompleteText = p.readString();
		multilineText = p.readString();
		numberDecimal = p.readString();
		number = p.readString();
		numberSigned = p.readString();
		password = p.readString();
		passwordNumeric = p.readString();
		personName = p.readString();
		phone = p.readString();
		plainText = p.readString();
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordNumeric() {
		return passwordNumeric;
	}

	public void setPasswordNumeric(String passwordNumeric) {
		this.passwordNumeric = passwordNumeric;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getMultilineText() {
		return multilineText;
	}

	public void setMultilineText(String multilineText) {
		this.multilineText = multilineText;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumberSigned() {
		return numberSigned;
	}

	public void setNumberSigned(String numberSigned) {
		this.numberSigned = numberSigned;
	}

	public String getNumberDecimal() {
		return numberDecimal;
	}

	public void setNumberDecimal(String numberDecimal) {
		this.numberDecimal = numberDecimal;
	}

	public String getAutoCompleteText() {
		return autoCompleteText;
	}

	public void setAutoCompleteText(String autoCompleteText) {
		this.autoCompleteText = autoCompleteText;
	}

	public String getMultiAutoCompleteText() {
		return multiAutoCompleteText;
	}

	public void setMultiAutoCompleteText(String multiAutoCompleteText) {
		this.multiAutoCompleteText = multiAutoCompleteText;
	}


}
