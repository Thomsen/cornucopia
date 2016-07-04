package com.cornucopia.http.retrofit;

import java.lang.reflect.Type;

import com.cornucopia.http.mibo.MiboUser;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserResultsDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        
        MiboUser user = null;
        
        if (null != json) {
            user = new MiboUser();
            JsonObject jsonObj = json.getAsJsonObject();
            user.setId(jsonObj.get("id").getAsString());
            user.setUsername(jsonObj.get("username").getAsString());
        }
        
        return user;
    }

}
