package utils;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class PojoUtils {

	public static <T extends Object> void copyAllSimpleFields(T from, T to){
		Field[] declaredFields = from.getClass().getDeclaredFields();
		for(Field f : declaredFields){
			try {
				//Ignora las referencias
				if (!isReference(f)){
					f.set(to, f.get(from));
				}
			} catch (Exception  e) {
				//Exception?
			}
		}
	}
	
	private static boolean isReference(Field f){
		return isAnnotated(f, OneToOne.class) || isAnnotated(f, OneToMany.class) || isAnnotated(f, ManyToOne.class) || isAnnotated(f, ManyToMany.class);
	}
	
	private static boolean isAnnotated(Field f , Class annotation){
		return f.getAnnotation(annotation) != null;
	}
	
}
