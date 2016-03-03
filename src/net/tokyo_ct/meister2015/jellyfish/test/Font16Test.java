package net.tokyo_ct.meister2015.jellyfish.test;

import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.font.Font16;

public class Font16Test {
	@Test
	public void test() throws InterruptedException {
		// Font16 f = new Font16();
		// f.str = "あいうえおかきくけこさしすせそ";
		// f.toImage();
		// for (int x = 0; x < 16 * 8; x++) {
		// //print(new Font16(f.toBoolean(x, 0), 16 * 8, 16));
		// Thread.sleep(100);
		// }
		// System.out.println(f.ys);

		Font16 f = new Font16();
		int[][] biti = {
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, },
				{ 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, },
				{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{ 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, },
				{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, },
				{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, },
				{ 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, },
				{ 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, },
				{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, } };
		boolean[][] bitb = new boolean[16][16];

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				bitb[i][15-j] = biti[i][j] == 1 ? true : false;
			}
		}

		byte[] bits = f.exchange(bitb);
		for (byte b : bits) {
			System.out.print(String.format("0x%x", b) + ",");
		}
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