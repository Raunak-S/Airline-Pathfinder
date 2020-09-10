package main;

import java.util.Iterator;
import java.util.LinkedList;

public class View {
	
	public static void listBuildings(AirlineModel model) {
		Iterator<String> buildingItr = model.getSortedNodes();
		
		while (buildingItr.hasNext()) {
			String name = buildingItr.next();
			System.out.println(name + " - " + model.getIdByName(name));
		}
	}
	
	
	public static void shortestPath(AirlineModel model, String id1, String id2) {
		boolean invalidChar = false;
		if (!model.containsNameOrId(id1)) {
			System.out.println("Unknown building: [" + id1 + "]");
			invalidChar = true;
		}
		if ((!id1.equals(id2)) && !model.containsNameOrId(id2)) {
			System.out.println("Unknown building: [" + id2 + "]");
			invalidChar = true;
		}
		if (invalidChar) return;
		
		LinkedList<String[]> path = model.Dijkstra(id1, id2);
		
		String name1, name2;
		name1 = model.getNameById(id1);
		if (name1 == null) name1 = id1;
		
		name2 = model.getNameById(id2);
		if (name2 == null) name2 = id2;
		
		if (path == null) {
			System.out.println(String.format("There is no path from %s to %s.", name1, name2));
			return;
		}
		System.out.println("Path from " + name1 + " to " + name2 + ":");
		double totalDistance = 0;
		for (String[] edge : path) {
			String start = model.getNameById(edge[0]);
			String destination = model.getNameById(edge[1]);
			System.out.println("\tFlight from " + start + " to " + destination + " with distance " + edge[2]);
			totalDistance += Double.parseDouble(edge[2]);
		}
		System.out.println(String.format("Total distance: %.3f pixel units.", totalDistance));
		
	}
	
	public static void listCommands() {
		System.out.println("r");
	}
	
	public static void unknownCommand() {
		System.out.println("Unknown option");
	}
}
