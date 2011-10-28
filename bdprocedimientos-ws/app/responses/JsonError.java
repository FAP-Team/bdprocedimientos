package responses;

public class JsonError {
	private Object error;
	
	public JsonError(Object error) {
		this.error = error;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
	
}
