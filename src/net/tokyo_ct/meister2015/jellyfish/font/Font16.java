package net.tokyo_ct.meister2015.jellyfish.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Font16 {

	public String str;
	public static final int width = 16 * 15;
	public static final int height = 16;
	public  int ys=140;

	public boolean[][] toImage() {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font("MS Gothic", Font.PLAIN, 16);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();

		g2d.dispose();

		boolean[][] data = new boolean[width][height];

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
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
		g2d.drawString(str, 0, fm.getAscent());
		g2d.dispose();
		try {
			ImageIO.write(img, "png", new File("Text.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int c =img.getRGB(x, y)>>>24;
				data[x][y] = c >ys;
			}
		}
		return data;

	}

}
