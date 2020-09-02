package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <b>Graph</b> represents a mutable directed labeled multigraph.
 * Graphs are a collection of labeled nodes. Each node has a unique label.
 * The class is generic with two data types, A which is the type of the
 * data in the contained nodes, and B which is the type of the edge label 
 * between a node and its children.
 *
 * Examples of Graphs include an empty graph, graph with one node, or graph with >1 nodes.
 * Empty Graph:
 * One Node: A
 * >1 Node: A, B, C
 */
public class Graph<A,B> {
	
	private HashMap<A, Node<A,B>> nodes;
	
	// Abstraction Function:
	// Graph, g, represents a graph consisting of nodes:
	// foreach String, s, in nodes.getKeys() => nodes.get(s) is a node in the graph

	// Representation Invariant for every Graph g:
	// nodes != null &&
	// foreach key in nodes => key != null &&
	// foreach key in nodes => nodes.get(key) != null && key == nodes.get(key).getData()
	// 
	// In other words,
	// 		* g.nodes is non-null
	// 		* g.nodes contains only non-null keys and values
	//		* each key in g.nodes will lead to a Node with key as the label
	
	/**
	 * @effects Constructs a new Graph with 0 nodes.
	 */
	public Graph() {
		nodes = new HashMap<>();
		// checkRep();
	}
	
	/**
	 * @param node A string that may or may not identify a node in the graph.
	 * @returns true if the string 'node' matches the label of a node in the graph,
	 *  false otherwise.
	 */
	public boolean contains(String node) {
		return nodes.containsKey(node);
	}
	
	/** 
	 * @returns an int that is equal to the number of nodes in the graph.
	 */
	public int getNodeCount() {
		return nodes.size();
	}
	
	/**
	 * @returns an int that is equal to the number of edges in the graph.
	 */
	public int getEdgeCount() {
		int edges = 0;
		for (A pNode : nodes.keySet()) {
			edges += nodes.get(pNode).getEdgeCount();
		}
		return edges;
	}
	
	/**
	 * @returns an iterator of templated A objects that iterates 
	 * 			through all nodes of the graph in alphabetical order.
	 */
	public Iterator<A> getSortedNodes() {
		TreeSet<A> sortedNodes = new TreeSet<>();
		for (A s : nodes.keySet()) {
			sortedNodes.add(s);
		}
		return sortedNodes.iterator();
	}
	
	/**
	 * @param parent The label of the parent node
	 * @returns a set of templated A objects which are the children of the node with the 
	 * 	label parent, will return an empty set if the parent is not in 
	 * 	the graph.
	 */
	public Set<A> getChildNodes(A parent) {
		Node<A,B> pNode = nodes.get(parent);
		if (pNode == null) {
			return Collections.<A>emptySet();
		}
		return pNode.getChildren();
	}
	
	/**
	 * @param parent The label of the parent node
	 * @param child The label of the child node
	 * @returns a set of templated B objects which are the labels of the edges between
	 * 	the parent and child nodes, will return an empty set if either
	 * 	parent or child node does not exist, or if the child is
	 * 	connected to the parent.
	 */
	public Set<B> getEdgeLabels(A parent, A child) {
		Node<A,B> pNode = nodes.get(parent);
		Node<A,B> cNode = nodes.get(child);
		if (pNode == null || cNode == null || !pNode.getChildren().contains(child)) {
			return Collections.<B>emptySet();
		}
		return pNode.getEdgeLabels(cNode);
	}
	
	/**
	 * @param data The label of the new node which will be added.
	 * @requires data != null && data != ""
	 * @modifies this
	 * @effects Construct a new node object with label 'data', and insert 
	 *        it into this
	 * @returns true iff the new node is successfully inserted into this, 
	 * 	otherwise false
	 */
	public boolean addNode(A data) {
		// checkRep();
		if (!nodes.containsKey(data)) {
			nodes.put(data, new Node<A,B>(data));
			// checkRep();
			return true;
		}
		return false;
	}
	
	/**
	 * @param parent The label of the parent node.
	 * @param child The label of the child node.
	 * @param edgeLabel The label of the newly created edge between the parent
	 * 	and child node.
	 * @requires parent != null && child != null && edgeLabel != null
	 * @modifies this
	 * @effects Construct a new node object with label 'data', and insert
	 *       it into this
	 * @returns true iff the new node is successfully inserted into this,
	 * 	otherwise false
	 */
	public boolean addEdge(A parent, A child, B edgeLabel) {
		// checkRep();
		Node<A,B> pNode = nodes.get(parent);
		Node<A,B> cNode = nodes.get(child);
		if (pNode == null || cNode == null) {
			return false;
		}
		// checkRep();
		return pNode.addChild(cNode, edgeLabel);
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
	 */
	// Throws a RuntimeException if the rep invariant is violated.
	private void checkRep() throws RuntimeException {
		for (A key : nodes.keySet()) {
			if (key == null) {
				throw new RuntimeException("Graph key cannot be NULL");
			}
			Node<A,B> value = nodes.get(key);
			if (value == null) {
				throw new RuntimeException("Graph value cannot be NULL");
			}
			if (!key.equals(value.getData())) {
				throw new RuntimeException("Each key in graph must be equivalent to the Node label to which it leads");
			}
		}
	}
	
}
