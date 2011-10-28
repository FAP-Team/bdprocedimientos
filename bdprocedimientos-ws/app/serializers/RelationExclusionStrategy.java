package serializers;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class RelationExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if(f.getAnnotation(OneToOne.class) != null) return true;
		if(f.getAnnotation(OneToMany.class) != null) return true;
		if(f.getAnnotation(ManyToOne.class) != null) return true;
		if(f.getAnnotation(ManyToMany.class) != null) return true;
		return false;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
