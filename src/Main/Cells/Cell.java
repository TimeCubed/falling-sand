package Main.Cells;

import Main.PixelDrawer;

public abstract class Cell {
	protected int x, y;
	protected PixelDrawer pixelDrawer;
	
	public Cell(PixelDrawer pixelDrawer, int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void update();
	public abstract void draw();
}