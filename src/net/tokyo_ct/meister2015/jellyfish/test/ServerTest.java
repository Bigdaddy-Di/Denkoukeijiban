package net.tokyo_ct.meister2015.jellyfish.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.tokyo_ct.meister2015.jellyfish.httpserver.HttpManager;

import org.junit.Test;

public class ServerTest {
	@Test
	public void test() throws IOException, InterruptedException {
		HttpManager man = new HttpManager();

		man.run();
		String str = "";
		while (!str.matches("quit")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			str = br.readLine();
		}
		man.stop();
	}
}
