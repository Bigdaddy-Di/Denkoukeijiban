package net.tokyo_ct.meister2015.jellyfish.test;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.font.Font16;

public class Font16Test {
	@Test
	public void test() {
		Font16 f = new Font16();
		f.str = "東京高専";
		boolean[][] d = f.toImage();
		for (int y = 0; y < Font16.height; y++) {
			for (int x = 0; x < Font16.width; x++) {
				System.out.print(d[x][y] ? "##" : "  ");
				if (x == Font16.width - 1) {
					System.out.print("\n");
				}
			}
		}
		System.out.println(f.ys);
	}
}
