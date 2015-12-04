package net.tokyo_ct.meister2015.jellyfish.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tokyo_ct.meister2015.jellyfish.sqlite.SQLiteManager;

import org.apache.commons.io.IOUtils;

import com.google.common.primitives.Bytes;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpTop implements HttpHandler {
	String id = "";

	@Override
	public void handle(HttpExchange he) throws IOException {
		String path = he.getRequestURI().getPath();
		OutputStream os = he.getResponseBody();
		SQLiteManager man = new SQLiteManager();
		String accessTokenName = "";
		String accessTokenSecret = "";

		if (path.matches("/")) {
			path = "/index.html";
		}
		if (path.endsWith("html")) {
			List<String> cookies = he.getRequestHeaders().get("Cookie");
			for (String cookie : cookies) {
				String[] parts = cookie.split("(=|;| )+");
				for (int i = 0; i < parts.length; i++) {
					if (parts[i].equals("ACCESS_TOKEN")) {
						accessTokenName = parts[i + 1];
					}
					if (parts[i].equals("ACCESS_TOKEN_SECRET")) {
						accessTokenSecret = parts[i + 1];
					}
				}
			}

			System.out.println(accessTokenName);
			System.out.println(accessTokenSecret);

			id = man.accessToken(accessTokenName, accessTokenSecret);

			File top = new File("serverfiles/top");
			File file = new File("serverfiles/html" + path);
			if (!file.exists()) {
				System.out.println("nothing");
				return;
			}
			File bottom = new File("serverfiles/bottom");

			FileInputStream topFis = new FileInputStream(top);
			FileInputStream fileFis = new FileInputStream(file);
			FileInputStream bottomFis = new FileInputStream(bottom);

			byte[] buf = replace(
					IOUtils.toString(topFis) + IOUtils.toString(fileFis)
							+ IOUtils.toString(bottomFis)).getBytes();

			he.sendResponseHeaders(200, buf.length);
			os.write(buf);

			os.flush();
			os.close();

			topFis.close();
			fileFis.close();
			bottomFis.close();

		} else if (path.equals("/login")) {
			Map<String, String> params = queryToMap(he.getRequestURI()
					.getQuery());
			System.out.println(he.getRequestURI()
					.getQuery());
			String[] accessToken = man.login(params.get("id"),
					params.get("password"));

			System.out.println(params.get("id") + "," + params.get("password"));

			he.getResponseHeaders().add("Set-Cookie",
					"ACCESS_TOKEN=" + accessToken[0] + "; path=/;");
			he.getResponseHeaders().add("Set-Cookie",
					"ACCESS_TOKEN_SECRET=" + accessToken[1] + "; path=/;");
			he.getResponseHeaders().add("Location", "/");
			he.sendResponseHeaders(302, -1);
		} else {
			FileInputStream fileFis = new FileInputStream(new File(
					"serverfiles/" + path));
			byte[] buf = IOUtils.toByteArray(fileFis);
			he.sendResponseHeaders(200, buf.length);
			he.getResponseHeaders().add("Context-Type", "text/html");
			os.write(buf);
			os.flush();
			os.close();

		}
	}

	public Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

	public String replace(String str) {
		str = str.replace("[[ID]]", id.equals("") ? "ゲスト" : id);
		return str;
	}
}
