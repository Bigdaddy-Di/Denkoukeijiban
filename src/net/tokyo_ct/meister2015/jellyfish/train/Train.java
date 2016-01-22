package net.tokyo_ct.meister2015.jellyfish.train;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Train {
	String data;

	public Train(String data) {
		this.data = data;
	}

	public String getTrainsFromDay(String day) {
		System.out.println(data);
		Matcher m1 = Pattern.compile(".*\\[" + day + "\\].*", Pattern.MULTILINE).matcher(data);
		Matcher m2 = Pattern.compile("H\\sI", Pattern.MULTILINE).matcher(data);

		String str = null;
		int start = 0;
		int end = 0;
		if (m1.find()) {
			start = m1.start();
		}

		m2.region(start, data.length());
		if (m2.find()) {
			end = m2.end();
		}
		data = data.substring(start, end);
		return data;
	}

	public String[] getTrainsFromHour(String hour) {
		Matcher m1 = Pattern.compile("^" + hour + ".*$", Pattern.MULTILINE).matcher(data);
		String str = "";
		if (m1.find()) {
			str = m1.group(0);
		}
		return str.split(":| ");

	}

}
