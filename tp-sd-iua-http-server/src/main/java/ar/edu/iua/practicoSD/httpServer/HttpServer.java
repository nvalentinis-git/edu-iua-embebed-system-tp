package ar.edu.iua.practicoSD.httpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ar.edu.iua.practicoSD.util.HttpServerConfig;

public class HttpServer {

	private static ServerSocket httpServer;
	
	public static void main(String[] args) {
			
		try {
			
			httpServer = new ServerSocket(HttpServerConfig.getHttpServerPortNumber());
			System.out.println("Http Server Started at: " + HttpServerConfig.getHttpServerPortNumber() + "\n");
			
			while(true) {				
				Socket httpClientSocket = httpServer.accept();
				System.out.println("New HTTP request established");
				
				new Thread(new HttpServerSession(httpClientSocket)).start();
			}

		} catch(IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				httpServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	
	
	
	
	
}


