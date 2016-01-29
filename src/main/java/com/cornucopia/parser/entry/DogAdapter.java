package com.cornucopia.parser.entry;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DogAdapter implements JsonSerializer<Dog>, JsonDeserializer<Dog> {

	@Override
	public JsonElement serialize(Dog src, Type typeOfT,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getName());
	}
	
	@Override
	public Dog deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonPrimitive prim = json.getAsJsonPrimitive();
		return new Dog();
	}

}
