package Main.Cells;

import Main.PixelDrawer;

import java.awt.*;
import java.util.Arrays;

public class WaterCell extends Cell {
	public WaterCell(PixelDrawer pixelDrawer, int x, int y) {
		super(pixelDrawer, x, y);
		
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
		
		if (!groundCheck() && !belowCheck(board)) {
			this.y++;
		}
		
		validatePosition();
		
		if (!horizontalEdgeCheck() && !groundCheck() && belowCheck(board) && (board[this.x + horizontalTravelDirection][this.y] == null || !Arrays.equals(board[this.x + horizontalTravelDirection][this.y].returnUpdatePosition(board), new int[] {this.x + horizontalTravelDirection, this.y}))) {
			this.x += horizontalTravelDirection;
		}
		
		if (horizontalEdgeCheck()) {
			horizontalTravelDirection = -horizontalTravelDirection;
		} else if (horizontalTravelDirection == -1 && leftCheck(board)) {
			horizontalTravelDirection = -horizontalTravelDirection;
		} else if (horizontalTravelDirection == 1 && rightCheck(board)) {
			horizontalTravelDirection = -horizontalTravelDirection;
		}
		
		validatePosition();
		
		return super.update(board);
	}
	
	@Override
	public int[] returnUpdatePosition(Cell[][] board) {
		int updateX = this.x;
		int updateY = this.y;
		
		int[] validatedCoordinates;
		
		if (!groundCheck() && !belowCheck(board)) {
			this.y++;
		}
		
		validatedCoordinates = validateCoordinates(updateX, updateY);
		updateX = validatedCoordinates[0];
		updateY = validatedCoordinates[1];
		
		if (!horizontalEdgeCheck() && !groundCheck() && belowCheck(board) && board[this.x + horizontalTravelDirection][this.y] == null) {
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
