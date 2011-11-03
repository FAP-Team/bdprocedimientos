package com.fap.bdp;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.fap.bdp.exceptions.BDPException;
import com.fap.bdp.exceptions.BDPNotFoundException;
import com.fap.bdp.exceptions.BDPValidationException;

public class RestTemplate {

	private String baseUrl;
	
	private String mediaType;

	public RestTemplate(String baseUrl) {
		constructor(baseUrl, MediaType.APPLICATION_JSON);
	}

	public RestTemplate(String baseUrl, String mediaType) {
		constructor(baseUrl, mediaType);
	}

	private void constructor(String baseUrl, String mediaType) {
		this.baseUrl = baseUrl;
		this.mediaType = mediaType;
	}

	public <T> List<T> getList(String url, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		GenericType<List<T>> type = new GenericType<List<T>>() {};
		ClientResponse<List<T>> response = request.get(type);
		return resultOrError(response);
	}

	public <T> T get(String url, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		ClientResponse<T> response = request.get(clazz);
		return resultOrError(response);
	}

	public <T> T create(String url, T tipoEvaluacion, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		request.body(this.mediaType, tipoEvaluacion);
		ClientResponse<T> response = request.post(clazz);
		return resultOrError(response);
	}

	public void delete(String url) throws Exception {
		ClientRequest request = getRequest(url);
		ClientResponse<?> response = request.delete();
		isOk(response);
	}

	public <T> T update(String url, T o, Class<T> clazz) throws Exception {
		ClientRequest request = getRequest(url);
		request.body(this.mediaType, o);
		ClientResponse<T> response = request.put(clazz);
		return resultOrError(response);
	}

	public <T> T resultOrError(ClientResponse<T> response) throws BDPException {
		if (isOk(response)) {
			return response.getEntity();
		}
		return null;
	}

	private ClientRequest getRequest(String url) {
		ClientRequest request = new ClientRequest(this.baseUrl + url);
		request.accept(this.mediaType);
		return request;
	}

	protected boolean isOk(ClientResponse<?> response) throws BDPException {
		if (response.getStatus() == HttpStatus.SC_OK) {
			return true;
		}
		createError(response);
		return false;
	}

	protected void createError(ClientResponse<?> response) throws BDPException {
		String body = response.getEntity(String.class);
		if (response.getStatus() == HttpStatus.SC_NOT_FOUND) {
			throw new BDPNotFoundException(body);
		} else if (response.getStatus() == HttpStatus.SC_BAD_REQUEST) {
			throw new BDPValidationException(body);
		}
		throw new BDPException(body);
	}

}
