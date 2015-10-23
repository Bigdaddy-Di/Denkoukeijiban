package net.tokyo_ct.meister2015.jellyfish.train;

import java.net.MalformedURLException;
import java.net.URL;

public class Train {
	URL searchURL;
	String staName;
	
	private Train() {
		try {
			this.searchURL = new URL("http://www.ekikara.jp/cgi-bin/find.cgi");
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public Train(String staName){
		this();
		this.staName=staName;
	}
}
