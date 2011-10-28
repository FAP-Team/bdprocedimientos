package serializers;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Excluye los campos anotados con @ManyToOne
 */
public class AscendentExclusionEstrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes field) {
		return field.getAnnotation(ManyToOne.class) != null;
	}

}
