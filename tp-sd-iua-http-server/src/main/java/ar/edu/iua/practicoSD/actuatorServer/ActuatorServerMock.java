package ar.edu.iua.practicoSD.actuatorServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import ar.edu.iua.practicoSD.util.HttpServerConfig;

public class ActuatorServerMock {
	
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		
		try {
			 serverSocket = new ServerSocket(HttpServerConfig.getActuatorClientPortNumber());
			 System.out.println("Actuator Server Mock Started at: " + HttpServerConfig.getActuatorClientPortNumber()+"\n");
			 
			while(true) {			
				Socket client = serverSocket.accept();
				System.out.println("New Connection established");
				
				new Thread(new ActuatorServerMockSession(client)).start();				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static String getResponse(String request) {
		String response = "error";
		
		if (HttpServerConfig.getActuatorRequestGetAnalogicalJson().equalsIgnoreCase(request) ) {
						
			response = "{ \"type\":\"analogic\", \"meta\":\"sensor2\", \"value\":0.67 }\n";
			
		} else if (HttpServerConfig.getActuatorRequestGetAnalogicalXml().equalsIgnoreCase(request) ) {
						
			response = "<response type=\"analogic\" meta=\"sensor2\">0.67</response>\n";

		} else if (HttpServerConfig.getActuatorRequestGetLogicalJson().equalsIgnoreCase(request) ) {
			
			response = "{ \"type\":\"logic\", \"meta\":\"sensor1\", \"value\":1 }\n";

		} else if (HttpServerConfig.getActuatorRequestGetLogicalXml().equalsIgnoreCase(request) ) {
			
			response = "<response type=\"logic\" meta=\"sensor1\">1</response>\n";
		}
		
		return response;
	}
	
	public static class ActuatorServerMockSession implements Runnable {
		
		private Socket client;
		
		public ActuatorServerMockSession(Socket client) {
			this.client = client;
		}
		
		@Override
		public void run() {
			try {
				
				BufferedReader input = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
				DataOutputStream out = new DataOutputStream( client.getOutputStream() );
				
				String request = input.readLine();								
				System.out.println("Request: " + request);
				
				String response = getResponse(request);				
				System.out.println("Response: " + response);
				
				out.writeBytes(response);
				
				input.close();
				out.close();
				client.close();
				
			}  catch (IOException e) {
				e.printStackTrace();
			}	
			
		}
	}
		
}
