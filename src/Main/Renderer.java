package Main;

import Main.Cells.*;
import Main.Util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static Main.Main.LISTENER;

public class Renderer implements Runnable {
	private final PixelDrawer pixelDrawer;
	private boolean shouldRender = true;
	private boolean isClicking = false;
	private String selectedCellType = "SandCell";
	private int clusterSize = 3;
	
	public Renderer(final PixelDrawer pixelDrawer) {
		this.pixelDrawer = pixelDrawer;
	}
	
	Cell[][] board = new Cell[Constants.SCREEN_WIDTH][Constants.SCREEN_HEIGHT];
	
	@Override
	public void run() {
		int FPS = 5;
		long lastTime = System.nanoTime();
		double nsPerFrame = 1.0 / FPS * 1e9;
		double delta = 0;
		
		try {
			while (shouldRender) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nsPerFrame;
				lastTime = now;
				
				while (delta >= 1) {
					pixelDrawer.refresh();
					
					renderFrame();
					
					pixelDrawer.repaint();
					
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
	
	public void renderFrame() {
		Cell[][] nextGen = new Cell[Constants.SCREEN_WIDTH][Constants.SCREEN_HEIGHT];
		
		// update loop
		for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
			for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
				if (board[i][j] == null) {
					continue;
				}
				
				int[] newPosition = board[i][j].update(board);
				
				nextGen[newPosition[0]][newPosition[1]] = board[i][j];
			}
		}
		board = nextGen;
		
		// draw loop
		for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
			for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
				if (board[i][j] != null) {
					board[i][j].draw();
				}
			}
		}
		
		if (isClicking) {
			Point mousePosition = MouseInfo.getPointerInfo().getLocation();
			
			int x = (mousePosition.x - LISTENER.getFrame().getX()) / Constants.PIXEL_SIZE;
			int y = (mousePosition.y - LISTENER.getFrame().getY()) / Constants.PIXEL_SIZE;
			
			if (!(x < 0 || y < 0) && !(x >= Constants.SCREEN_WIDTH || y >= Constants.SCREEN_HEIGHT)) {
				switch (selectedCellType) {
					case "SandCell" -> Cell.createCluster(pixelDrawer, x, y, board, clusterSize, SandCell.class);
					case "WaterCell" -> Cell.createCluster(pixelDrawer, x, y, board, clusterSize, WaterCell.class);
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent ignored) {
	
	}
	
	public void mousePressed(MouseEvent ignored) {
		isClicking = true;
	}
	
	public void mouseReleased(MouseEvent ignored) {
		isClicking = false;
	}
	
	public void mouseMoved(MouseEvent ignored) {
	
	}
	
	public void mouseDragged(MouseEvent ignored) {
	
	}
	
	public void mouseEntered(MouseEvent ignored) {
	
	}
	
	public void mouseExited(MouseEvent ignored) {
	
	}
	
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
			case '1' -> selectedCellType = "SandCell";
			case '2' -> selectedCellType = "WaterCell";
			
			case 'a' -> clusterSize = 1;
			case 's' -> clusterSize = 3;
			case 'd' -> clusterSize = 5;
		}
		
		System.out.println(selectedCellType + ' ' + clusterSize);
	}
	
	public void keyPressed(KeyEvent ignored) {
	
	}
	
	public void keyReleased(KeyEvent ignored) {
	
	}
}