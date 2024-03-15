package Main;

import Main.Cells.Cell;
import Main.Util.Constants;

import java.awt.event.MouseEvent;

public class Renderer implements Runnable {
	private final PixelDrawer pixelDrawer;
	private boolean shouldRender = true;
	private int frameCount = 0;
	
	public Renderer(final PixelDrawer pixelDrawer) {
		this.pixelDrawer = pixelDrawer;
	}
	
	Cell board[][] = new Cell[Constants.SCREEN_WIDTH][Constants.SCREEN_WIDTH];
	
	@Override
	public void run() {
		while (shouldRender) {
			try {
			
			} catch (Exception e) {
				System.err.println("----------------------------------------------------------------------------------------");
				System.err.println("! RENDER THREAD FAIL !");
				System.err.println("The render thread crashed due to the following exception:");
				e.printStackTrace(System.err);
				System.err.println("----------------------------------------------------------------------------------------");
				shouldRender = false;
			}
		}
	}
	
	public long getFrameCount() {
		return frameCount;
	}
	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}
	
	public void mouseClicked(MouseEvent e) {
	
	}
	
	public void mousePressed(MouseEvent e) {
	
	}
	
	public void mouseReleased(MouseEvent e) {
	
	}
	
	public void mouseEntered(MouseEvent e) {
	
	}
	
	public void mouseExited(MouseEvent e) {
	
	}
}
