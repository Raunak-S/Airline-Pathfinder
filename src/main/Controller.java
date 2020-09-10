package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {

	public static void main(String[] args) {
		AirlineModel am = new AirlineModel();
		am.createNewGraph("data/airports-extended.csv", "data/routes.csv");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = null;
        while (true) {
        	try {
        		command = reader.readLine();         	
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	if (command.equals("b")) {
        		View.listBuildings(am);
        	} else if (command.equals("r")) {
        		String b1 = null;
        		String b2 = null;
        		try {
        			System.out.print("First building id/name, followed by Enter: ");
            		b1 = reader.readLine();
            		System.out.print("Second building id/name, followed by Enter: ");
            		b2 = reader.readLine();
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
        		View.shortestPath(am, b1, b2);
        	} else if (command.equals("q")) {
        		return;
        	} else if (command.equals("m")) {
        		View.listCommands();
        	} else {
        		View.unknownCommand();
        	}
        }
		
	}
	
}
