package net.tokyo_ct.meister2015.jellyfish.clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Clock {
	public String getDate(){
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		return date.format(calendar.getTime());
	}
}
	
