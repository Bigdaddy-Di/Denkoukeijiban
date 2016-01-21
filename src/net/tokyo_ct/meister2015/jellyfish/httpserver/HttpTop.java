package net.tokyo_ct.meister2015.jellyfish.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tokyo_ct.meister2015.jellyfish.datamanager.AccessTokenManager;
import net.tokyo_ct.meister2015.jellyfish.datamanager.JsonManager;
import net.tokyo_ct.meister2015.jellyfish.weather.Weather;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpTop implements HttpHandler {
	String id = "";

	JsonManager jm = new JsonManager("setting.json");
	AccessTokenManager atm = new AccessTokenManager();

	@Override
	public void handle(HttpExchange he) throws IOException {
		File f = new File("setting.json");
		if (!f.exists()) {
			f.createNewFile();
		}
		
		jm.read();
		System.out.println("aaaa");
		String path = he.getRequestURI().getPath();
		OutputStream os = he.getResponseBody();
		String accessTokenName = "";
		String accessTokenSecret = "";
		Headers headers = he.getRequestHeaders();

		if (path.matches("/")) {
			path = "/index.html";
		}
		if (path.endsWith("html")) {
			List<String> cookies = new ArrayList<String>();
			cookies = headers.get("Cookie");

			if (cookies != null) {
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
			}

			System.out.println("ATN:" + accessTokenName);
			System.out.println("ATS:" + accessTokenSecret);
			System.out.println(atm.getId(accessTokenName + ","
					+ accessTokenSecret));
			atm.print();

			if (atm.getId(accessTokenName + "," + accessTokenSecret) != null) {
				id = atm.getId(accessTokenName + "," + accessTokenSecret);
			} else {
				id = "ゲスト";
			}

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
			System.out.println(he.getRequestURI().getQuery());
			String[] accessToken = new String[2];
			System.out.println(jm.writeToJson());

			if (jm.verification(params.get("id"), params.get("password"))) {
				accessToken[0] = RandomStringUtils.randomAlphabetic(10);
				accessToken[1] = RandomStringUtils.randomAlphabetic(15);
				atm.putAccessToken(params.get("id"), accessToken[0] + ","
						+ accessToken[1]);
				System.out.println(params.get("id") + "."
						+ params.get("password"));
			}

			he.getResponseHeaders().add("Set-Cookie",
					"ACCESS_TOKEN=" + accessToken[0] + "; path=/;");
			he.getResponseHeaders().add("Set-Cookie",
					"ACCESS_TOKEN_SECRET=" + accessToken[1] + "; path=/;");
			he.getResponseHeaders().add("Location", "/");
			he.sendResponseHeaders(302, -1);
		} else if (path.equals("/weather")) {
			Map<String, String> params = queryToMap(he.getRequestURI()
					.getQuery());
			System.out.println(he.getRequestURI().getQuery());
			jm.setLocale(Integer.parseInt(params.get("pref")),
					Integer.parseInt(params.get("city")));
			he.getResponseHeaders().add("Location", "/");
			he.sendResponseHeaders(302, -1);
		}else if(path.equals("/twitter")){
		    Twitter twitter = TwitterFactory.getSingleton();
		    twitter.setOAuthConsumer("3900807690-pHvP8OO7aBZAf9ReO8SkOfS96yM4fzAVE9THqs6", "JO4e9DkH0wbgkrbJsVWYAlV84aONY3kHmEMzKWt3l5PkI");
		    try {
				RequestToken requestToken = twitter.getOAuthRequestToken();
				he.getRequestHeaders().add("Location",requestToken.getAuthenticationURL());
				he.sendResponseHeaders(302, -1);
		    } catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
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
		if (str.contains("[[LOCATION_LIB]]")) {
			str = str.replace("[[LOCATION_LIB]]", locations());
		}
		return str;
	}

	public String locations() {
		StringBuilder sb = new StringBuilder();
		Weather w = new Weather();
		sb.append("[");
		for (String[] strs : w.getList()) {
			sb.append("[");
			for (String str : strs) {
				sb.append("\"");
				sb.append(str);
				sb.append("\",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("],");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");

		return sb.toString();

	}

	public static String hash(String source) {
		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(source.getBytes());
			byte[] hash = md.digest();
			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
