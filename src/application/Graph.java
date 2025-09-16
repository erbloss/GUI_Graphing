package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/** 
 * @author Elizabeth Bloss
 * July 2024
 * UMGC, CMSC 315
 * Project 4
 * 
 * This class defines the graph.  Its contains methods to 
 * add an edge, add a vertex, check for cycles, check if
 * its connected, perform a DFS, and perform a BFS.
 * 
 */

public class Graph {
	
	private final Map<String, Vertex> vertices;
	private final List<Edge> edges;    // edges as objects
	private final Map<Vertex, List<Vertex>> neighbors; // Adjacency list of edges
	
	//################################################
	//		CONSTRUCTORS
	
	/** Construct a graph from vertices and edges stored in List */
	public Graph() {
	   vertices = new HashMap<>();
	   edges = new ArrayList<>();	
	   neighbors = new HashMap<>();
	}
	
	
	//#################################################
	//		METHODS
	
	/** METHOD to add a vertex to graph
	 * and add the vertex as head of adjacency list
	 *  @param Vertex: the vertex to be added  */
	public void addVertex(Vertex v) {
		vertices.put(v.getName(), v);
		
		// adjacency list
		neighbors.put(v,  new ArrayList<>());
	}
	
	/** METHOD to add an edge to graph
	 * and add the vertex pair to the adjacency list
	 * @param Vertex v1: vertex 1 of edge
	 * @param Vertex v2: vertex 2 of edge */
	public void addEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		edges.add(edge);
		
