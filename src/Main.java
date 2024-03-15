import javax.swing.*;

public class Main extends JPanel {
	public static void main(String[] args) {
		PixelDrawer pixelDrawer = new PixelDrawer();
		JFrame jFrame = new JFrame("Wireframe Renderer");
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.add(pixelDrawer);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		
		Thread renderThread = new Thread(new RenderThread(pixelDrawer));
		renderThread.start();
	}
}