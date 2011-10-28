package responses;

import com.google.gson.JsonPrimitive;

import play.exceptions.UnexpectedException;
import play.libs.MimeTypes;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;
import utils.JSonUtils;

public class ResourceNotFoundResponse extends ErrorResponse  {

	public ResourceNotFoundResponse(String resourceName, Long id) {
		super();
		setError(new JsonError("Recurso " + resourceName + " con id " + id + " no encontrado"));
		setStatus(Http.StatusCode.NOT_FOUND);
	}
	
}
