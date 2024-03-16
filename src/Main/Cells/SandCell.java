package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;
import java.awt.*;

public class SandCell extends Cell {
	private final int maxTravel;
	
	public SandCell(PixelDrawer pixelDrawer, int x, int y) {
		super(pixelDrawer, x, y);
		
		int brightnessOffset = this.random.nextInt(41) - 20;
		
		super.setColor(new Color(194 + brightnessOffset, 178 + brightnessOffset, 128 + brightnessOffset));
		
		switch (random.nextInt(3) - 1) {
			case -1 -> horizontalTravelDirection = HorizontalTravelDirections.LEFT;
			case 0 -> horizontalTravelDirection = HorizontalTravelDirections.NO_TRAVEL;
			case 1 -> horizontalTravelDirection = HorizontalTravelDirections.RIGHT;
		}
		
		maxTravel = random.nextInt(3, 6);
	}
	
	@Override
	public int[] update(final Cell[][] board) {
		this.prevX = this.x;
		this.prevY = this.y;
		
		validatePosition();
		
		if (!groundCheck() && board[this.x][this.y + 1] == null) {
			this.y += 1;
		} else if (!leftEdgeCheck() && !groundCheck() && board[this.x - 1][this.y + 1] == null) {
			this.x -= 1;
			this.y += 1;
		} else if (!rightEdgeCheck() && !groundCheck() && board[this.x +1][this.y + 1] == null) {
			this.x += 1;
			this.y += 1;
		}
		
		validatePosition();
		
		if (random.nextInt(2) == 0 && totalHorizontalTravel < maxTravel && !horizontalEdgeCheck() && board[this.x + horizontalTravelDirection][this.y] == null) {
			this.x += horizontalTravelDirection;
			totalHorizontalTravel++;
		}
		
		validatePosition();
		
		return super.update(board);
	}
	
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
	
	@Override
	public void draw() {
		pixelDrawer.setDrawColor(this.color);
		pixelDrawer.drawPixel(x, y);
	}
}