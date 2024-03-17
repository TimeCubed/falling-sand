package Main.Cells.Util;

import Main.Cells.Cell;
import Main.Util.Constants;

public class NeighbourChecks {
	public static boolean leftEdgeCheck(int x) {
		return x == 0;
	}
	public static boolean rightEdgeCheck(int x) {
		return x == Constants.SCREEN_WIDTH - 1;
	}
	public static boolean horizontalEdgeCheck(int x) {
		return leftEdgeCheck(x) || rightEdgeCheck(x);
	}
	
	public static boolean groundCheck(int y) {
		return y != Constants.SCREEN_HEIGHT - 1;
	}
	
	public static boolean belowCheck(final Cell[][] board, int x, int y) {
		return board[x][y + 1] != null;
	}
	public static boolean leftCheck(final Cell[][] board, int x, int y) {
		return board[x - 1][y] != null;
	}
	public static boolean rightCheck(final Cell[][] board, int x, int y) {
		return board[x + 1][y] != null;
	}
}