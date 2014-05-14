package ar.edu.iua.practicoSD.httpServer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActuatorClientParser {

	public static String getVaueFromXMLResponse(String response) {
	
		String value = "";
		Pattern pattern = Pattern.compile(">(.*)<");
		Matcher matcher = pattern.matcher(response);
		if (matcher.find()) {
			value = matcher.group(1);
		}
		return value;
	}
	
	public static String getVaueFromJSNResponse(String response) {
		
		String value = "";
		Pattern pattern = Pattern.compile("\"value\":(\\d*\\.*\\d*)");
		Matcher matcher = pattern.matcher(response);
		if (matcher.find()) {
			value = matcher.group(1);
		}
		return value;
	}
	
	//test
	public static void main(String[] args) {

		System.out.println( getVaueFromXMLResponse("<response type=\"analogic\" meta=\"sensor2\">0.67</response>") );
		System.out.println( getVaueFromXMLResponse("<response type=\"logic\" meta=\"sensor1\">1</response>") );
		System.out.println( getVaueFromJSNResponse("{ \"type\":\"logic\", \"meta\":\"sensor1\", \"value\":1 }") );
		System.out.println( getVaueFromJSNResponse("{ \"type\":\"analogic\", \"meta\":\"sensor2\", \"value\":0.67}") );

	}
}
