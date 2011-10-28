package serializers;

import java.lang.reflect.Type;

import play.data.validation.Error;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ErrorSerializer implements JsonSerializer<Error> {
	@Override
	public JsonElement serialize(Error error, Type arg1, JsonSerializationContext arg2) {
		JsonObject obj = new JsonObject();
        obj.addProperty("parameter", error.getKey());
        obj.addProperty("errors", error.message());
		return obj;
	}
}