package Main;

import Main.Cells.*;
import Main.Util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static Main.Main.LISTENER;

public class Renderer implements Runnable {
	private final PixelDrawer pixelDrawer;
	private boolean shouldRender = true, debugMode = false;
	private boolean isClicking = false;
	private String selectedCellType = "SandCell";
	private int clusterSize = 3, step = 0, cellCount = 0;
	
	public Renderer(final PixelDrawer pixelDrawer) {
		this.pixelDrawer = pixelDrawer;
	}
	
	Cell[][] board = new Cell[Constants.SCREEN_WIDTH][Constants.SCREEN_HEIGHT];
	
	@Override
	public void run() {
		int FPS = 30;
		long lastTime = System.nanoTime();
		double nsPerFrame = 1.0 / FPS * 1e9;
		double delta = 0;
		
		int x = 0, y = 0;
		
		try {
			while (shouldRender) {
				int prevX = x, prevY = y;
				
				long now = System.nanoTime();
				delta += (now - lastTime) / nsPerFrame;
				lastTime = now;
				
				while ((delta >= 1 && !debugMode) || (step >= 1 && debugMode)) {
					pixelDrawer.refresh();
					
					renderFrame();
					
					pixelDrawer.repaint();
					
					if (!debugMode) delta--;
					if (debugMode) step--;
				}
				
				
				Point mousePosition = MouseInfo.getPointerInfo().getLocation();
				
				x = (mousePosition.x - LISTENER.getFrame().getX()) / Constants.PIXEL_SIZE;
				y = (mousePosition.y - LISTENER.getFrame().getY()) / Constants.PIXEL_SIZE;
				
				pixelDrawer.setDrawColor(Color.white);
				pixelDrawer.drawPixel(x, y);
				
				if (prevX != x || prevY != y) {
					pixelDrawer.clearPixel(prevX, prevY);
				}
				
				for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
					for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
						if (board[i][j] != null) {
							board[i][j].draw();
						}
					}
				}
				
				pixelDrawer.repaint();
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
		// update loop
		for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
			for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
				if (board[i][j] == null || board[i][j].hasUpdated()) {
					continue;
				}
				
				int[] newPosition = board[i][j].update(board);
				
				board[i][j].setUpdated(true);
				
				if (newPosition[0] == i && newPosition[1] == j) {
					continue;
				}
				
				board[newPosition[0]][newPosition[1]] = board[i][j];
				board[i][j] = null;
			}
		}
		
		// draw loop
		for (int i = 0; i < Constants.SCREEN_WIDTH; i++) {
			for (int j = 0; j < Constants.SCREEN_HEIGHT; j++) {
				if (board[i][j] != null) {
					board[i][j].draw();
					board[i][j].setUpdated(false);
				}
			}
		}
		
		Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		
		int x = (mousePosition.x - LISTENER.getFrame().getX()) / Constants.PIXEL_SIZE;
		int y = (mousePosition.y - LISTENER.getFrame().getY()) / Constants.PIXEL_SIZE;
		
		if (isClicking) {
			if (!(x < 0 || y < 0) && !(x >= Constants.SCREEN_WIDTH || y >= Constants.SCREEN_HEIGHT)) {
				switch (selectedCellType) {
					case "SandCell" -> Cell.createCluster(pixelDrawer, x, y, board, clusterSize, SandCell.class, 0);
					case "WaterCell" -> Cell.createCluster(pixelDrawer, x, y, board, clusterSize, WaterCell.class, cellCount);
				}
				cellCount++;
			}
		}
		
		pixelDrawer.setDrawColor(Color.white);
		pixelDrawer.drawPixel(x, y);
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
			case ' ' -> step++;
			case 'k' -> {
				if (!debugMode) {
					return;
				}
				
				Point mousePosition = MouseInfo.getPointerInfo().getLocation();
				
				int x = (mousePosition.x - LISTENER.getFrame().getX()) / Constants.PIXEL_SIZE;
				int y = (mousePosition.y - LISTENER.getFrame().getY()) / Constants.PIXEL_SIZE;
				
				System.out.println("printing debug info for cell at " + x + ", " + y);
				
				Cell debugCell = board[x][y];
				
				System.out.println("horizontal travel direction: " + debugCell.horizontalTravelDirection
				+ ", x: " + debugCell.x + ", y: " + debugCell.y + ", id: " + debugCell.id);
				int[] next = debugCell.returnUpdatePosition(board);
				System.out.println("next expected position: " + next[0] + ", " + next[1]);
			}
			case 'l' -> {
				if (!debugMode) {
					return;
				}
				
				Point mousePosition = MouseInfo.getPointerInfo().getLocation();
				
				int x = (mousePosition.x - LISTENER.getFrame().getX()) / Constants.PIXEL_SIZE;
				int y = (mousePosition.y - LISTENER.getFrame().getY()) / Constants.PIXEL_SIZE;
				
				System.out.println("updating cell at " + x + ", " + y);
				
				int[] newpos = board[x][y].update(board);
				
				if (newpos[0] != x || newpos[1] != y) {
					board[newpos[0]][newpos[1]] = board[x][y];
					board[x][y] = null;
				}
			}
			case ';' -> {
				debugMode = !debugMode;
				System.out.println(debugMode ? "entering debug mode" : "exiting debug mode");
			}
		}
	}
	
	public void keyPressed(KeyEvent ignored) {
	
	}
	
	public void keyReleased(KeyEvent ignored) {
	
	}
}