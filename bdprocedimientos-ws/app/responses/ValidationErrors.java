package responses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import play.data.validation.Error;

public class ValidationErrors {
	public Set<ValidationError> validationErrors;
	
	public ValidationErrors(List<Error> errors){
		validationErrors = new HashSet<ValidationError>();
		for(Error e : errors){
			validationErrors.add(new ValidationError(e.getKey(), e.message()));
		}
	}
	
	public boolean contains(ValidationError error){
		return validationErrors.contains(error);
	}
	
	public boolean contains(String parameter, String error){
		return contains(new ValidationError(parameter, error));
	}
	

}


