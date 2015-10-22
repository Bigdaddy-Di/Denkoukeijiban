package net.tokyo_ct.meister2015.jellyfish.test;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.weather.Weather;

public class WeatherGetTest {

	@Test
	public void test() {
		Weather w = new Weather();
		String id = w.getId("新潟");
		w.getWeather(id);

	}

}
