package ar.edu.iua.practicoSD.util;

import ar.edu.iua.practicoSD.httpServer.HttpServerLogic;

public class ErrorHandler {

	public static String handleError(HttpParser parser) {
		
		String httpResponse = HttpServerConstant.EMPTY_STRING;
		HtmlCreator.createErrorHtmlResponse("Algo inesperado sucedio intentelo de nuevo");
		
		if (!parser.getMethod().equalsIgnoreCase("GET")) {			
			httpResponse = HtmlCreator.createErrorHtmlResponse("Metodo " + parser.getMethod() + " No soportado");
		
		} else if (!HttpServerLogic.isValidURL(parser.getRequestURL())) {
			httpResponse = HtmlCreator.createErrorHtmlResponse("URL " + parser.getRequestURL() + " No Valida, " +
					"recursos soportados 'getAnalogicValue' y 'getLogicValue' ");
		
		} else if (!HttpServerLogic.isParameterWellFormed(parser)) {
			httpResponse = HtmlCreator.createErrorHtmlResponse("Parametro o Valor invalido! Estos parametros: " + parser.getParams().toString() + 
					" no son soportados. Parametros posibles 'format=xml' , 'format=json' , 'reload=[number]' ");
		}
		
		return httpResponse;
	}
}
