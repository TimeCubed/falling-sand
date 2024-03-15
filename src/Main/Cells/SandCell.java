package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;

public class SandCell extends Cell {
	public SandCell(PixelDrawer pixelDrawer, int x, int y) {
		super(pixelDrawer, x, y);
	}
	
	@Override
	public int[] update(final Cell[][] board) {
		this.y += 1;
		
		if (this.y >= Constants.SCREEN_HEIGHT || board[this.x][this.y] != null) {
			this.y -= 1;
		}
		
		return new int[] {this.x, this.y};
	}
	
	@Override
	public void draw() {
		pixelDrawer.drawPixel(x, y);
	}
}