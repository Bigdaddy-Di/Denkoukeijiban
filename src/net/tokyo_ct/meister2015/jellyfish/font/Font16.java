package net.tokyo_ct.meister2015.jellyfish.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Font16 {

	public String str;
	public int width;
	public int height = 16;
	public int ys = 0;
	public boolean[][] data;
	private static final int WIDTH = 16 * 8;
	private static final int HEIGHT = 16;
	public String fontFile = "JF-Dot-Shinonome14B.ttf";

	public Font16(boolean[][] data, int w, int h) {
		this.data = data;
		this.width = w;
		this.height = h;
	}

	public Font16() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void toImage() {
		try {
			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = img.createGraphics();
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile));
			font = font.deriveFont(14F);
			g2d.setFont(font);
			FontMetrics fm = g2d.getFontMetrics();

			g2d.dispose();

			width = fm.stringWidth(str);

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g2d = img.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g2d.setFont(font);
			fm = g2d.getFontMetrics();
			g2d.setColor(Color.BLACK);
			g2d.drawString(str, 0, fm.getAscent() + 1);
			g2d.dispose();

			ImageIO.write(img, "png", new File("Text.png"));

			data = new boolean[img.getWidth()][img.getHeight()];

			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					int c = img.getRGB(x, y) >>> 24;
					data[x][y] = (c > ys);
				}
			}
		} catch (IOException | FontFormatException ex) {
			ex.printStackTrace();
		}

	}

	public LinkedList<Integer[]> toInts() {
		LinkedList<Integer[]> d = new LinkedList<Integer[]>();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Integer[] z = { x, y };
				if (this.data[x][y]) {
					d.add(z);
				}
			}
		}

		return d;
	}

	public static boolean[][] toBooleans(int w, int h, LinkedList<Integer[]> l) {
		boolean[][] d = new boolean[w][h];
		System.out.println(w + " " + h);
		for (int i = 0; i < l.size(); i++) {
			if (0 <= l.get(i)[0] && l.get(i)[0] < d.length && 0 <= l.get(i)[1] && l.get(i)[1] < d[0].length) {
				d[l.get(i)[0]][l.get(i)[1]] = true;
			}
		}
		return d;
	}

	public boolean[][] displace(int gapX, int gapY, int w, int h) {
		LinkedList<Integer[]> d = this.toInts();

		for (int i = 0; i < d.size(); i++) {
			d.get(i)[0] += gapX;
			d.get(i)[1] += gapY;
		}

		return toBooleans(w, h, d);

	}

	public byte[] exchange(boolean[][] bits) {
		ArrayList<Byte> b = new ArrayList<Byte>();
		StringBuilder s = new StringBuilder();
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 15; x++) {
				if (bits[x][y] == true) {
					s.append('1');
				} else {
					s.append('0');
				}
			}
			System.out.println(s.toString());
			b.add(Byte.parseByte(s.toString().substring(0, 7), 2));
			b.add(Byte.parseByte(s.toString().substring(8, 15), 2));
			s.delete(0, s.length());
		}

		byte[] result = new byte[32];
		for (int i = 0; i < 32; i++) {
			result[i] = b.get(i);
		}
		return result;
	}
}
