package net.tokyo_ct.meister2015.jellyfish.test;

import net.tokyo_ct.meister2015.jellyfish.bouyomichan.Bouyomichan;
import net.tokyo_ct.meister2015.jellyfish.clock.Clock;
import net.tokyo_ct.meister2015.jellyfish.font.Font16;
import net.tokyo_ct.meister2015.jellyfish.twitter.TwitterStreaming;
import net.tokyo_ct.meister2015.jellyfish.weather.Weather;

import org.junit.Test;

public class Sample {
	static Bouyomichan b = new Bouyomichan();
	static Font16 f = new Font16();
	static Clock c = new Clock();

	public static void main(String args[]) {
		for (;;) {
			String time = c.getTime();
			f.str = time;
			f.toImage();

			Font16Test.print(f);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			System.out.println("\033[2J");
		}

	}
}
