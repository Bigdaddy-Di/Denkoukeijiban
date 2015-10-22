package net.tokyo_ct.meister2015.jellyfish.clock;

import java.util.Calendar;

public class Clock {
	static int h;
	static int m;
	static int s;

	String time;

	boolean clockactive = true;

	static Clock c = new Clock();
	Calendar now = Calendar.getInstance();

	public String getTime() {
		h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		m = Calendar.getInstance().get(Calendar.MINUTE);
		s = Calendar.getInstance().get(Calendar.SECOND);

		time = (h + ":" + m + ":" + s);
		if (s < 10 && m < 10 && h < 10) {
			time = ("0" + h + ":0" + m + ":0" + s);
		} else if (s < 10 && m < 10) {
			time = (h + ":0" + m + ":0" + s);
		} else if (h < 10 && s < 10) {
			time = ("0" + h + ":" + m + ":0" + s);
		} else if (m < 10 && h < 10) {
			time = ("0" + h + ":0" + m + ":" + s);
		} else if (h < 10) {
			time = ("0" + h + ":" + m + ":" + s);
		} else if (m < 10) {
			time = (h + ":0" + m + ":" + s);
		} else if (s < 10) {
			time = (h + ":" + m + ":0" + s);
		}

		return time;
	}

	public void stop() {
		clockactive = false;
	}
}
