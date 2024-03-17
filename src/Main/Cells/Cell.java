package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;

import java.awt.*;
import java.util.Random;

public abstract class Cell {
	protected int x, prevX, y, prevY, horizontalTravelDirection, totalHorizontalTravel = 0, id;
	protected boolean updated;
	protected long tickLifeTime = 0;
	protected Color color;
	protected PixelDrawer pixelDrawer;
	protected Random random = new Random();
	
	protected static class HorizontalTravelDirections {
		public static int LEFT = -1;
		public static int NO_TRAVEL = 0;
		public static int RIGHT = 1;
	}
	
	public Cell(PixelDrawer pixelDrawer, int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.color = Color.BLACK;
		this.id = id;
		
		this.pixelDrawer = pixelDrawer;
		this.updated = false;
	}
	
	public int[] update(final Cell[][] board) {
		tickLifeTime++;
		
		return new int[] {this.x, this.y};
	}

	public abstract int[] returnUpdatePosition(final Cell[][] board);
	
	public abstract void draw();
	
	public static void createCluster(PixelDrawer pixelDrawer, int x, int y, final Cell[][] board, int clusterSize, Class<? extends Cell> cellType, int id) {
		for (int i = x - (int) (Math.floor((double) clusterSize / 2)); i <= x + (int) (Math.floor((double) clusterSize / 2)); i++) {
			for (int j = y - (int) (Math.floor((double) clusterSize / 2)); j <= y + (int) (Math.floor((double) clusterSize / 2)); j++) {
				Random random1 = new Random();
				
				if (i >= 0 && i < Constants.SCREEN_WIDTH && j >= 0 && j < Constants.SCREEN_HEIGHT && random1.nextBoolean()) {
					board[i][j] = createCellOfType(pixelDrawer, i, j, cellType, id);
					id++;
				}
			}
		}
	}
	
	private static Cell createCellOfType(PixelDrawer pixelDrawer, int x, int y, Class<? extends Cell> cellType, int id) {
		return switch (cellType.getSimpleName()) {
			case "SandCell" -> new SandCell(pixelDrawer, x, y);
			case "WaterCell" -> new WaterCell(pixelDrawer, x, y, id);
			default -> throw new IllegalArgumentException("Invalid cell type given");
		};
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getId() {
		return id;
	}
	public boolean hasUpdated() {
		return updated;
	}
	public int getHorizontalTravelDirection() {
		return horizontalTravelDirection;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
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
}