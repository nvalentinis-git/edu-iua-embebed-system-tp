package ar.edu.iua.practicoSD.httpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
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
			
			// handle the sensor server TCP configuration			
			if(HttpServerConstant.EMPTY_STRING.equalsIgnoreCase(httpResponse)) {
				httpResponse = HttpServerLogic.handleSensorServerTCPConfiguration(parser);
			}
				
			SensorTCPClientRequest request = new SensorTCPClientRequest();			
			// process the Ok request
			if(HttpServerConstant.EMPTY_STRING.equalsIgnoreCase(httpResponse)) {
				
				try { 
										
					// create a communication with the Sensor TCP Server, time out 5 seg
					Socket sensorTCPServerSocket = new Socket();
					sensorTCPServerSocket.connect(
							new InetSocketAddress(
									HttpServerConfig.getSensorTCPHostNumber(), 
									HttpServerConfig.getSensorTCPPortNumber()), 
									5000);
					
					// for read tiem out
					sensorTCPServerSocket.setSoTimeout(5000);
					
					BufferedReader inputStreamTCPServer = new BufferedReader( new InputStreamReader( sensorTCPServerSocket.getInputStream() ) );
					DataOutputStream outputStreamTCPServer = new DataOutputStream( sensorTCPServerSocket.getOutputStream() ); 
					
					//create the Sensor TCP request 
					HttpServerLogic.createSensorTCPRequest(parser, request);
					
					//wait to complete the socket link with the Actuator
					Thread.sleep(300);
					
					//request to the Sensor TCP Server
					if (!"Request Error".equalsIgnoreCase(request.getRequest())) {
						outputStreamTCPServer.writeBytes(request.getRequest());
						outputStreamTCPServer.flush();
					}
					
					//get the response from the Sensor TCP Server
					String actuatorResponse = inputStreamTCPServer.readLine();
					request.setResponse(actuatorResponse);
					
					//parse the response
					HttpServerLogic.parseSensorTCPResponse(parser, request);
					
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
					httpResponse = HtmlCreator.createErrorHtmlResponse("Error de conexion con el Modulo de Sensores - Server TCP <br> " + 
							"IP configurada del Modulo de Sensores: " + HttpServerConfig.getSensorTCPHostNumber() + "<br> " + 
							"Pueto configurado del Modulo de Sensores: " + HttpServerConfig.getSensorTCPPortNumber() );
				}				
			}			
			
			// send the response
			outputStream.writeBytes(httpResponse);
			outputStream.flush();
			
			// log the activity		
			System.out.println("Method:                     " + parser.getMethod());
			System.out.println("URL:                        " + parser.getRequestURL());
			System.out.println("Actuator request sent:      " + request.getRequest());
			System.out.println("Actuator response received: " + request.getResponse());
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
