package net.tokyo_ct.meister2015.jellyfish.test;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.clock.Clock;
import net.tokyo_ct.meister2015.jellyfish.font.Font16;

public class ClockTest {
	@Test
	public void test() {
		Clock c = new Clock();
		String time;
		Font16 f = new Font16();
		for (;;) {
			time = c.getTime();
			f.str = time;
			f.toImage();
			Font16Test.print(f);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

}
