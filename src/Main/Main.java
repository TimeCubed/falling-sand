package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main implements MouseListener, MouseMotionListener, KeyListener {
	private static Renderer renderer;
	public static Main LISTENER = new Main();
	private static JFrame jFrame;
	
	public static void main(String[] args) {
		PixelDrawer pixelDrawer = new PixelDrawer();
		jFrame = new JFrame("Wireframe Main.Renderer");
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.add(pixelDrawer);
		jFrame.addMouseListener(LISTENER);
		jFrame.addKeyListener(LISTENER);
		jFrame.getContentPane().setBackground(Color.black);
		pixelDrawer.setBackground(Color.black);
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
	
	public JFrame getFrame() {
		return jFrame;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		renderer.keyTyped(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		renderer.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		renderer.keyReleased(e);
	}
}