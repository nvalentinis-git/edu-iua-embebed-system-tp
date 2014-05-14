package ar.edu.iua.practicoSD.httpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import ar.edu.iua.practicoSD.util.ErrorHandler;
import ar.edu.iua.practicoSD.util.HtmlCreator;
import ar.edu.iua.practicoSD.util.HttpParser;
import ar.edu.iua.practicoSD.util.HttpServerConfig;
import ar.edu.iua.practicoSD.util.HttpServerConstant;

public class HttpServerSession implements Runnable {

	private Socket socketConnection;

	public HttpServerSession(Socket socketConnection) {
		this.socketConnection = socketConnection;
	}
	
	@Override
	public void run() {
		
		try {
		
			InputStream inputStream = socketConnection.getInputStream();
			DataOutputStream outputStream = new DataOutputStream( socketConnection.getOutputStream() );  
			
			HttpParser parser = new HttpParser(inputStream);
			parser.parseRequest();
				
			String httpResponse = ErrorHandler.handleError(parser);
			
			ActuatorClientRequest request = new ActuatorClientRequest();
			
			// process the Ok request
			if(HttpServerConstant.EMPTY_STRING.equalsIgnoreCase(httpResponse)) {
				
				try { 
										
					// create a communication with the Actuator
					Socket actuatorClientSocket = new Socket(
							HttpServerConfig.getActuatorClientHostNumber(),
							HttpServerConfig.getActuatorClientPortNumber());
					
					BufferedReader inputStreamActuator = new BufferedReader( new InputStreamReader( actuatorClientSocket.getInputStream() ) );
					DataOutputStream outputStreamActuator = new DataOutputStream( actuatorClientSocket.getOutputStream() ); 
					
					//create the Actuator request 
					HttpServerLogic.createActuatorRequest(parser, request);
					
					//request to the Actuator Server
					if (!"Request Error".equalsIgnoreCase(request.getRequest())) {
						outputStreamActuator.writeBytes(request.getRequest());
						outputStreamActuator.flush();
					}
					
					//get the response from the Actuator Server
					String actuatorResponse = inputStreamActuator.readLine();
					request.setResponse(actuatorResponse);
					
					//parse the response
					HttpServerLogic.parseActuatorResponse(parser, request);
					
					//add the automatic reload
					String reloadTime = "";
					if (parser.getParam("reload") != null && 
							!HttpServerConstant.EMPTY_STRING.equalsIgnoreCase(parser.getParam("reload"))) {
						reloadTime = parser.getParam("reload");
					}
					
					httpResponse = HtmlCreator.createHtmlResponse(
							request.getFormat(),
							request.getResponse(),
							request.getSensorType(), 
							request.getValue(),
							reloadTime);
								
				} catch(Exception e) {
					e.printStackTrace();		
					httpResponse = HtmlCreator.createErrorHtmlResponse("Error al conectarse al Actuador");
				}				
			}			
			
			// send the response
			outputStream.writeBytes(httpResponse);
			outputStream.flush();
			
			// log the activity		
			System.out.println(parser.getMethod());
			System.out.println(parser.getRequestURL());
			System.out.println("Actuator request: " + request.getRequest());
			System.out.println("Actuator response: " + request.getResponse());
			System.out.println("--------------------------------------------\n");
			
			if(Boolean.parseBoolean(System.getProperty("debugMode", "false"))) {
				System.out.println(httpResponse);
			}		
			
			inputStream.close();
			outputStream.close();
			socketConnection.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
