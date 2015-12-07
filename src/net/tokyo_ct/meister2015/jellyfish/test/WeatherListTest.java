package net.tokyo_ct.meister2015.jellyfish.test;

import java.util.Arrays;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.httpserver.HttpTop;
import net.tokyo_ct.meister2015.jellyfish.weather.Weather;

public class WeatherListTest {

	@Test
	public void test() {
		System.out.println(new HttpTop().locations());
	}

}
