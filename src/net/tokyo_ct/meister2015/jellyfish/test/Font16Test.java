package net.tokyo_ct.meister2015.jellyfish.test;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.font.Font16;

public class Font16Test {
	@Test
	public void test() throws InterruptedException {
		Font16 f = new Font16();
		f.str = "あいうえおかきくけこさしすせそ";
		f.toImage();
		for (int x = 0; x < 16 * 8; x++) {
			//print(new Font16(f.toBoolean(x, 0), 16 * 8, 16));
			Thread.sleep(100);
		}
		System.out.println(f.ys);
	}

	public static void print(Font16 f) {
		for (int y = 0; y < f.height; y++) {
			for (int x = 0; x < f.width; x++) {
				System.out.print(f.data[x][y] ? "■" : "□");
				if (x == f.width - 1) {
					System.out.print("\n");
				}
			}
		}
	}
}