package pw.vodes.nhentai.connection;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import pw.vodes.nhentai.Finals;
import pw.vodes.nhentai.NHentai;

public class Connection {

	private static final Response DEFAULT_RESPONSE = new Response("Connection failed", -1);

	public static Response getResponse(String url) {
		try {
			return getResponse(new URL(url.replaceAll(" ", "%20")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return DEFAULT_RESPONSE;
	}

	public static Response getResponse(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", Finals.USER_AGENT);
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			int code = connection.getResponseCode();
			if (code != 200) {
				return new Response(connection.getResponseMessage(), code);
			}
			Scanner scan = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A");
			String result = scan.next();
			scan.close();
			return new Response(result, code);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return DEFAULT_RESPONSE;
	}

}
