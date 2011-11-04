package controllers;

import play.mvc.Controller;
import play.mvc.results.RenderTemplate;

public class WSDL extends Controller {

	public static void wsdl(){
		renderTemplate("service.wsdl");
	}
	
}
