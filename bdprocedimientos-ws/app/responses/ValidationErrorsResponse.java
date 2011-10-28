package responses;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import play.Logger;
import play.data.validation.Error;
import play.exceptions.UnexpectedException;
import play.libs.MimeTypes;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;
import utils.JSonUtils;

public class ValidationErrorsResponse extends Result {
	
	private ValidationErrors errors;
	
	public ValidationErrorsResponse(List<Error> errors) {
		this.errors = new ValidationErrors(errors);
	}

	@Override
	public void apply(Request request, Response response) {
		response.status = Http.StatusCode.BAD_REQUEST;
        String encoding = getEncoding();
		setContentTypeIfNotSet(response, "application/json; charset=" + encoding);
	    String json = new Gson().toJson(errors);
		try {
	    	response.out.write(json.getBytes(getEncoding()));
	    } catch (Exception e) {
	    	throw new UnexpectedException(e);
	    }
	}	
}
