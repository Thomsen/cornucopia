package com.cornucopia.parser.entry;

public class Cat implements IAnimal {
	
	private String name;
	
	public Cat() {
		
	}
	
	public Cat(String name) {
		this.setName(name);
	}

	@Override
	public String sound() {
		return getName() + ":\"meaow\"";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
