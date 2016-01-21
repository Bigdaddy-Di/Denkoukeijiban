package net.tokyo_ct.meister2015.jellyfish.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.tokyo_ct.meister2015.jellyfish.httpserver.HttpManager;
import net.tokyo_ct.meister2015.jellyfish.twitter.TwitterStreaming;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import org.junit.Test;

public class ServerTest {
	@Test
	public void test() throws IOException, InterruptedException {
		HttpManager man = new HttpManager();
		TwitterStreaming ts = new TwitterStreaming(null, null);
		
		man.run();
		String str = "";
		while (!str.matches("quit")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			str = br.readLine();
		}
		man.stop();
	}
}
