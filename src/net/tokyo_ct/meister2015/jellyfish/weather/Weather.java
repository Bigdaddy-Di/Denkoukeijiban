package net.tokyo_ct.meister2015.jellyfish.weather;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.jayway.jsonpath.JsonPath;

import net.tokyo_ct.meister2015.jellyfish.main.Http;

public class Weather {

	public void getWeather(String id) {
		Http http = new Http("http://weather.livedoor.com/forecast/webservice/json/v1?city=" + id);
		String s = http.getToUrl();
		String tdWtr = JsonPath.read(s, "$.forecasts[0].telop");
		String maxTemp = JsonPath.read(s, "$.forecasts[1].temperature.max.celsius");
		String minTemp = JsonPath.read(s, "$.forecasts[1].temperature.min.celsius");
		String tmWtr = JsonPath.read(s, "$.forecasts[1].telop");

		System.out.println("今日の天気:" + tdWtr);
		System.out.println("明日の天気:" + tmWtr);
		System.out.println("明日の最高気温:" + maxTemp + "°C");
		System.out.println("明日の最低気温:" + minTemp + "°C");

	}

	public String getId(String city) {

		SAXReader sr = new SAXReader();
		Document doc;
		String id = null;
		try {
			doc = sr.read(new URL("http://weather.livedoor.com/forecast/rss/primary_area.xml"));
			List nodes = doc.selectNodes("/rss/channel/ldWeather:source/pref/city");
			for (Iterator<Node> i = nodes.iterator(); i.hasNext();) {
				Node cityNode = (Node) i.next();
				if (city.equals(cityNode.selectSingleNode("@title").getText())) {
					id = cityNode.selectSingleNode("@id").getText();
				}
			}

		} catch (MalformedURLException | DocumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return id;
	}

}