package Main.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Shape2D {
	private Line[] lines;
	
	public Shape2D(Line[] lines) {
		this.lines = lines;
	}
	
	public Line[] getLines() {
		return lines;
	}
	public List<Point> getPoints() {
		Set<Point> vertices = new HashSet<>();
		
		for (Line line : lines) {
			vertices.add(line.getStartPoint());
			vertices.add(line.getEndPoint());
		}
		
		return vertices.stream().toList();
	}
	
	public void setLines(Line[] lines) {
		this.lines = lines;
	}
}