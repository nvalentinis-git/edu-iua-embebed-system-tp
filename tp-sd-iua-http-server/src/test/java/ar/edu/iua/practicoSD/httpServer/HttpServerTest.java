package ar.edu.iua.practicoSD.httpServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Test;


public class HttpServerTest {

	@Test
	public void testHttpServerGetAnalogicValue() throws Exception {
		
		String page = getContent("http://localhost:8000/getAnalogicValue");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Analogic"));
		Assert.assertTrue(page.contains("JSON"));
	}
	
	@Test
	public void testHttpServerGetAnalogicValueJSON() throws Exception {
		
		String page = getContent("http://localhost:8000/getAnalogicValue?format=json");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Analogic"));
		Assert.assertTrue(page.contains("JSON"));
	}
	
	@Test
	public void testHttpServerGetAnalogicValueXML() throws Exception {
		
		String page = getContent("http://localhost:8000/getAnalogicValue?format=xml");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Analogic"));
		Assert.assertTrue(page.contains("XML"));
	}
	
	@Test
	public void testHttpServerGetLogicValue() throws Exception {
		
		String page = getContent("http://localhost:8000/getLogicValue");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Logic"));
		Assert.assertTrue(page.contains("JSON"));
	}
	
	@Test
	public void testHttpServerGetLogicValueJSON() throws Exception {
		
		String page = getContent("http://localhost:8000/getLogicValue?format=json");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Logic"));
		Assert.assertTrue(page.contains("JSON"));
	}
	
	@Test
	public void testHttpServerGetLogicValueXML() throws Exception {
		
		String page = getContent("http://localhost:8000/getLogicValue?format=xml");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Logic"));
		Assert.assertTrue(page.contains("XML"));
	}
	
	@Test
	public void testHttpServerReloadFunction() throws Exception {
		
		String page = getContent("http://localhost:8000/getLogicValue?format=xml&reload=5");		
		Assert.assertTrue(page.contains("Sistema de Medicion Distribuido"));
		Assert.assertTrue(page.contains("Logic"));
		Assert.assertTrue(page.contains("XML"));
		Assert.assertTrue(page.contains("Recarga Automatica cada"));		
	}
		
	private String getContent(String urlParam) throws Exception {
		
		URL url = new URL(urlParam);
		InputStream content = url.openStream();
		BufferedReader contentReader = new BufferedReader(
				new InputStreamReader( content ) );
		
		String in = "";
		String page = "";
		while((in = contentReader.readLine()) != null) {
			page += in;
		}
		
		System.out.println(page);
		
		return page;
		
	}
	
}
