package utils;

import java.lang.reflect.Type;

import play.data.validation.Error;
import serializers.ErrorSerializer;
import serializers.IdExclusionEstrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import flexjson.JSONSerializer;

public class JSonUtils {

	public static GsonBuilder getGsonBuilder(){
		GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Error.class, new ErrorSerializer());
		gsonBuilder.serializeNulls();
		return gsonBuilder;
	}
	
	public static Gson getGson(){
		return getGsonBuilder().create();
	}
	
	public static Gson getGsonIgnoreId(){
		return getGsonBuilder()
				.addDeserializationExclusionStrategy(new IdExclusionEstrategy())
				.create();
	}
	
	public static JSONSerializer getSerializer(){
		//TODO pretty print only in dev mode
		return new JSONSerializer().exclude("*.persistent.*", "*.entityId.*", "*.class.*").prettyPrint(true);
	}
	
	public static String toJsonString(String str){
		return "\"" + str + "\"";
	}
	
}
