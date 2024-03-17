package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;

import java.awt.*;
import java.util.Random;

public abstract class Cell {
	protected int x, prevX, y, prevY;
	protected long tickLifeTime = 0;
	protected Color color;
	protected PixelDrawer pixelDrawer;
	protected Random random = new Random();
	
	protected int horizontalTravelDirection, totalHorizontalTravel = 0;
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
	 * Returns the cell's expected X and Y position after an update without updating its position
	 *
	 * @param board The game board with all the cells in it for neighbor data
	 * @return A 2 element integer array containing the updated X and Y position
	 */
	public abstract int[] returnUpdatePosition(final Cell[][] board);
	
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
	public static void createCluster(PixelDrawer pixelDrawer, int x, int y, final Cell[][] board, int clusterSize, Class<? extends Cell> cellType) {
		for (int i = x - (int) (Math.floor((double) clusterSize / 2)); i <= x + (int) (Math.floor((double) clusterSize / 2)); i++) {
			for (int j = y - (int) (Math.floor((double) clusterSize / 2)); j <= y + (int) (Math.floor((double) clusterSize / 2)); j++) {
				if (i >= 0 && i < Constants.SCREEN_WIDTH && j >= 0 && j < Constants.SCREEN_HEIGHT) {
					board[i][j] = createCellOfType(pixelDrawer, i, j, cellType);
				}
			}
		}
	}
	
	private static Cell createCellOfType(PixelDrawer pixelDrawer, int x, int y, Class<? extends Cell> cellType) {
		return switch (cellType.getSimpleName()) {
			case "SandCell" -> new SandCell(pixelDrawer, x, y);
			case "WaterCell" -> new WaterCell(pixelDrawer, x, y);
			default -> throw new IllegalArgumentException("Invalid cell type given");
		};
	}
	
	public void setColor(Color color) {
		this.color = color;
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
	
	public int[] validateCoordinates(int x, int y) {
		if (x >= Constants.SCREEN_WIDTH) {
			x = Constants.SCREEN_WIDTH - 1;
		}
		
		if (x < 0) {
			x = 0;
		}
		
		if (y >= Constants.SCREEN_HEIGHT) {
			y = Constants.SCREEN_HEIGHT - 1;
		}
		
		if (y < 0) {
			y = 0;
		}
		
		return new int[] {x, y};
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
		return this.y != Constants.SCREEN_HEIGHT - 1;
	}
	
	public boolean belowCheck(final Cell[][] board) {
		return board[this.x][this.y + 1] != null;
	}
	public boolean leftCheck(final Cell[][] board) {
		return board[this.x - 1][this.y] != null;
	}
	public boolean rightCheck(final Cell[][] board) {
		return board[this.x + 1][this.y] != null;
	}
}