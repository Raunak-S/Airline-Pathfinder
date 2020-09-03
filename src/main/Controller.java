package main;

public class Controller {

	public static void main(String[] args) {
		AirlineModel am = new AirlineModel();
		am.createNewGraph("data/airports-extended.csv", "data/routes.csv");
		
		for (String[] edge : am.Dijkstra("1", "2")) {
			System.out.println(edge[0] + " to " + edge[1] + " with weight " + edge[2]);
		}
		
	}
	
}
