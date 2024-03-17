package Main.Cells;

import Main.PixelDrawer;

import java.awt.*;

public class WaterCell extends Cell {
	public WaterCell(PixelDrawer pixelDrawer, int x, int y) {
		super(pixelDrawer, x, y);
		
		int brightnessOffset = this.random.nextInt(41) - 20;
		
		super.setColor(new Color(brightnessOffset, 119 + brightnessOffset, 190 + brightnessOffset));
		
	}
	
	@Override
	public int[] update(final Cell[][] board) {
		
		
		return super.update(board);
	}
	
	@Override
	public void draw() {
	
	}
}
