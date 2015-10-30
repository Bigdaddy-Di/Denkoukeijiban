package net.tokyo_ct.meister2015.jellyfish.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

import com.google.common.primitives.Bytes;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpTop implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		String path = he.getRequestURI().getPath();
		OutputStream os = he.getResponseBody();
		if (path.matches("/")) {
			path = "/index.html";
		}
		if (path.endsWith("html")) {
			System.out.println(path);
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

			StringBuilder sb = new StringBuilder();

			byte[] buf = Bytes.concat(IOUtils.toByteArray(topFis),
					IOUtils.toByteArray(fileFis),
					IOUtils.toByteArray(bottomFis));

			he.sendResponseHeaders(200, buf.length);
			he.getResponseHeaders().add("Context-Type", "text/html");
			os.write(buf);

			os.flush();
			os.close();

			topFis.close();
			fileFis.close();
			bottomFis.close();

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

}
