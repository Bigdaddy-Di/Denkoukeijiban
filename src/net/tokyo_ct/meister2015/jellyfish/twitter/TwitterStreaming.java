package net.tokyo_ct.meister2015.jellyfish.twitter;

import java.util.Date;

import net.tokyo_ct.meister2015.jellyfish.main.Main;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreaming {
	TwitterStream twitterStream;
	FilterQuery query;

	String accessToken = "3900807690-pHvP8OO7aBZAf9ReO8SkOfS96yM4fzAVE9THqs6";
	String accessTokenSecret = "JO4e9DkH0wbgkrbJsVWYAlV84aONY3kHmEMzKWt3l5PkI";

	ConfigurationBuilder cb;

	public TwitterStreaming(String token, String tokenSec) {
		if (token != null || tokenSec != null) {
			this.accessToken = token;
			this.accessTokenSecret = tokenSec;
		}

		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("qorHi4DftvXsO2IyjxOxE3ylC")
				.setOAuthConsumerSecret(
						"7EMVdDXqopt7MhEQeHjj2bAFV4Y1qKW17nA6kKyrnbhvSgJAkM")
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret);
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	};

	public void start() {
		twitterStream.addListener(new UserStreamAdapter() {
			@Override
			public void onStatus(Status status) {
				long id = status.getId();
				String text = status.getText();
				long userId = status.getUser().getId();
				String username = status.getUser().getScreenName();
				Date whenTweeted = status.getCreatedAt();

				Main.getBouyomichan().talk(username + "さんからのツイート:" + text);
			}

		});

	}

	public void setQuery(String[] list) {
		System.out.println("Query Setting...");
		query = new FilterQuery();
		query.track(list);
		twitterStream.filter(query);
		try {
			Thread.sleep(300 * 1000L);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ストリームをやめます
			twitterStream.shutdown();
		}
	}

	public void setQuery(long[] list) {
		System.out.println("Query Setting...");
		query = new FilterQuery(list);
		twitterStream.filter(query);
		try {
			Thread.sleep(300 * 1000L);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ストリームをやめます
			twitterStream.shutdown();
		}
	}

}
