package net.tokyo_ct.meister2015.jellyfish.test;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.font.Font16;

public class Font16Test {
	@Test
	public void test() {
		Font16 f = new Font16();
		f.c = 'A';
		f.toImage();
	}
}
