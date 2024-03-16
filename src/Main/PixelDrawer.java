package Main;

import Main.Util.*;
import Main.Util.Point;
import Main.Util.Shape2D;

import javax.swing.*;
import java.awt.*;

public class PixelDrawer extends JComponent {
	private int[][] pixels;
	private Color drawColor;
	
	public PixelDrawer() {
		pixels = new int[Constants.SCREEN_WIDTH][Constants.SCREEN_HEIGHT];
		setPreferredSize(new Dimension(Constants.SCREEN_WIDTH * Constants.PIXEL_SIZE, Constants.SCREEN_HEIGHT * Constants.PIXEL_SIZE));
		drawColor = Color.BLACK;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int x = 0; x < Constants.SCREEN_WIDTH; x++) {
			for (int y = 0; y < Constants.SCREEN_HEIGHT; y++) {
				if (pixels[x][y] != 0) {
					g.setColor(new Color(pixels[x][y]));
					g.fillRect(x * Constants.PIXEL_SIZE, y * Constants.PIXEL_SIZE, Constants.PIXEL_SIZE, Constants.PIXEL_SIZE);
				}
			}
		}
	}
	
	public void drawPixel(int x, int y) {
		if (x >= 0 && x < Constants.SCREEN_WIDTH && y >= 0 && y < Constants.SCREEN_HEIGHT) {
			pixels[x][y] = drawColor.getRGB();
			repaint();
		}
	}
	private void drawLine(int x1, int y1, int x2, int y2) {
		float slope = (float) (y2 - y1) / (x2 - x1);
		int prev_y = (int) (y1 - slope);
		
		for (int x = 0; x <= x2 - x1; x++) {
			int y = (int) (slope + prev_y);
			
			// pixel is at x + 0.5, y + 0.5
			double dA = Math.sqrt(Math.pow((y + 0.5) - (x * slope + 0.5), 2));
			double dB = Math.sqrt(Math.pow((y + 1.5) - (x * slope + 0.5), 2));
			
			if (dA < dB) {
				drawPixel(x + x1, y);
			} else {
				drawPixel(x + x1, y + 1);
			}
			
			prev_y = y;
		}
	}
	public void drawLine(Point a, Point b) {
		if (b.getX() < a.getX()) {
			UtilMethods.swap(a, b);
		}
		
		drawLine(a.getX(), a.getY(), b.getX(), b.getY());
	}
	public void drawLine(Line l) {
		drawLine(l.getStartPoint(), l.getEndPoint());
	}
	public void drawShape2D(Shape2D shape2D) {
		for (Line line : shape2D.getLines()) {
			drawLine(line);
		}
	}
	
	public void refresh() {
		pixels = new int[Constants.SCREEN_WIDTH][Constants.SCREEN_HEIGHT];
		this.repaint();
	}
	public void setDrawColor(Color color) {
		drawColor = color;
	}
}