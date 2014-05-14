package ar.edu.iua.practicoSD.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlCreator {
	public static String createHttpResponseHeader() {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("HTTP/1.1 200 OK\n");
		String dateFormated = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
		htmlBuilder.append("Date: " + dateFormated + "\n"); // Wed, 31 Dec 2003 23:59:59 GMT TODO extract the date
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
		htmlBuilder.append("<head><title>IUA - Sistemas Embebidos - Sistemas Distribuidos - TP Final</title></head>");
		htmlBuilder.append("<body>");
		if (!HttpServerConstant.EMPTY_STRING.equals(realoadTime)) {
			htmlBuilder.append("<script type=\"text/javascript\">");
			htmlBuilder.append("function reFresh(){");
			htmlBuilder.append("location.reload(true)}");
			htmlBuilder.append("window.setInterval(\"reFresh()\","+ Integer.parseInt(realoadTime)*1000  +");");
			htmlBuilder.append("</script>");
		}
		htmlBuilder.append("<p><h1  style=\"color: blue;\"> El actuador se consulto con formato: " + format + " </h1></p>");
		htmlBuilder.append("<p><h2  style=\"color: black;\"> La respuesta del actuador fue: " + StringEscapeUtils.escapeHtml4(response) + " </h2></p>");
		htmlBuilder.append("<p><h2  style=\"color: black;\"> El valor del sensor tipo \"" + sensorType + "\", es: \"" + value + "\" </h2></p>");
		if (!HttpServerConstant.EMPTY_STRING.equals(realoadTime)) {
			htmlBuilder.append("<p><h4 style=\"color: grey;\"> La pagina se cargara automaticamente cada: "+ realoadTime +" segundos </h4</p>");
			String dateFormated = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z").format(new Date());
			htmlBuilder.append("<p><h4 style=\"color: grey;\"> Ultima Recarga fue: " + dateFormated + " </h4</p>");
		}
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");
		return htmlBuilder.toString();
	}
		
	public static String createErrorHtmlResponse(String msg) {
		StringBuilder htmlBuilder = new StringBuilder(createHttpResponseHeader());
		htmlBuilder.append("<html>");
		htmlBuilder.append("<head><title>IUA - Sistemas Embebidos - Sistemas Distribuidos - TP Final</title></head>");
		htmlBuilder.append("<body>");
		htmlBuilder.append("<p><h2  style=\"color: red;\"> " + msg + " </h2></p>");
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");
		return htmlBuilder.toString();
	}
}
