package Main.Cells;

import Main.PixelDrawer;

public abstract class Cell {
	protected int x, y;
	protected boolean updated;
	protected PixelDrawer pixelDrawer;
	
	public Cell(PixelDrawer pixelDrawer, int x, int y) {
		this.x = x;
		this.y = y;
		
		this.pixelDrawer = pixelDrawer;
	}
	
	/**
	 * Call to update this cell's position and relevant state information.
	 *
	 * @return A 2 element integer array containing the new X and Y positions after updating
	 */
	public abstract int[] update();
	
	/**
	 * Draws the current cell using the internal <code>PixelDrawer</code> object from the constructor.
	 */
	public abstract void draw();
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	public boolean hasUpdated() {
		return this.updated;
	}
}