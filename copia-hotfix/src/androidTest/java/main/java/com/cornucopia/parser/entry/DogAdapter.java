package com.cornucopia.parser.entry;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DogAdapter implements JsonSerializer<Dog>, JsonDeserializer<Dog> {

	@Override
	public JsonElement serialize(Dog src, Type typeOfT,
			JsonSerializationContext context) {
//		JsonPrimitive prim = new JsonPrimitive(src.getName());
//		return prim; 
//		return context.serialize(src);
		JsonObject obj = new JsonObject();
		obj.addProperty("name", src.getName());
		obj.addProperty("ferocity", src.getFerocity());
		return obj;
	}
	
	@Override
	public Dog deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
//		JsonPrimitive prim = json.getAsJsonPrimitive();
//		return new Dog(prim.getAsString(), 1);
//		return context.deserialize(json, typeOfT);
		JsonObject obj = json.getAsJsonObject();
		JsonPrimitive name = (JsonPrimitive) obj.get("name");
		JsonPrimitive ferocity = (JsonPrimitive) obj.get("ferocity");
		return new Dog(name.getAsString(), ferocity.getAsInt());
	}

}
