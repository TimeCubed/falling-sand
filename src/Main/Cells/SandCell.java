package Main.Cells;

import Main.PixelDrawer;
import java.awt.*;
import java.util.Arrays;

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
		
		if (groundCheck() && (board[this.x][this.y + 1] == null || !Arrays.equals(board[this.x][this.y + 1].returnUpdatePosition(board), new int[]{this.x, this.y + 1}))) {
			this.y += 1;
		} else if (!leftEdgeCheck() && groundCheck() && (board[this.x - 1][this.y + 1] == null || !Arrays.equals(board[this.x - 1][this.y + 1].returnUpdatePosition(board), new int[]{this.x - 1, this.y + 1}))) {
			this.x -= 1;
			this.y += 1;
		} else if (!rightEdgeCheck() && groundCheck() && (board[this.x + 1][this.y + 1] == null || !Arrays.equals(board[this.x + 1][this.y + 1].returnUpdatePosition(board), new int[]{this.x + 1, this.y + 1}))) {
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
	public int[] returnUpdatePosition(Cell[][] board) {
		int updateX = this.x;
		int updateY = this.y;

		int[] validatedCoordinates;

		if (groundCheck() && board[updateX][updateY + 1] == null) {
			updateY += 1;
		} else if (!leftEdgeCheck() && groundCheck() && board[updateX - 1][updateY + 1] == null) {
			updateX -= 1;
			updateY += 1;
		} else if (!rightEdgeCheck() && groundCheck() && board[updateX +1][updateY + 1] == null) {
			updateX += 1;
			updateY += 1;
		}

		validatedCoordinates = validateCoordinates(updateX, updateY);

		updateX = validatedCoordinates[0];
		updateY = validatedCoordinates[1];

		if (random.nextInt(2) == 0 && totalHorizontalTravel < maxTravel && !horizontalEdgeCheck() && board[updateX + horizontalTravelDirection][updateY] == null) {
			this.x += horizontalTravelDirection;
			totalHorizontalTravel++;
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