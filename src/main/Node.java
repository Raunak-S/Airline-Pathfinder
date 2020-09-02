package main;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * <b>Node</b> represents a mutable node.
 * Node consists of a non-empty label and all non-empty weighted connections to its child nodes.
 * The class is generic with two data types, A which is the type of the
 * data in the node, and B which is the type of the edge label between
 * a node and its children.
 * 
 * Examples of Nodes include nodes with no edges, with reflexive edges, and with >= 1 edge(s).
 * No edges: A
 * Reflexive edge: A --> A (4)
 * >= 1 edges: A --> B (4)
 * 			   A --> C (6)
 * 			   B --> C (3)
 */
public class Node<A,B> {
	
	private final A data;
	private HashMap<Node<A,B>, HashSet<B>> children;
	
	// Abstraction Function:
	// Node, n, represents a node labeled data with n.children.size() child nodes,
	// connected by a total of n.getEdgeCount() edges.
	// 
	// Representation Invariant for every Node n:
	// data != null && data != "" &&
	// foreach Node n : children.keySet() 
	// => n != null && 							
	// => foreach key in n.children => n.children.get(key) != null
	//								=> n.children.get(key).size() != 0
	//	
	//	In other words,
	//		* n.data is not null or an empty string
	//		* all keys in n.children are not null
	//		* all values in n.children are not null
	//		* all elements in the values of n.children are not null
	// 		* there is a key for a child node iff there is at least 1 edge between this and child
	
	/**
	 * @param arg A string that uniquely indentifes the node
	 * @effects Constructs a new Node with the label arg and 
	 * 	0 child nodes
	 */
	public Node(A arg) {
		this.data = arg;
		this.children = new HashMap<>();
		// checkRep();
	}
	
	/** 
	 * @returns a templated A object equal to the node's label.
	 */
	public A getData() {
		return this.data;
	}
	
	/** 
	 * @returns an int that is equal to the number of this node's children
	 */
	public int getChildrenCount() {
		return this.children.size();
	}
	
	/** 
	 * @returns an int that is equal to the number edges leaving this node.
	 */
	public int getEdgeCount() {
		int edges = 0;
		for (HashSet<B> s : children.values()) {
			edges += s.size();
		}
		return edges;
	}
	
	/** 
	 * @returns a set of templated A objects that contains all labels of this node's
	 * 	children.
	 */
	public Set<A> getChildren() {
		Set<A> s = new HashSet<>();
		for (Node<A,B> n : this.children.keySet()) {
			s.add(n.getData());
		}
		return s;
	}
	
	/**
	 * @param child The child node to which the returned edge labels are connected.
	 * @requires child != null && there exists an edge between this and child
	 * @returns a set of templated B objects which are all of the edge labels between
	 * 	this and child.
	 */
	public Set<B> getEdgeLabels(Node<A,B> child) {
		return Collections.unmodifiableSet(this.children.get(child));
	}
	
	/**
	 * @param child A node that is the endpoint of the edge being created
	 * @param edgeLabel A string label that is associated with the edge between
	 * 	this node and the child node
	 * @requires child != null && edgeLabel != null
	 * @modifies this
	 * @effects Inserts a new edge between this node and the child node, labels
	 * 	the edge with edgeLabel
	 * @returns true iff the edge between this and the child node is added,
	 * 	otherwise false.
	 */
	public boolean addChild(Node<A,B> child, B edgeLabel) {
		// checkRep();
		HashSet<B> edgeLabels = children.get(child);
		boolean rv;
		if (edgeLabels == null) {
			children.put(child, new HashSet<B>());
			rv = children.get(child).add(edgeLabel);
			// checkRep();
			return rv;
		}
		rv = edgeLabels.add(edgeLabel);
		// checkRep();
		return rv;
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
	 */
	// Throws a RuntimeException if the rep invariant is violated.
	private void checkRep() throws RuntimeException {
		if (data == "") {
			throw new RuntimeException("Node cannot be labeled with an empty string");
		}
		if (children.containsKey(null)) {
			throw new RuntimeException("Node cannot have an edge to a null child");
		}
		for (HashSet<B> edges : children.values()) {
			if (edges == null) {
				throw new RuntimeException("Node cannot have a null set of edges");
			}
			if (edges.size() == 0) {
				throw new RuntimeException("Node cannot have a child node with 0 edges");
			}
			if (edges.contains(null)) {
				throw new RuntimeException("Node cannot have a null edge between itself and its child");
			}
		}
	}
	
	/**
	 * @return an int that all objects equal to this will also return.
	 */
	public int hashCode() {
		return this.data.hashCode();
	}
	
	/**
	 * @param obj The object to be compared for equality
	 * @return true if and only if 'obj' is an instance of a Node and 'this'
	 *         and 'obj' represent the same Node.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Node<?,?>) {
			Node<?, ?> n = (Node<?, ?>) obj;
            return this.data == n.getData();
		} else {
			return false;
		}
	}
	
}
