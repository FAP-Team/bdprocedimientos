package responses;

import play.exceptions.UnexpectedException;
import play.libs.MimeTypes;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Http.StatusCode;
import play.mvc.results.Result;
import utils.JSonUtils;

public class ErrorResponse extends Result{

	private JsonError error;
	
	private Integer status;
	
	public ErrorResponse() {		
	}
	
	public ErrorResponse(JsonError error, Integer status) {
		this.error = error;
		this.status = status;
	}

	public ErrorResponse(String error, Integer status) {
		this.error = new JsonError(error);
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
		
	public JsonError getError() {
		return error;
	}

	public void setError(JsonError error) {
		this.error = error;
	}

	@Override
	public void apply(Request request, Response response) {
		response.status = status;
        String encoding = getEncoding();
		setContentTypeIfNotSet(response, "application/json; charset=" + encoding);
	    
		String json = JSonUtils.getGson().toJson(error);
		try {
	    	response.out.write(json.getBytes(getEncoding()));
	    } catch (Exception e) {
	    	throw new UnexpectedException(e);
	    }
	}
}
