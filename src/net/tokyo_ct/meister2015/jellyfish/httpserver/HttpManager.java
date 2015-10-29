package net.tokyo_ct.meister2015.jellyfish.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class HttpManager {
	HttpServer svr;
	ExecutorService htp;

	public void run() {
		int port = 8080;
		try {
			svr = HttpServer.create(new InetSocketAddress("localhost", port),
					port);

			htp = Executors.newFixedThreadPool(1);
			svr.setExecutor(htp);
			svr.createContext("/", new HttpTop());
			svr.start();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void stop() {
		svr.stop(1);
		htp.shutdownNow();
	}
}
