package Main;

import Main.Cells.Cell;
import Main.Cells.SandCell;
import Main.Util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static Main.Main.LISTENER;

public class Renderer implements Runnable {
	private final PixelDrawer pixelDrawer;
	private boolean shouldRender = true;
	private int frameCount = 0;
	private boolean isClicking = false;
	
	public Renderer(final PixelDrawer pixelDrawer) {
		this.pixelDrawer = pixelDrawer;
	}
	
	Cell[][] board = new Cell[Constants.SCREEN_WIDTH][Constants.SCREEN_HEIGHT];
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerFrame = 1.0 / 30 * 1e9;
		double delta = 0;
		
		try {
			while (shouldRender) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nsPerFrame;
				lastTime = now;
				
				while (delta >= 1) {
					pixelDrawer.refresh();
					
					// update loop
					for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
						for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
							if (board[i][j] != null) {
								if (board[i][j].hasUpdated()) {
									continue;
								}
								
								int[] newPosition = board[i][j].update(board);
								
								if (newPosition[0] == i && newPosition[1] == j) {
									board[newPosition[0]][newPosition[1]].setUpdated(true);
									
									continue;
								}
								
								board[newPosition[0]][newPosition[1]] = board[i][j];
								board[i][j] = null;
								
								board[newPosition[0]][newPosition[1]].setUpdated(true);
							}
						}
					}
					
					// draw loop
					for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
						for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
							if (board[i][j] != null) {
								board[i][j].draw();
							}
						}
					}
					
					// refresh loop
					for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
						for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
							if (board[i][j] != null) {
								board[i][j].setUpdated(false);
							}
						}
					}
					
					if (isClicking) {
						Point mousePosition = MouseInfo.getPointerInfo().getLocation();
						
						int x = (mousePosition.x - LISTENER.getFrame().getX()) / Constants.PIXEL_SIZE;
						int y = (mousePosition.y - LISTENER.getFrame().getY()) / Constants.PIXEL_SIZE;
						
						if (!(x < 0 || y < 0) && !(x >= Constants.SCREEN_WIDTH || y >= Constants.SCREEN_HEIGHT)) {
							System.out.println("adding new cell: " + x + " " + y);
							board[x][y] = new SandCell(pixelDrawer, x, y);
						}
					}
					
					delta--;
				}
			}
		} catch (Exception e) {
			System.err.println("----------------------------------------------------------------------------------------");
			System.err.println("! RENDER THREAD FAIL !");
			System.err.println("The render thread crashed due to the following exception:");
			e.printStackTrace(System.err);
			System.err.println("----------------------------------------------------------------------------------------");
			shouldRender = false;
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
		int x = e.getX() / Constants.PIXEL_SIZE;
		int y = e.getY() / Constants.PIXEL_SIZE;
		
		System.out.println("adding new cell: " + x + " " + y);
		board[x][y] = new SandCell(pixelDrawer, x, y);
		
		isClicking = true;
	}
	
	public void mouseReleased(MouseEvent e) {
		isClicking = false;
	}
	
	public void mouseMoved(MouseEvent e) {
	
	}
	
	public void mouseDragged(MouseEvent e) {
	
	}
	
	public void mouseEntered(MouseEvent e) {
	
	}
	
	public void mouseExited(MouseEvent e) {
	
	}
}
