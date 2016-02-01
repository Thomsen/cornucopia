package com.cornucopia.parser.entry;

public class Dog implements IAnimal {

	private String name;
	
	private int ferocity;
	
	public Dog() {
		
	}
	
	public Dog(String name, int ferocity) {
		this.setName(name);
		this.setFerocity(ferocity);
	}

	@Override
	public String sound() {
		return getName() + ":\"brak\" { ferocity level: " + getFerocity() + " }"; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFerocity() {
		return ferocity;
	}

	public void setFerocity(int ferocity) {
		this.ferocity = ferocity;
	}

}
