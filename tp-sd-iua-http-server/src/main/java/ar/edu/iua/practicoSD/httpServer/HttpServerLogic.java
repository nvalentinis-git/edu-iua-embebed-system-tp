package ar.edu.iua.practicoSD.httpServer;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.InetAddressValidator;


import ar.edu.iua.practicoSD.util.HtmlCreator;
import ar.edu.iua.practicoSD.util.HttpParser;
import ar.edu.iua.practicoSD.util.HttpServerConfig;
import ar.edu.iua.practicoSD.util.HttpServerConstant;

public class HttpServerLogic {

	public static boolean isValidURL(String url) {		
		return isAnalogicValue(url) || 
				isLogicValue(url) || 
				isSensorTCPConfig(url);		
	}
	
	public static boolean isAnalogicValue(String url) {
		return url.contains("getAnalogicValue");
	}
	
	public static boolean isLogicValue(String url) {
		return url.contains("getLogicValue");
	}
	
	public static boolean isSensorTCPConfig(String url) {
		return url.contains("sensorConfig");
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
	
	public static void createSensorTCPRequest(HttpParser parser, SensorTCPClientRequest request) {
		if (HttpServerLogic.isLogicValue(parser.getRequestURL())) {
			request.setRequest("Request Error");
			if(HttpServerLogic.isJsonRequest(parser)) {
				request.setRequest(HttpServerConfig.getSensorTCPCommandGetLogicalJson() + 
						HttpServerConstant.SENSOR_TCP_REQUEST_END_OF_MESSAGE);
			
			} else if(HttpServerLogic.isXmlRequest(parser)) {
				request.setRequest(HttpServerConfig.getSensorTCPCommandGetLogicalXml() + 
						HttpServerConstant.SENSOR_TCP_REQUEST_END_OF_MESSAGE); 
			
			} else {			
				request.setRequest(HttpServerConfig.getSensorTCPCommandGetLogicalJson() +
						 HttpServerConstant.SENSOR_TCP_REQUEST_END_OF_MESSAGE);
			} 
			request.setSensorType("Logic");

		} else if(HttpServerLogic.isAnalogicValue(parser.getRequestURL())) {
			request.setRequest("Request Error");
			if(HttpServerLogic.isJsonRequest(parser)) {
				request.setRequest(HttpServerConfig.getSensorTCPCommandGetAnalogicalJson() + 
						HttpServerConstant.SENSOR_TCP_REQUEST_END_OF_MESSAGE);
				
			} else if (HttpServerLogic.isXmlRequest(parser)) {
				request.setRequest(HttpServerConfig.getSensorTCPCommandGetAnalogicalXml() +
						HttpServerConstant.SENSOR_TCP_REQUEST_END_OF_MESSAGE);
				
			} else {
				request.setRequest(HttpServerConfig.getSensorTCPCommandGetAnalogicalJson() +
						HttpServerConstant.SENSOR_TCP_REQUEST_END_OF_MESSAGE);
			} 
			request.setSensorType("Analogic");

		} else {
			request.setRequest("Request Error");
			request.setSensorType("Sensor Type Error");
			System.out.println(request.getRequest());
			System.out.println(request.getSensorType());
		}
	}
	
	public static void parseSensorTCPResponse(HttpParser parser, SensorTCPClientRequest request) {
		if (HttpServerLogic.isLogicValue(parser.getRequestURL())) {
			if (HttpServerLogic.isJsonRequest(parser)) {
				request.setValue(SensorTCPClientParser.getVaueFromJSNResponse(request.getResponse()));
				request.setFormat("JSON");
				
			} else if (HttpServerLogic.isXmlRequest(parser)) {
				request.setValue(SensorTCPClientParser.getVaueFromXMLResponse(request.getResponse()));
				request.setFormat("XML");
				
			} else {
				request.setValue(SensorTCPClientParser.getVaueFromJSNResponse(request.getResponse()));
				request.setFormat("JSON");
			}
			
			String value = request.getValue();
			request.setValue(value + getLogicString( value  ) );
			
		} else if (HttpServerLogic.isAnalogicValue(parser.getRequestURL())) {
			if (HttpServerLogic.isJsonRequest(parser)) {
				request.setValue(SensorTCPClientParser.getVaueFromJSNResponse(request.getResponse()));
				request.setFormat("JSON");
					
			} else if (HttpServerLogic.isXmlRequest(parser)) {
				request.setValue(SensorTCPClientParser.getVaueFromXMLResponse(request.getResponse()));
				request.setFormat("XML");
					
			} else {
				request.setValue(SensorTCPClientParser.getVaueFromJSNResponse(request.getResponse()));
				request.setFormat("JSON");
			}
			
		} else {
			request.setValue("Value Error");
			request.setFormat("Format Error");
			System.out.println(request.getValue());
			System.out.println(request.getFormat());
		}
	}

	public static String handleSensorServerTCPConfiguration(HttpParser parser) {
		
		if ( isSensorTCPConfig(parser.getRequestURL()) ) {
			
			String host = parser.getParam("sensorTCPHost");
			if (host != null && 
					new InetAddressValidator().isValidInet4Address( host ) ) {
				
				HttpServerConfig.setSensorTCPHost(host);
			} else {
				return HtmlCreator.createErrorHtmlResponse("El Parametro 'sensorTCPHost' es nulo o no cumple con el formato de una IP valida ");
			}
			
			String port = parser.getParam("sensorTCPPort");
			if (port != null && 
					NumberUtils.isDigits(port)) {
				
				HttpServerConfig.setSensorTCPPortNumber(port);
			} else {
				return HtmlCreator.createErrorHtmlResponse("El Parametro 'sensorTCPPort' es nulo o no cumple con el formato de un puerto ");
			}
			
			// configuration ok
			return HtmlCreator.createSensorConfigurationResponse(host,port);
		}
		
		// not the configuration page
		return HttpServerConstant.EMPTY_STRING;
	}
	
	private static String getLogicString(String value) {
		
		String logicStr = "";
		
		if ( "1".equalsIgnoreCase(value)) {
			
			logicStr = " (ON) "; 
			
		} else if ( "0".equalsIgnoreCase(value)) {
			
			logicStr = " (OFF) ";
		}
		
		return logicStr;
	}
}

















