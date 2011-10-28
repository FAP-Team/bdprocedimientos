package serializers;

import javax.persistence.Id;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class IdExclusionEstrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes field) {
		Id annotation = field.getAnnotation(Id.class);
		return annotation != null;
	}

}
