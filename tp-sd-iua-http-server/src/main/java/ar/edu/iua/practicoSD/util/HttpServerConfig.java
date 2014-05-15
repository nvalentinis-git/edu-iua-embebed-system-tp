package ar.edu.iua.practicoSD.util;

public class HttpServerConfig {

	// 192.168.1.10[0-20]
	
	public static final String SERVER_PORT_NUMBER = "8000";
	
	// 8001
	public static final String ACTUATOR_CLIENT_PORT_NUMBER = "8001";	
	
	// 192.168.1.50 [0-20]
	public static final String ACTUATOR_CLIENT_HOST_NUMBER = "localhost";
	
	
	public static int getHttpServerPortNumber() {
		return Integer.parseInt(System.getProperty("httpServerPortNumber", SERVER_PORT_NUMBER));
	}
	
	public static int getActuatorClientPortNumber() {		
		return Integer.parseInt(System.getProperty("actuatorClientPortNumber", ACTUATOR_CLIENT_PORT_NUMBER));	
	}
	
	public static String getActuatorClientHostNumber() {
		return System.getProperty("actuatorClientHostNumber", ACTUATOR_CLIENT_HOST_NUMBER);
	}
	
	public static String getActuatorRequestGetLogicalJson() {
		return System.getProperty("actuatorClientLogicalJson", 
				HttpServerConstant.ACTUATOR_REQUEST_GET_LOGICAL_VALUE_JSON);
	}
	
	public static String getActuatorRequestGetLogicalXml() {
		return System.getProperty("actuatorClientLogicalXml", 
				HttpServerConstant.ACTUATOR_REQUEST_GET_LOGICAL_VALUE_XML);
	}
	
	public static String getActuatorRequestGetAnalogicalJson() {
		return System.getProperty("actuatorClientAnalogicalJson", 
				HttpServerConstant.ACTUATOR_REQUEST_GET_ANALOGICAL_VALUE_JSON);
	}
	
	public static String getActuatorRequestGetAnalogicalXml() {
		return System.getProperty("actuatorClientAnalogicalXml", 
				HttpServerConstant.ACTUATOR_REQUEST_GET_ANALOGICAL_VALUE_XML);
	}	
}
