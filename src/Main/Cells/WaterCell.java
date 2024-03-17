package Main.Cells;

import Main.Cells.Util.NeighbourChecks;
import Main.PixelDrawer;

import java.awt.*;

public class WaterCell extends Cell {
	public WaterCell(PixelDrawer pixelDrawer, int x, int y, int id) {
		super(pixelDrawer, x, y, id);
		
		int brightnessOffset = this.random.nextInt(41) - 20;
		
		switch (random.nextInt(2)) {
			case 0 -> horizontalTravelDirection = HorizontalTravelDirections.LEFT;
			case 1 -> horizontalTravelDirection = HorizontalTravelDirections.RIGHT;
		}
		
		super.setColor(new Color(20 + brightnessOffset, 119 + brightnessOffset, 190 + brightnessOffset));
	}
	
	@Override
	public int[] update(final Cell[][] board) {
		this.prevX = this.x;
		this.prevY = this.y;
		
		if (NeighbourChecks.groundCheck(y) && !NeighbourChecks.belowCheck(board, x, y)) {
			this.y++;
		}
		
		validatePosition();
		
		if (NeighbourChecks.horizontalEdgeCheck(x)) {
			horizontalTravelDirection = -horizontalTravelDirection;
		} else if (horizontalTravelDirection == -1 && NeighbourChecks.leftCheck(board, x, y)) {
			horizontalTravelDirection = -horizontalTravelDirection;
		} else if (horizontalTravelDirection == 1 && NeighbourChecks.rightCheck(board, x, y)) {
			horizontalTravelDirection = -horizontalTravelDirection;
		}
		
		if (horizontalTravelDirection == -1 && NeighbourChecks.groundCheck(y) && NeighbourChecks.belowCheck(board, x, y) && !NeighbourChecks.leftEdgeCheck(x) && board[x + horizontalTravelDirection][y] == null) {
			this.x += horizontalTravelDirection;
		} else if (horizontalTravelDirection == 1 && NeighbourChecks.groundCheck(y) && NeighbourChecks.belowCheck(board, x, y) && !NeighbourChecks.rightEdgeCheck(x) && board[x + horizontalTravelDirection][y] == null) {
			this.x += horizontalTravelDirection;
		}
		
		validatePosition();
		
		return new int[] {this.x, this.y};
	}
	
	@Override
	public int[] returnUpdatePosition(Cell[][] board) {
		int updateX = this.x;
		int updateY = this.y;
		
		int[] validatedCoordinates;
		
		if (NeighbourChecks.groundCheck(y) && !NeighbourChecks.belowCheck(board, x, y)) {
			updateY++;
		}
		
		validatedCoordinates = validateCoordinates(updateX, updateY);
		updateX = validatedCoordinates[0];
		updateY = validatedCoordinates[1];
		
		if (!NeighbourChecks.horizontalEdgeCheck(x) && NeighbourChecks.groundCheck(y) && NeighbourChecks.belowCheck(board, x, y) && board[this.x + horizontalTravelDirection][this.y] == null) {
			updateX += horizontalTravelDirection;
		}
		
		validatedCoordinates = validateCoordinates(updateX, updateY);
		updateX = validatedCoordinates[0];
		updateY = validatedCoordinates[1];
		
		return new int[] {updateX, updateY};
	}
	
	@Override
	public void draw() {
		pixelDrawer.setDrawColor(this.color);
		pixelDrawer.drawPixel(x, y);
	}
}
