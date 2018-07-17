package com.cornucopia.parser.entry;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Dog implements IAnimal, Parcelable {

	abstract String name();
	
	abstract int ferocity();
	
	public Dog() {
	}
	
	public static Dog create(String name, int ferocity) {
		return new AutoValue_Dog(name, ferocity);
	}

	@Override
	public String sound() {
		return name() + ":\"brak\" { ferocity level: " + ferocity() + " }";
	}

	// Parcelable need auto-value-parcel generate writeToParcel
	// com.ryanharter.auto.value:auto-value-parcel

}
