package net.tokyo_ct.meister2015.jellyfish.weather;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.jayway.jsonpath.JsonPath;

import net.tokyo_ct.meister2015.jellyfish.main.Http;

public class Weather {
	protected SAXReader sr = new SAXReader();
	protected Document doc;
	String id, tdWtr, maxTemp, minTemp, tmWtr = "";

	public void getData() {
		Http http = new Http(
				"http://weather.livedoor.com/forecast/webservice/json/v1?city="
						+ id);
		String s = http.getToUrl();
		tdWtr = JsonPath.read(s, "$.forecasts[0].telop");
		maxTemp = JsonPath.read(s, "$.forecasts[1].temperature.max.celsius");
		minTemp = JsonPath.read(s, "$.forecasts[1].temperature.min.celsius");
		tmWtr = JsonPath.read(s, "$.forecasts[1].telop");
	}

	public String getId(String city) {

		try {
			doc = sr.read(new URL(
					"http://weather.livedoor.com/forecast/rss/primary_area.xml"));
			List nodes = doc
					.selectNodes("/rss/channel/ldWeather:source/pref/city");
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

	public String[][] getList() {
		List<List<String>> list = new ArrayList<List<String>>();
		list.clear();
		try {
			doc = sr.read(new URL(
					"http://weather.livedoor.com/forecast/rss/primary_area.xml"));
			List prefs = doc.selectNodes("/rss/channel/ldWeather:source/pref");
			for (Iterator<Node> i = prefs.iterator(); i.hasNext();) {
				Node prefNode = (Node) i.next();

				List<String> pref = new ArrayList<String>();
				String title = prefNode.selectSingleNode("@title").getText();
				pref.add("title:" + title);
				List cities = doc
						.selectNodes("/rss/channel/ldWeather:source/pref[@title='"
								+ title + "']/city");
				for (Iterator<Node> j = cities.iterator(); j.hasNext();) {
					Node cityNode = (Node) j.next();
					pref.add(cityNode.selectSingleNode("@title").getText());
				}

				list.add(pref);
			}

		} catch (MalformedURLException | DocumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return toArray(list);
	}
	
	public String[][] toArray(List<List<String>> list){
		String[][] strList=new String[list.size()][];
		for(int i=0;i<list.size();i++){
			strList[i]=list.get(i).toArray(new String[0]);
		}
		return strList;
	}
}