package net.tokyo_ct.meister2015.jellyfish.weather;

import java.net.MalformedURLException;
import java.net.URL;

public class Weather {
	public static void main(String[] args){ 
		String locationud = ("35.6392733,139.2966618"); //緯度経度
		try {
			URL url = new URL("http://weather.olp.yahooapis.jp/v1/place?appid=");
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} //暫定的に高専の緯度経度を指定
		}
	
	static String location(String placekeyword){
		
		return placekeyword;
		
	}
			
	
}