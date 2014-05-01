package edi.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphSerializable implements Serializable {

    private static final long serialVersionUID = 1L;
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();
    public double latitude = -1;
    public double longitude = -1;

    public GraphSerializable() {}

    public GraphSerializable (ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public GraphSerializable (ArrayList<Node> nodes, ArrayList<Edge> edges, double latitude, double longitude) {
        this.nodes = nodes;
        this.edges = edges;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void display () {
        System.out.println("-- nodes:");
        for (Node n : nodes) {
            System.out.println(n.id + ", " + n.label + ", " + n.isPersonalizedNode + ", " + n.isOutputNode);
        }
        System.out.println("-- edges:");
        for (Edge e : edges) {
            System.out.println(e.fromNodeId + ", " + e.toNodeId + ", " + e.label);
        }
    }

    public String getPersonalizedNode () {
        for (Node n : nodes) {
            if (n.isPersonalizedNode) {
                return n.id;
            }
        }
        return null;
    }

    public String getOutputNode () {
        for (Node n : nodes) {
            if (n.isOutputNode) {
                return n.id;
            }
        }
        return null;
    }
    
    /*public cg_graph getCgGraph () {
    	cg_graph Q = new cg_graph();
		for (Node n : nodes) {
			Q.addVertex(n.id);
		}
		for (Edge e : edges) {
			Q.addEdge(e.fromNodeId, e.toNodeId, new edge(e.fromNodeId,e.toNodeId));
		}
		return Q;
    }*/

    public HashMap<String, String> getQAttr() {
        HashMap<String, String> attr = new HashMap<String, String>();
        for (Node n : nodes) {
            attr.put(n.id, n.label);
        }
        return attr;
    }

    public static GraphSerializable createGraph () {
        GraphSerializable g = new GraphSerializable();

        g.nodes.add(new Node("0", "PM", true, false));
        g.nodes.add(new Node("1", "MK", false, false));
        g.nodes.add(new Node("2", "PG", false, true));
//        g.nodes.add(new Node("3", "ui design", false, true));

        g.edges.add(new Edge ("0", "1", "friend"));
        g.edges.add(new Edge ("0", "2", "friend"));
//        g.edges.add(new Edge ("1", "2", "friend"));
//        g.edges.add(new Edge("2", "3", "friend"));

        g.latitude = 39.4+Math.random()*2.2;
        g.longitude = 115.7+Math.random()*1.7;

        return g;
    }

}