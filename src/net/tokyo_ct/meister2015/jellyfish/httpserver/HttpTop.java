package net.tokyo_ct.meister2015.jellyfish.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpTop implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		String path = he.getRequestURI().getPath();
		if (path.equals("/")) {
			path = "index.html";
		}
		File file = new File("serverfiles", path);

		FileInputStream fis = new FileInputStream(file);

		he.getResponseHeaders().add("Context-Type", "text/html");
		he.sendResponseHeaders(200, file.length());
		OutputStream os = he.getResponseBody();
		byte[] bs = new byte[1024];
		int d = 0;

		while ((d = fis.read(bs)) >= 0) {
			os.write(bs, 0, d);
		}
		os.flush();
		os.close();
		fis.close();

	}

}
