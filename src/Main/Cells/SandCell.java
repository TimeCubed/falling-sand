package Main.Cells;

import Main.Cells.Util.NeighbourChecks;
import Main.PixelDrawer;
import java.awt.*;

public class SandCell extends Cell {
	private final int maxTravel;
	
	public SandCell(PixelDrawer pixelDrawer, int x, int y) {
		super(pixelDrawer, x, y, 0);
		
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
		
		if (NeighbourChecks.groundCheck(y) && board[this.x][this.y + 1] == null) {
			this.y += 1;
		} else if (!NeighbourChecks.leftEdgeCheck(x) && NeighbourChecks.groundCheck(y) && board[this.x - 1][this.y + 1] == null && (horizontalTravelDirection == -1 || random.nextBoolean())) {
			this.x -= 1;
			this.y += 1;
		} else if (!NeighbourChecks.rightEdgeCheck(x) && NeighbourChecks.groundCheck(y) && board[this.x + 1][this.y + 1] == null && (horizontalTravelDirection == 1 || random.nextBoolean())) {
			this.x += 1;
			this.y += 1;
		}
		
		validatePosition();
		
		if (random.nextInt(2) == 0 && totalHorizontalTravel < maxTravel && !NeighbourChecks.horizontalEdgeCheck(x) && board[this.x + horizontalTravelDirection][this.y] == null) {
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

		if (NeighbourChecks.groundCheck(updateY) && board[updateX][updateY + 1] == null) {
			updateY += 1;
		} else if (!NeighbourChecks.leftEdgeCheck(updateX) && NeighbourChecks.groundCheck(updateY) && board[updateX - 1][updateY + 1] == null) {
			updateX -= 1;
			updateY += 1;
		} else if (!NeighbourChecks.rightEdgeCheck(updateX) && NeighbourChecks.groundCheck(updateY) && board[updateX +1][updateY + 1] == null) {
			updateX += 1;
			updateY += 1;
		}

		validatedCoordinates = validateCoordinates(updateX, updateY);

		updateX = validatedCoordinates[0];
		updateY = validatedCoordinates[1];

		if (random.nextInt(2) == 0 && totalHorizontalTravel < maxTravel && !NeighbourChecks.horizontalEdgeCheck(updateX) && board[updateX + horizontalTravelDirection][updateY] == null) {
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