package application;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/** 
 * @author Elizabeth Bloss
 * July 2024
 * UMGC, CMSC 315
 * Project 4
 * 
 * This class is an extension of the javafx Pane
 * class. It visually displays the graph.
 * Includes an event handler to create new vertices
 * with a mouse click and a method to draw edges.
 * 
 */

public class GraphView extends Pane {
	
	private Graph graph;
	
	// constructor that handles mouse click events
	public GraphView(Graph graph) {
		this.graph = graph;
		
		// mouses click events to add vertex
		this.setOnMouseClicked(e -> {
			double x = e.getX();
			double y = e.getY();
			
			// create label in alphabetic ascending order
			char nextLetter = (char) ('A' + 
					graph.getVertices().size());
			String nameNext = Character.toString(nextLetter);
			
			// create vertex, add to graph, draw on pane
			Vertex newVertex = new Vertex(x, y, nameNext);
			graph.addVertex(newVertex);
			Circle circle = new Circle(x, y, 5);
			Text vertexName = new Text(x, y - 8, nameNext);
			getChildren().addAll(circle, vertexName);
		});
	}
	
	/** METHOD to draw an edge connecting two vertices
	 * @param Vertex v1: first vertex to connect
	 * @param Vertex v2: second vertex to connect */
	public void drawEdge(Vertex v1, Vertex v2) {
		// add edge to graph
		graph.addEdge(v1, v2);
		// draw edge
		Line line = new Line(v1.getXValue(), v1.getYValue(), 
				v2.getXValue(), v2.getYValue());
		getChildren().add(line);
	}
}
