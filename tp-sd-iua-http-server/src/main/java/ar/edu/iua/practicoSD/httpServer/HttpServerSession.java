package ar.edu.iua.practicoSD.httpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;

import ar.edu.iua.practicoSD.util.ErrorHandler;
import ar.edu.iua.practicoSD.util.HtmlCreator;
import ar.edu.iua.practicoSD.util.HttpParser;
import ar.edu.iua.practicoSD.util.HttpServerConfig;

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
			if(HttpServerConfig.EMPTY_STRING.equalsIgnoreCase(httpResponse)) {
				httpResponse = HttpServerLogic.handleSensorServerTCPConfiguration(parser);
			}
				
			SensorTCPClientRequest request = new SensorTCPClientRequest();			
			// process the Ok request
			if(HttpServerConfig.EMPTY_STRING.equalsIgnoreCase(httpResponse)) {
				
				try { 
										
					// create a communication with the Sensor TCP Server, time out 5 seg
					Socket sensorTCPServerSocket = new Socket();
					sensorTCPServerSocket.connect(
							new InetSocketAddress(
									HttpServerConfig.getSensorTCPHostNumber(), 
									HttpServerConfig.getSensorTCPPortNumber()), 
									10000);
					
					// for read time out
					sensorTCPServerSocket.setSoTimeout(8000);
					
					BufferedReader inputStreamTCPServer = new BufferedReader( new InputStreamReader( sensorTCPServerSocket.getInputStream() ) );
					PrintWriter outputStreamTCPServer = new PrintWriter( sensorTCPServerSocket.getOutputStream(), true ); 
					
					//create the Sensor TCP request 
					HttpServerLogic.createSensorTCPRequest(parser, request);
					
					//wait to complete the socket link with the Sensor ServerTCP
					Thread.sleep(500);
					
					//request to the Sensor TCP Server
					if (!"Request Error".equalsIgnoreCase(request.getRequest())) {
						outputStreamTCPServer.println( request.getRequest() );
					}
					
					//get the response from the Sensor TCP Server
					//String sensorTCPServerResponse = inputStreamTCPServer.readLine();
					String sensorTCPServerResponse = readResponseFromInput( inputStreamTCPServer );
					request.setResponse(sensorTCPServerResponse);
						
					//wait to complete the read and the close connection with TCP Server
					Thread.sleep(100);
					outputStreamTCPServer.close();
					inputStreamTCPServer.close();
					sensorTCPServerSocket.close();
										
					//parse the response
					HttpServerLogic.parseSensorTCPResponse(parser, request);
												
					//add the automatic reload
					String reloadTime = "";
					if (parser.getParam("reload") != null && 
							!HttpServerConfig.EMPTY_STRING.equalsIgnoreCase(parser.getParam("reload"))) {
						reloadTime = parser.getParam("reload");
					}
					
					// check for error on the response
					if ( request.getResponse().contains("error") || 
						 request.getValue() == null || 
						 HttpServerConfig.EMPTY_STRING.equalsIgnoreCase(request.getValue() ) ) {
						
						System.out.println("Response from TCP Server: " + request.getResponse());
						httpResponse = HtmlCreator.createErrorHtmlResponse("Error en la Respuesta del Modulo de Sensores - Server TCP <br> " + 
								"Respuesta del Modulo de Sensores: " + request.getResponse() + "<br> " );
					} else {
						
						httpResponse = HtmlCreator.createHtmlResponse(
								request.getFormat(),
								request.getResponse(),
								request.getSensorType(), 
								request.getValue(),
								reloadTime);						
					}					
								
				} catch(Exception e) {
					e.printStackTrace(System.out);
					httpResponse = HtmlCreator.createErrorHtmlResponse("Error de conexion con el Modulo de Sensores - Server TCP <br> " + 
							"IP configurada del Modulo de Sensores: " + HttpServerConfig.getSensorTCPHostNumber() + "<br> " + 
							"Pueto configurado del Modulo de Sensores: " + HttpServerConfig.getSensorTCPPortNumber() );
				}				
			}			
			
			// send the response
			outputStream.writeBytes(httpResponse);
			outputStream.flush();
			
			// log the activity		
			System.out.println("Method:                      " + parser.getMethod());
			System.out.println("URL:                         " + parser.getRequestURL());
			System.out.println("ServerTCP request sent:      " + request.getRequest());
			System.out.println("ServerTCP response received: " + request.getResponse());
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
	
	private String readResponseFromInput( Reader input ) {
		
		String response = "error";
		String sensorResponse = "";
		try {
			int in = 0;
			boolean needToRead = true;
			
			while ( !input.ready() ) { 
				Thread.sleep(200);
			}
			
			while ( input.ready() && needToRead ) {
				in = input.read();
				sensorResponse += Character.toString( (char) in );
//				System.out.print(command);
//				System.out.print("\n");
				if (isValidResponse(sensorResponse)) {
					needToRead = false;
					response = sensorResponse;
				}						
			}
			
		} catch (Exception e) {
			e.printStackTrace();				
		}			
		
		return response;			
	}
	
	private boolean isValidResponse(String sensorResponse) {
			
		boolean valid = false;
		
		// xml response
		if ( sensorResponse.contains("</response>")) {
			valid = true;	
		}
		
		// json response
		if ( sensorResponse.contains("}")) {
			valid = true;	
		}
			
		return valid;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
