package Main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main implements MouseListener, MouseMotionListener {
	private static Renderer renderer;
	
	public static void main(String[] args) {
		PixelDrawer pixelDrawer = new PixelDrawer();
		JFrame jFrame = new JFrame("Wireframe Main.Renderer");
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.add(pixelDrawer);
		jFrame.addMouseListener(new Main());
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		
		renderer = new Renderer(pixelDrawer);
		Thread renderThread = new Thread(renderer);
		renderThread.start();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		renderer.mouseClicked(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		renderer.mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		renderer.mouseReleased(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		renderer.mouseEntered(e);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		renderer.mouseExited(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		renderer.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		renderer.mouseMoved(e);
	}
}