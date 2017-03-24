package com.cornucopia.parser.gson;

import com.cornucopia.parser.entry.Cat;
import com.cornucopia.parser.entry.CatAdapter;
import com.cornucopia.parser.entry.Dog;
import com.cornucopia.parser.entry.DogAdapter;
import com.cornucopia.parser.entry.IAnimal;
import com.cornucopia.parser.entry.IAnimalAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonBuilderUtil {
	
	public static Gson createAminalGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(IAnimal.class, new IAnimalAdapter());
		
		return gsonBuilder.create();
	}
	
	public static Gson createAnimalHierarchyGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeHierarchyAdapter(IAnimal.class, new IAnimalAdapter());
		gsonBuilder.registerTypeHierarchyAdapter(Cat.class, new CatAdapter());
		gsonBuilder.registerTypeHierarchyAdapter(Dog.class, new DogAdapter());
		
		return gsonBuilder.create();
	}

}