		// add edge to adjacency linked list
		neighbors.get(v1).add(v2);
		neighbors.get(v2).add(v1); // since graph is undirected
	}
	
	/** METHOD to check whether the graph has cycles 
	 * @return boolean: true if the graph contains a cycle */
	public boolean hasCycle() {
		// to mark vertices if visited
		Map <Vertex, Boolean> visited = createVisitMap();
		// call recursive helper to detect cycle
		for(Vertex v: vertices.values()) {
			if(visited.get(v) == false) {
				if(hasCycleHelper(v, visited, null)) {
					return true;
				}
			}
		}
		return false;
	}
	/** METHOD helper method to recursively check if graph is cyclic 
	 * @param Vertex current: the current vertex being analyzed
	 * @param Map<Vertex, Boolean>: map for value if vertex is visited 
	 * @param Vertex parent = the parent vertex to check 
	 * @return boolean: true if cycle is found */
	private boolean hasCycleHelper(Vertex current, Map<Vertex, Boolean> visited, Vertex parent) {
		// mark current node as visited
		visited.put(current, true);
		// get adjacent nodes of current
		List<Vertex> adjVertices = neighbors.get(current); 
		
		// recur for all adjacent nodes
		for(Vertex adj: adjVertices) {
			if(visited.get(adj) == false) {		// adjacent not visited
				if(hasCycleHelper(adj, visited, current)) {
					return true;
				}
			}
			else if(adj.equals(parent) == false) { // not parent of current
				return true; // there exists a cycle	
			}
		}
		return false;
	}
	
	/** METHOD to check if the graph is connected
	 * @return boolean: true if the graph is connected */
	public boolean isConnected() {
		// if # vertices searched == # vertices in graph -> true
		List<Vertex> listSearched = dfs();
		if(listSearched.size() == vertices.size()) {
			return true;
		}
		return false;
	}

	/** METHOD depth first search of the graph from vertex A
	 * @return String: message to output on pane.  DFS order of traversal */
	public List<Vertex> dfs() {
		Map <Vertex, Boolean> visited = createVisitMap(); // to mark vertices
		List<Vertex> searchOrder = new ArrayList<>(); // order visited
		
		// vertex A to begin DFS
		Vertex vertexA = vertices.get("A");
		searchOrder = dfsHelper(vertexA, visited, searchOrder);
		
		return searchOrder;
	}
	/** METHOD helper method to perform recursive DFS 
	 * @param Vertex: the vertex to be analyzed
	 * @param Map<Vertex, Boolean>: KEY = vertices on graph. VALUE = visited T/F? 
	 * @param List<Vertex>: the list of vertices in order they were visited
	 * @return List<Vertex>: list of vertices in order they were visited */
 	private List<Vertex> dfsHelper(Vertex v, 
 			Map<Vertex,Boolean> visited, List<Vertex> searchOrder){		
	    
		// Recursively search: base case
	    if(visited.get(v) == true) {
	    	return searchOrder;
	    }
	    else {
	    	// mark vertex as visited
	    	visited.put(v, true);
	    	// add to searchOrder list
	    	searchOrder.add(v);
	    }
	    //iterate over neighbors of V, look for false in visited map.
	    List<Vertex> adjVertices = neighbors.get(v); // possible neighbors
	    for(Vertex vTemp : adjVertices) {
	    	if(visited.get(vTemp) == false) {		// if an option has not been visited
	    		dfsHelper(vTemp, visited, searchOrder);
	    	}
	    }
	    return searchOrder;
	}	
	
	
	/** METHOD breadth first search of the graph from vertex A
	 * @return String: message to be displayed with BFS order of vertices */
	public String bfs(){
		Queue<Vertex> queue = new LinkedList<>(); 
		Map<Vertex, Boolean> visited = createVisitMap(); // to mark vertex
		String message = "BFS order from vertex A --> ";
		// mark vertex A as visited and enqueue
		Vertex vertexA = vertices.get("A");
		visited.put(vertexA, true);
		queue.add(vertexA);
		
		// iterate over queue
		while(!queue.isEmpty()) {
			//dequeue a vertex and print
			Vertex current = queue.poll();
			message = message + current.getName() + " ";
			
			// get adjacent vertices and 
			// if not visited, mark visited and enqueue
			List<Vertex> adjVertices = neighbors.get(current); 
			
			// recur for all adjacent nodes
			for(Vertex adj: adjVertices) {
				if(visited.get(adj) == false) {	
					visited.put(adj,true);
					queue.add(adj);
				}
			}
		}
		return message;
	}
	
	
	//################################################
	//		ADDITIONAL METHODS

	/** METHOD getter for vertices in graph 
	 * @return Map<String, Vertex> : map of vertices with their names */
	public Map<String, Vertex> getVertices() {
		return vertices;
	}
	
	/** METHOD to get the neighbors of a vertex
	 * @param Vertex: the root vertex
	 * @return List<Edge>: all edges neighboring vertex */
	public List<Vertex> getNeighbors (Vertex v) {
		return neighbors.get(v);
	}
	/** METHOD to create string for displaying results of DFS
	 * @param List<Vertex> : list of vertices in their searched order 
	 * @return String: results in form of string for DFS */
	public String dfsOutput(List<Vertex> searchOrder) {
		// create string to be displayed in main class
		String message = "DFS Order from vertex A --> ";
			for(Vertex x: searchOrder) {
				message = message + x.getName() + "  ";
			}
		return message;
	}
	
	/** METHOD to create a map for vertices with boolean
	 * value to represent if a specific vertex has been visited
	 * @return: map<Vertex, Boolean> : all set to false */
	private Map<Vertex, Boolean> createVisitMap(){
		Map <Vertex, Boolean> visited = new HashMap<>();
		for(Map.Entry<String, Vertex> vertex: vertices.entrySet()) {
			visited.put(vertex.getValue(), false); 
		}
		return visited;
	}
	
	//##########################################################
	//#################################################
	//		INNER CLASS
	/** 	INNER CLASS 
	 * 
	 * inner class to define an edge consisting 
	 * of two vertices as ends */
	public static class Edge {
		Vertex v1;
		Vertex v2;

		public Edge(Vertex v1, Vertex v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
		// test if two edges are identical
		public boolean equals(Edge e) {
			return v1 == e.v1 && v2 == e.v2;
		}
	}

	
}
