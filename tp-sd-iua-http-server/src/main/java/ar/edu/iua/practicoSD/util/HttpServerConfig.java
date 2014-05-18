package ar.edu.iua.practicoSD.util;

public class HttpServerConfig {

	// 192.168.1.10[0-20]
	
	public static final String SERVER_PORT_NUMBER = "8000";
	
	// 8001
	public static final String SENSOR_TCP_PORT_NUMBER = "8001";	
	
	// 192.168.1.50 [0-20]
	public static final String SENSOR_TCP_HOST_NUMBER = "localhost";
	
	
	public static int getHttpServerPortNumber() {
		return Integer.parseInt(System.getProperty("httpServerPortNumber", SERVER_PORT_NUMBER));
	}
	
	// Sensor TCP Server Config
	public static void setSensorTCPPortNumber(String port) {
		System.setProperty("sensorTCPPortNumber", port);
	}
	
	public static int getSensorTCPPortNumber() {		
		return Integer.parseInt(System.getProperty("sensorTCPPortNumber",SENSOR_TCP_PORT_NUMBER));	
	}
	
	public static void setSensorTCPHost(String host) {
		System.setProperty("sensorTCPHostNumber", host);
	}
	
	public static String getSensorTCPHostNumber() {
		return System.getProperty("sensorTCPHostNumber", SENSOR_TCP_HOST_NUMBER);
	}
	// End Sensor TCP Server Config
	
	// Sensor TCP Commands
	public static String getSensorTCPCommandGetLogicalJson() {
		return System.getProperty("sensorTCPCommandLogicalJson", 
				HttpServerConstant.SENSOR_TCP_REQUEST_GET_LOGICAL_VALUE_JSON);
	}
	
	public static String getSensorTCPCommandGetLogicalXml() {
		return System.getProperty("sensorTCPCommandLogicalXml", 
				HttpServerConstant.SENSOR_TCP_REQUEST_GET_LOGICAL_VALUE_XML);
	}
	
	public static String getSensorTCPCommandGetAnalogicalJson() {
		return System.getProperty("sensorTCPCommandAnalogicalJson", 
				HttpServerConstant.SENSOR_TCP_REQUEST_GET_ANALOGICAL_VALUE_JSON);
	}
	
	public static String getSensorTCPCommandGetAnalogicalXml() {
		return System.getProperty("sensorTCPCommandAnalogicalXml", 
				HttpServerConstant.SENSOR_TCP_REQUEST_GET_ANALOGICAL_VALUE_XML);
	}
	// End Sensor TCP Commands
	

}
