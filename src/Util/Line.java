package Util;

public class Line implements Cloneable {
	private final Point startPoint, endPoint;
	
	public Line(Point start, Point end) {
		this.startPoint = start;
		this.endPoint = end;
		
		swapCheck();
	}
	
	private void swapCheck() {
		if (this.startPoint.getX() > this.endPoint.getX()) {
			UtilMethods.swap(startPoint, endPoint);
		}
	}
	
	public Point getStartPoint() {
		return startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	
	@Override
	public Line clone() throws CloneNotSupportedException {
		return (Line) super.clone();
	}
}
