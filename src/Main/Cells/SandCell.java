package Main.Cells;

import Main.PixelDrawer;
import Main.Util.Constants;

public class SandCell extends Cell {
	public SandCell(PixelDrawer pixelDrawer, int x, int y) {
		super(pixelDrawer, x, y);
	}
	
	@Override
	public void update() {
		this.y += 1;
		
		if (this.y > Constants.SCREEN_HEIGHT) {
			this.y -= 1;
		}
	}
	
	@Override
	public void draw() {
		pixelDrawer.drawPixel(x, y);
	}
}