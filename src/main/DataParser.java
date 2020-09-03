package main;

import java.util.*;
import java.io.*;

public class DataParser {

	/** 
	 * There is no representation invariant or abstraction function for this class. This
	 * is because DataParser is never constructed and only contains static methods.
	*/
	
	/** @param: nodeFile The path to the "CSV" file that contains the [name,id,x-coordinate,y-coordinate]
	 			info of each building
		@param: edgeFile The path to the "CSV" file that contains the <id,id> edge pairs                                                                                                
        @param: nameToInfo The Map that stores parsed <name, building-info> pairs;
        	    usually an empty Map
        @param: idToInfo The Map that stores parsed <id, building-info> pairs;
        	    usually an empty Map
        @param: edges The Set that stores parsed <id, id> pairs;
        		usually an empty Set
        @requires nodeFile and edgeFile are in expected CSV format
        @effects: adds parsed <name, building-info> pairs to Map nameToInfo;
        		  adds parsed <id, building-info> pairs to Map idToInfo;
        		  adds parsed <id, id> pairs to Set edges
        @throws: IOException if file cannot be read or file not a CSV file                                                                                     
	 */
    public static void readData(String nodeFile,
    							String edgeFile,
    							Map<String, ArrayList<String>> nameToInfo, 
    							Map<String, ArrayList<String>> idToInfo,
    							Set<ArrayList<String>> edges) 
    									throws IOException {

    	BufferedReader reader = new BufferedReader(new FileReader(nodeFile));
        String line = null;
        String info = null;
        while ((line = reader.readLine()) != null) {
        	 int index;
        	 ArrayList<String> infoArray = new ArrayList<>();
        	 for (int i = 0; i < 8; i++) {
    			 index = line.indexOf(",");
        		 info = line.substring(0, index);
        		 info = info.replaceAll("^\"|\"$", "");
        		 if (i == 0 || i == 2 || i == 6 || i == 7) {
        			 infoArray.add(info);
        		 }
        		 line = line.substring(index+1, line.length());        			 
        	 }
             nameToInfo.put(infoArray.get(1), infoArray);
             idToInfo.put(infoArray.get(0), infoArray);
        }
        reader.close();
        
        reader = new BufferedReader(new FileReader(edgeFile));
        while ((line = reader.readLine()) != null) {
			int index;
			ArrayList<String> infoArray = new ArrayList<>();
			for (int i = 0; i < 6; i++) {
				index = line.indexOf(",");
				info = line.substring(0, index);
		   		if (i == 3 || i == 5) {
		   			infoArray.add(info);
		   		}
		   		line = line.substring(index+1, line.length());        			 
		   	}
			edges.add(infoArray);
    	}
        reader.close();
    }
    
    public static void prettyPrint(HashMap<String,ArrayList<String>> genMap) {
    	for (String key : genMap.keySet()) {
    		System.out.println(key);
    		for (String info : genMap.get(key)) {
    			System.out.println("\t" + info);
    		}
    	}
    }
    
    public static void main(String[] args) throws IOException {
    	HashMap<String,ArrayList<String>> idToInfo = new HashMap<>();
    	HashMap<String,ArrayList<String>> nameToInfo = new HashMap<>();
    	HashSet<ArrayList<String>> edges = new HashSet<>();
    	DataParser.readData("data/airports.csv", "data/routes.csv", nameToInfo, idToInfo, edges);
    	DataParser.prettyPrint(idToInfo);
    	for (ArrayList<String> edge : edges) {
    		System.out.println(edge.get(0) + " " + edge.get(1));
    	}
    }
    
}

