package ar.edu.iua.practicoSD.httpServer;

import org.apache.commons.lang3.math.NumberUtils;

import ar.edu.iua.practicoSD.util.HttpParser;
import ar.edu.iua.practicoSD.util.HttpServerConfig;
import ar.edu.iua.practicoSD.util.HttpServerConstant;

public class HttpServerLogic {

	public static boolean isValidURL(String url) {		
		return isAnalogicValue(url) || isLogicValue(url);		
	}
	
	public static boolean isAnalogicValue(String url) {
		return url.contains("getAnalogicValue");
	}
	
	public static boolean isLogicValue(String url) {
		return url.contains("getLogicValue");
	}
	
	public static boolean isXmlRequest(HttpParser parser) {
		return parser.getParam("format") != null && "xml".equalsIgnoreCase(parser.getParam("format"));
	}
	
	public static boolean isJsonRequest(HttpParser parser) {
		return parser.getParam("format") != null && "json".equalsIgnoreCase(parser.getParam("format"));
	}
	
	public static boolean isReloadRequest(HttpParser parser) {
		return parser.getParam("reload") != null && NumberUtils.isDigits(parser.getParam("reload"));
	}
	
	public static boolean isFormatParamPresent(HttpParser parser) {
		return parser.getParam("format") != null;
	}	
	
	public static boolean isReloadParamPresent(HttpParser parser) {
		return parser.getParam("reload") != null;
	}	
	
	public static boolean isParameterWellFormed(HttpParser parser) {		
		if (!parser.getParams().isEmpty()) {
			
			if (isFormatParamPresent(parser)) {				
				if (!(isXmlRequest(parser) || isJsonRequest(parser))) {
					return false;
				}				
			} 
			
			if (isReloadParamPresent(parser)) {
				if (!isReloadRequest(parser)) {
					return false;
				}				
			}				
		} 
		return true;
	}
	
	public static void createActuatorRequest(HttpParser parser, ActuatorClientRequest request) {
		if (HttpServerLogic.isLogicValue(parser.getRequestURL())) {
			request.setRequest("Request Error");
			if(HttpServerLogic.isJsonRequest(parser)) {
				request.setRequest(HttpServerConfig.getActuatorRequestGetLogicalJson() + 
						HttpServerConstant.ACTUATOR_REQUEST_END_OF_MESSAGE);
			
			} else if(HttpServerLogic.isXmlRequest(parser)) {
				request.setRequest(HttpServerConfig.getActuatorRequestGetLogicalXml() + 
						HttpServerConstant.ACTUATOR_REQUEST_END_OF_MESSAGE); 
			
			} else {			
				request.setRequest(HttpServerConfig.getActuatorRequestGetLogicalXml() +
						 HttpServerConstant.ACTUATOR_REQUEST_END_OF_MESSAGE);
			} 
			request.setSensorType("Logic");

		} else if(HttpServerLogic.isAnalogicValue(parser.getRequestURL())) {
			request.setRequest("Request Error");
			if(HttpServerLogic.isJsonRequest(parser)) {
				request.setRequest(HttpServerConfig.getActuatorRequestGetAnalogicalJson() + 
						HttpServerConstant.ACTUATOR_REQUEST_END_OF_MESSAGE);
				
			} else if (HttpServerLogic.isXmlRequest(parser)) {
				request.setRequest(HttpServerConfig.getActuatorRequestGetAnalogicalXml() +
						HttpServerConstant.ACTUATOR_REQUEST_END_OF_MESSAGE);
				
			} else {
				request.setRequest(HttpServerConfig.getActuatorRequestGetAnalogicalXml() +
						HttpServerConstant.ACTUATOR_REQUEST_END_OF_MESSAGE);
			} 
			request.setSensorType("Analogic");

		} else {
			request.setRequest("Request Error");
			request.setSensorType("Sensor Type Error");
			System.out.println(request.getRequest());
			System.out.println(request.getSensorType());
		}
	}
	
	public static void parseActuatorResponse(HttpParser parser, ActuatorClientRequest request) {
		if (HttpServerLogic.isLogicValue(parser.getRequestURL())) {
			if (HttpServerLogic.isJsonRequest(parser)) {
				request.setValue(ActuatorClientParser.getVaueFromJSNResponse(request.getResponse()));
				request.setFormat("JSON");
				
			} else if (HttpServerLogic.isXmlRequest(parser)) {
				request.setValue(ActuatorClientParser.getVaueFromXMLResponse(request.getResponse()));
				request.setFormat("XML");
				
			} else {
				request.setValue(ActuatorClientParser.getVaueFromXMLResponse(request.getResponse()));
				request.setFormat("XML");
			}
			
		} else if (HttpServerLogic.isAnalogicValue(parser.getRequestURL())) {
			if (HttpServerLogic.isJsonRequest(parser)) {
				request.setValue(ActuatorClientParser.getVaueFromJSNResponse(request.getResponse()));
				request.setFormat("JSON");
					
			} else if (HttpServerLogic.isXmlRequest(parser)) {
				request.setValue(ActuatorClientParser.getVaueFromXMLResponse(request.getResponse()));
				request.setFormat("XML");
					
			} else {
				request.setValue(ActuatorClientParser.getVaueFromXMLResponse(request.getResponse()));
				request.setFormat("XML");
			}
			
		} else {
			request.setValue("Value Error");
			request.setFormat("Format Error");
			System.out.println(request.getValue());
			System.out.println(request.getFormat());
		}
	}
}
