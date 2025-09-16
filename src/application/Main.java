package application;
	
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** 
 * @author Elizabeth Bloss
 * July 2024
 * UMGC, CMSC 315
 * Project 4
 * 
 * This class contains the main method to launch
 * the GUI application.
 * 
 */
public class Main extends Application {
	
	// instantiate text fields
	private TextField vertex1TF = new TextField();
	private TextField vertex2TF = new TextField();
	private TextField resultsTF = new TextField();
	
	// instantiate graph
	Graph graph = new Graph();
	GraphView graphView = new GraphView(graph);
	
	
	//##############################################
	//		STAGE
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane pane = new BorderPane();			
			pane.setPadding((new Insets(10, 20, 20, 20)));
			
		// create HBox for top of pane
			HBox hboxTop = new HBox(5.0);
			hboxTop.setAlignment(Pos.CENTER);
			
				// text field labels, button
			vertex1TF.setPrefWidth(30.0);
			vertex2TF.setPrefWidth(30.0);
			Label v1Label = new Label("Vertex 1:");
			Label v2Label = new Label("Vertex 2:");
			Button addEdgeBtn = new Button("Add Edge");
				// handle action event
			addEdgeBtn.setOnAction(e -> addEdge());
				// populate
			hboxTop.getChildren().addAll(addEdgeBtn, v1Label, vertex1TF, v2Label, vertex2TF);
			
		// create HBox for bottom of pane
			HBox hboxBottom = new HBox(5.0);
			hboxBottom.setAlignment(Pos.CENTER);
			
				// buttons and handle respective action events
			Button connectedBtn = new Button("Is Connected?");
			connectedBtn.setOnAction(e -> checkConnected());
			Button cyclesBtn = new Button("Has Cycles?");
			cyclesBtn.setOnAction(e -> checkCycles());
			Button dfsBtn = new Button("Depth First Search");
			dfsBtn.setOnAction(e -> checkDFS());
			Button bfsBtn = new Button("Breadth First Search");
			bfsBtn.setOnAction(e -> checkBFS());
				// populate
			hboxBottom.getChildren().addAll(connectedBtn, cyclesBtn, dfsBtn, bfsBtn);
			
		// create VBox for hboxBottom and results field
			VBox vbox = new VBox(10.0);
				// results textfield settings
			resultsTF.setEditable(false);
			resultsTF.setPrefWidth(300);
				// populate vbox
			vbox.getChildren().addAll(hboxBottom, resultsTF);
			vbox.setAlignment(Pos.CENTER);
			
		// add to pane
			pane.setTop(hboxTop);
			pane.setCenter(graphView);
			pane.setBottom(vbox);
			
		// set scene
			Scene scene = new Scene(pane, 500,600);
			primaryStage.setTitle("Project 4");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//###############################################
	//		EVENT HANDLING METHODS
	
	/** METHOD to handle add edge */
	public void addEdge() {
		String vertex1 = vertex1TF.getText(); 
		String vertex2 = vertex2TF.getText();
		resultsTF.clear();
		// if both points exist on graph
		if(graph.getVertices().containsKey(vertex1) 
				&& graph.getVertices().containsKey(vertex2)) {
			// draw edge between the vertices
			graphView.drawEdge(graph.getVertices().get(vertex1),
					graph.getVertices().get(vertex2));
		}
		else {
			// if a vertex does not exist on graph display error message
			resultsTF.setText("At least one vertex does not exist."
					+ " Please try again.");
		}
	}
	
	/** METHOD to handle is connected? button
	 * displays resulting message */
	public void checkConnected() {
		resultsTF.clear();
		String message;
		// proceed if at least one point on graph
		if(!graph.getVertices().isEmpty()) {
			if(graph.isConnected()) {
				message = "This is a connected graph";
			}
			else {
				message = "The graph is not connected";
			}
		}
		else { // empty graph
			message = "Please add a vertex to begin.";
		}
		resultsTF.setText(message);
		
	}
	
	/** METHOD to handle if graph has cycles
	 * displays resulting message */
	public void checkCycles() {
		resultsTF.clear();
		if(graph.hasCycle()) {
			resultsTF.setText("Graph contains cycles");
		}
		else {
			resultsTF.setText("Graph does not contain cycles");
		}
	}
	
	/** METHOD to handle DFS 
	 * displays list of vertices resulting from 
	 * depth first search, starting with A */
	public void checkDFS() {
		resultsTF.clear();
		String message;
		// proceed if at least one point on graph
		if(!graph.getVertices().isEmpty()) {
			List<Vertex> searchOrder = graph.dfs();
			message = graph.dfsOutput(searchOrder);
		}
		else {  // empty graph
			message = "Please add a vertex to begin.";
		}
		resultsTF.setText(message);
	}
	
	/** METHOD to handle BFS 
	 * displays list of vertices resulting from 
	 * breadth first search, starting with A*/
	public void checkBFS() {
		resultsTF.clear();
		String message;
		// proceed if at least one point on graph
		if(!graph.getVertices().isEmpty()) {
			message = graph.bfs();
		}
		else { //empty graph
			message = "Please add a vertex to begin.";
		}
		resultsTF.setText(message);
	}
	

	//##############################################
	//		EXECUTE MAIN

	public static void main(String[] args) {
		launch(args);
	}
}
