package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;
import java.awt.*;

public class SandCell extends Cell {
	private final int maxTravel;
	protected int horizontalTravelDirection, totalHorizontalTravel = 0;
	
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
	
	@Override
	public void draw() {
		pixelDrawer.setDrawColor(this.color);
		pixelDrawer.drawPixel(x, y);
	}
}