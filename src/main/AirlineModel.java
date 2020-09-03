package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

public class AirlineModel {

	private Graph<String,Double> g;
	private HashMap<String,ArrayList<String>> idToInfo;
	private HashMap<String,ArrayList<String>> nameToInfo;
	
	public AirlineModel() {
		this.g = new Graph<>();
		this.idToInfo = new HashMap<>();
		this.nameToInfo = new HashMap<>();
	}
	
	public void createNewGraph(String nodeFile, String edgeFile) {
		Graph<String,Double> newGraph = new Graph<>();
		HashSet<ArrayList<String>> edges = new HashSet<>();
		try {
			DataParser.readData(nodeFile, edgeFile, 
								this.nameToInfo, this.idToInfo, edges);			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		for (String key : this.idToInfo.keySet()) {
			newGraph.addNode(key);
		}
		
		for (ArrayList<String> edge : edges) {
			String startNode = edge.get(0);
        	String destNode = edge.get(1);
        	System.out.println(startNode + "  " + destNode);
        	double deltaX = Double.parseDouble(idToInfo.get(startNode).get(2))-Double.parseDouble(idToInfo.get(destNode).get(2));
        	double deltaY = Double.parseDouble(idToInfo.get(startNode).get(3))-Double.parseDouble(idToInfo.get(destNode).get(3));
        	double distance = Math.hypot(deltaX, deltaY);
        	newGraph.addEdge(startNode, destNode, distance);
		}
		this.g = newGraph;
	}
	
	public LinkedList<String[]> Dijkstra(String node1, String node2) {
		String startNode = node1;
		String destNode = node2;
		// create comparator for the priority queue based on lowest cost
		Comparator<LinkedList<String[]>> lowestCostComparator = new Comparator<LinkedList<String[]>>() {
			public int compare(LinkedList<String[]> l1, LinkedList<String[]> l2) {
				String sum1 = l1.peekFirst()[3];
				String sum2 = l2.peekFirst()[3];
				return Double.compare(Double.valueOf(sum1), Double.valueOf(sum2));
			}
		};
		PriorityQueue<LinkedList<String[]>> activePaths = new PriorityQueue<>(lowestCostComparator);
		HashSet<String> finishedNodes = new HashSet<>();
		// create initial path
		LinkedList<String[]> startPath = new LinkedList<>();
		String[] initialEdge = {startNode, startNode, "0.0", "0.0"};
		startPath.add(initialEdge);
		activePaths.add(startPath);
		
		while (activePaths.size() != 0) {
			LinkedList<String[]> minPath = activePaths.poll();
			String minDest = minPath.peekLast()[1];
			if (minDest.equals(destNode)) {
				// remove the first edge because it is always 'reflexive'
				minPath.removeFirst();
				return minPath;
			}
			
			if (finishedNodes.contains(minDest)) continue;
			
			Set<String> children = g.getChildNodes(minDest);
			for (String child : children) {
				Set<Double> edgeLabels = g.getEdgeLabels(minDest, child);
				for (Double edgeLabel : edgeLabels) {
					// check if the node already has a lowest cost path known
					if (!finishedNodes.contains(child)) {
						String[] newEdge = {minDest, child, edgeLabel.toString()};
						LinkedList<String[]> newPath = new LinkedList<>();
						for (int i = 0; i < minPath.size(); i++) {
							newPath.add(minPath.get(i).clone());
						}
						newPath.add(newEdge);
						String[] firstEdge = newPath.peekFirst();
						Double newPathWeight = Double.valueOf(firstEdge[3]) + edgeLabel;
						firstEdge[3] = newPathWeight.toString();
						activePaths.add(newPath);
					}
				}
			}
			finishedNodes.add(minDest);
		}
		return null;
	}
	
}
