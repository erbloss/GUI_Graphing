package application;
/** 
 * @author Elizabeth Bloss
 * July 2024
 * UMGC, CMSC 315
 * Project 4
 * 
 * This immutable class defines a vertex consisting
 * of x and y coordinates, and a name.
 * 
 */

final class Vertex {
	private final double x;
	private final double y;
	private final String name;
	
	// constructor
	public Vertex(double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	// getter methods
	public double getXValue() {
		return x;
	}
	
	public double getYValue() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
}
