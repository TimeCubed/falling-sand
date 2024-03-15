import Util.*;
import Util.Constants;

public class RenderThread implements Runnable {
	private final PixelDrawer pixelDrawer;
	private boolean shouldRender = true;
	private int frameCount = 0;
	
	public RenderThread(final PixelDrawer pixelDrawer) {
		this.pixelDrawer = pixelDrawer;
	}
	
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
}
