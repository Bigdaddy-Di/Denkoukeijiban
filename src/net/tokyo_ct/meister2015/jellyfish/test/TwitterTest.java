package net.tokyo_ct.meister2015.jellyfish.test;

import net.tokyo_ct.meister2015.jellyfish.twitter.TwitterStreaming;

import org.junit.Test;

public class TwitterTest {

	@Test
	public void test() {
		TwitterStreaming ts = new TwitterStreaming(null, null);
		String[] list={"木下"};
		ts.start();
		ts.setQuery(list);
	}

}
