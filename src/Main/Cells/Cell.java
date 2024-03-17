package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;

import java.awt.*;
import java.util.Random;

public abstract class Cell {
	protected int x, prevX, y, prevY;
	protected long tickLifeTime = 0;
	protected boolean updated;
	protected Color color;
	protected PixelDrawer pixelDrawer;
	protected Random random = new Random();
	
	protected static class HorizontalTravelDirections {
		public static int LEFT = -1;
		public static int NO_TRAVEL = 0;
		public static int RIGHT = 1;
	}
	
	/**
	 * Creates a new typeless cell with a default black color
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
	 * Call to update this cell's position and relevant state information. Must be called by
	 * subclasses at the end of their update to update base cell info.
	 *
	 * @param board The game board with all the cells in it for neighbor data
	 * @return A 2 element integer array containing the new X and Y positions after updating
	 */
	public int[] update(final Cell[][] board) {
		tickLifeTime++;
		
		return new int[] {this.x, this.y};
	}
	
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
	public static void createCluster(PixelDrawer pixelDrawer, int x, int y, final Cell[][] board, Class<? extends Cell> cellType) {
		board[x][y] = createCellOfType(pixelDrawer, x, y, cellType);
	}
	
	private static Cell createCellOfType(PixelDrawer pixelDrawer, int x, int y, Class<? extends Cell> cellType) {
		return switch (cellType.getSimpleName()) {
			case "SandCell" -> new SandCell(pixelDrawer, x, y);
			case "WaterCell" -> new WaterCell(pixelDrawer, x, y);
			default -> throw new IllegalArgumentException("Invalid cell type given");
		};
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
	
	// Util methods
	public void validatePosition() {
		if (this.x >= Constants.SCREEN_WIDTH) {
			this.x = Constants.SCREEN_WIDTH - 1;
		}
		
		if (this.x < 0) {
			this.x = 0;
		}
		
		if (this.y >= Constants.SCREEN_HEIGHT) {
			this.y = Constants.SCREEN_HEIGHT - 1;
		}
		
		if (this.y < 0) {
			this.y = 0;
		}
	}
	
	public boolean leftEdgeCheck() {
		return this.x == 0;
	}
	
	public boolean rightEdgeCheck() {
		return this.x == Constants.SCREEN_WIDTH - 1;
	}
	
	public boolean horizontalEdgeCheck() {
		return leftEdgeCheck() || rightEdgeCheck();
	}
	
	public boolean groundCheck() {
		return this.y == Constants.SCREEN_HEIGHT - 1;
	}
}