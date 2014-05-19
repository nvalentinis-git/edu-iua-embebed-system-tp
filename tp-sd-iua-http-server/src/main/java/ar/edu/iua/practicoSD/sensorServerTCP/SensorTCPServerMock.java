package ar.edu.iua.practicoSD.sensorServerTCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import ar.edu.iua.practicoSD.util.HttpServerConfig;

public class SensorTCPServerMock {
	
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		
		try {
			 serverSocket = new ServerSocket(HttpServerConfig.getSensorTCPPortNumber());
			 System.out.println("Sensor Server TCP Mock Started at: " + HttpServerConfig.getSensorTCPPortNumber()+"\n");
			 
			while(true) {			
				Socket client = serverSocket.accept();
				System.out.println("New Connection established");
				
				new Thread(new SensorTCPServerMockSession(client)).start();				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static String getResponse(String request) {
		String response = "error";
		
		if (HttpServerConfig.getSensorTCPCommandGetAnalogicalJson().equalsIgnoreCase(request) ) {
						
			response = "{ \"type\":\"analogic\", \"meta\":\"sensor2\", \"value\":0.67 }\n";
			
		} else if (HttpServerConfig.getSensorTCPCommandGetAnalogicalXml().equalsIgnoreCase(request) ) {
						
			response = "<response type=\"analogic\" meta=\"sensor2\">0.67</response>\n";

		} else if (HttpServerConfig.getSensorTCPCommandGetLogicalJson().equalsIgnoreCase(request) ) {
			
			response = "{ \"type\":\"logic\", \"meta\":\"sensor1\", \"value\":1 }\n";
									  
		} else if (HttpServerConfig.getSensorTCPCommandGetLogicalXml().equalsIgnoreCase(request) ) {
			
			response = "<response type=\"logic\" meta=\"sensor1\">1</response>\n";
		}
		
		return response;
	}
	
	public static class SensorTCPServerMockSession implements Runnable {
		
		private Socket client;
		
		public SensorTCPServerMockSession(Socket client) {
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
		
		private String readCommandFromRequestInput( Reader input ) {
			
			String response = "error";
			try {
				int in = 0;
				boolean needToRead = true;
				while ( input.ready() && needToRead ) {
					in = input.read();
					response += Character.toString( (char) in );
					needToRead = false;
				}
				
			} catch (Exception e) {
				e.printStackTrace();				
			}			
			
			return response;			
		}
	}
		
}
