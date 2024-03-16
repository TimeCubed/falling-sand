package Main.Cells;

import Main.PixelDrawer;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public abstract class Cell {
	protected int x, y;
	protected boolean updated;
	protected Color color;
	protected PixelDrawer pixelDrawer;
	protected Random random = new Random();
	
	/**
	 * Creates a new default Cell.
	 *
	 * @param pixelDrawer The <code>PixelDrawer</code> object that is used for drawing
	 * @param x The initial X position of this cell
	 * @param y The initial Y position of this cell
	 * @param color The draw color to be used by the <code>pixelDrawer</code> to draw this cell with.
	 */
	public Cell(PixelDrawer pixelDrawer, int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		
		this.pixelDrawer = pixelDrawer;
		updated = false;
	}
	
	/**
	 * Overload constructor without color parameter that sets the color of this cell to <code>Color.black</code>
	 *
	 * @param pixelDrawer The <code>PixelDrawer</code> object that is used for drawing
	 * @param x The initial X position of this cell
	 * @param y The initial Y position of this cell
	 */
	public Cell(PixelDrawer pixelDrawer, int x, int y) {
		this.x = x;
		this.y = y;
		this.color = Color.BLACK;
		
		this.pixelDrawer = pixelDrawer;
		updated = false;
	}
	
	/**
	 * Call to update this cell's position and relevant state information.
	 *
	 * @param board The game board with all the cells in it for neighbor data
	 * @return A 2 element integer array containing the new X and Y positions after updating
	 */
	public abstract int[] update(final Cell[][] board);
	
	/**
	 * Draws the current cell using the internal <code>PixelDrawer</code> object from the constructor.
	 */
	public abstract void draw();
	
	/**
	 * Creates a cluster of this cell at the given location
	 *
	 * @param x The X position to create the cluster of cells at
	 * @param y The Y position to create the cluster of cells at
	 * @param board The game board used for neighbor data
	 */
	public static void createCluster(PixelDrawer pixelDrawer, int x, int y, final Cell[][] board, Class<? extends Cell> cellType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		board[x][y] = createCellOfType(pixelDrawer, x, y, cellType);
	}
	
	private static Cell createCellOfType(PixelDrawer pixelDrawer, int x, int y, Class<? extends Cell> cellType) {
		switch(cellType.getSimpleName()) {
			case "SandCell":
				return new SandCell(pixelDrawer, x, y);
			default:
				throw new IllegalArgumentException("Invalid cell type given");
		}
	}
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean hasUpdated() {
		return this.updated;
	}
}