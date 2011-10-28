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

public class ValidationErrors extends ErrorResponse {
	
	public ValidationErrors(List<Error> errors) {
		super(JSonUtils.getGson().toJson(new ArrayList<Error>(errors)), Http.StatusCode.BAD_REQUEST);
	}
	
}
