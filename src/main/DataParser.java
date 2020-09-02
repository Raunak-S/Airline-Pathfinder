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
    							Map<String, String[]> nameToInfo, 
    							Map<String, String[]> idToInfo,
    							Set<String[]> edges) 
    									throws IOException {

    	BufferedReader reader = new BufferedReader(new FileReader(nodeFile));
        String line = null;
        Integer[] ints = {0, 2, 6, 7};
        HashSet<Integer> indices = new HashSet<>();
        for (int i = 0; i < ints.length; i++) {
        	indices.add(ints[i]);
        }
        while ((line = reader.readLine()) != null) {
        	 String[] building = new String[4];
        	 int index;
        	 String info;
        	 for (int i = 0; i < 8; i++) {
        		 if (indices.contains(i)) {
        			 index = line.indexOf(",");
            		 info = line.substring(0, index);
            		 info = info.replaceAll("^\"|\"$", "");
            		 building[i] = info;
            		 line = line.substring(index+1, line.length());
        		 }
        	 }
        	 building[3] = line;
             nameToInfo.put(building[0], building);
             idToInfo.put(building[1], building);
        }

        
        reader.close();
        reader = new BufferedReader(new FileReader(edgeFile));
        while ((line = reader.readLine()) != null) {
        	String[] edge = new String[2];
        	int index = line.indexOf(",");
        	edge[0] = line.substring(0, index);
        	edge[1] = line.substring(index+1, line.length());
        	edges.add(edge);
        }
        reader.close();
    }
}

