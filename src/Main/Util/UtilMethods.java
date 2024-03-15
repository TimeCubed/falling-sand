package Main.Util;

public class UtilMethods {
	/**
	 * Swaps two points by swapping their x and y values
	 *
	 * @param a First point
	 * @param b Second point
	 */
	public static void swap(Point a, Point b) {
		Point temp = new Point(b.getX(), b.getY());
		b.setX(a.getX());
		b.setY(a.getY());
		
		a.setX(temp.getX());
		a.setY(temp.getY());
	}
}
