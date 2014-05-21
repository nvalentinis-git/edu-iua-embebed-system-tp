package ar.edu.iua.practicoSD.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlCreator {
	public static String createHttpResponseHeader() {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("HTTP/1.1 200 OK\n");
		String dateFormated = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
		htmlBuilder.append("Date: " + dateFormated + "\n");
		htmlBuilder.append("Content-Type: text/html\n");
		htmlBuilder.append("Content-Length: 9999" + "\n");
		htmlBuilder.append("\n");
		htmlBuilder.append("\n");
		return htmlBuilder.toString();
	}
	
	public static String createHtmlResponse(String format, String response, 
			String sensorType, String value, String realoadTime) {
		
		StringBuilder htmlBuilder = new StringBuilder(createHttpResponseHeader());
		
		htmlBuilder.append("<html>");
		htmlBuilder.append("<head><title>TP Final SD.</title></head>");
		htmlBuilder.append("<body>");
		
		if (!HttpServerConfig.EMPTY_STRING.equals(realoadTime)) {
			htmlBuilder.append("<script type=\"text/javascript\">");						
			htmlBuilder.append("window.setInterval(function() {location.reload(true)},"+ Integer.parseInt(realoadTime)*1000  +");");
			htmlBuilder.append("</script>");
		}
		
		htmlBuilder.append("<p><h1  style=\"color: blue; text-align: center;\"> Sistema de Medicion Distribuido</h1>");
		htmlBuilder.append("<p><h1  style=\"color: blue;\"> Estado del Modulo de Sensores </h1></p>");
		
		htmlBuilder.append("<table style=\"width:80%; font-size: 20;\"> "+
			"<tr>"+
			"  <td style=\"width:320px; font-weight:bold;\">Tipo de Sensor</td> " +
			"  <td>" + sensorType + "</td>" +		
			"</tr>" +
			"<tr>" +
			"  <td style=\"width:320px; font-weight:bold;\">Valor de Sensor</td>" +
			"  <td>" + value + "</td>" +		
			"</tr>" +
			"<tr>" +
			"  <td style=\"width:320px; font-weight:bold;\">Tipo de Formato</td>" +
			"  <td>" + format + "</td>" +		
			"</tr>" + 
			"<tr>" + 
			"  <td style=\"width:320px; font-weight:bold;\">Rta. Modulo Sensores</td>" +
			"  <td>" + StringEscapeUtils.escapeHtml4(response) + "</td>" +		
			"</tr>" +
			"</table>");
		
		htmlBuilder.append("<p><h1  style=\"color: blue;\"> Estado de la Pagina </h1></p>");
		
		String dateFormated = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
		htmlBuilder.append("<table style=\"width:80%; font-size: 20;\"> ");
		
		htmlBuilder.append("<tr> " +
				"  <td style=\"width:320px; font-weight:bold; \">Ultima Actualizacion: </td> " +
				"  <td>" + dateFormated + "</td> " +		 
				"</tr>");	
		
		if (!HttpServerConfig.EMPTY_STRING.equals(realoadTime)) {
			htmlBuilder.append("<tr>" +
					"  <td style=\"width:320px; font-weight:bold;\">Recarga Automatica cada: </td>" +
					"  <td>" + realoadTime + " seg. </td>" +		
					"</tr>");
		}
		
		htmlBuilder.append("</table>");
	
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");
		
		return htmlBuilder.toString();
	}
		
	public static String createErrorHtmlResponse(String msg) {
		StringBuilder htmlBuilder = new StringBuilder(createHttpResponseHeader());
		htmlBuilder.append("<html>");
		htmlBuilder.append("<head><title>TP Final SD.</title></head>");
		htmlBuilder.append("<body>");
		htmlBuilder.append("<p><h2  style=\"color: red;\"> " + msg + " </h2></p>");
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");
		return htmlBuilder.toString();
	}
	
	public static String createSensorConfigurationResponse(String ip, String puerto) {
		StringBuilder htmlBuilder = new StringBuilder(createHttpResponseHeader());
		htmlBuilder.append("<html>");
		htmlBuilder.append("<head><title>TP Final SD.</title></head>");
		htmlBuilder.append("<body>");
		htmlBuilder.append("<p><h2  style=\"color: black;\"> Nuevo Host   del TCP Server:  "  + ip + " </h2></p>");
		htmlBuilder.append("<p><h2  style=\"color: black;\"> Nuevo Puerto del TCP Server:  " + puerto + " </h2></p>");
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");
		return htmlBuilder.toString();
	}
}
