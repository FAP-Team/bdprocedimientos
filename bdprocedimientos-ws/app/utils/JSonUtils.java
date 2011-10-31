package utils;

import play.data.validation.Error;
import serializers.ErrorSerializer;
import serializers.IdExclusionEstrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
	public static String toJsonString(String str){
		return "\"" + str + "\"";
	}
	
}
