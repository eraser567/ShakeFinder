package edi.graph;

import java.io.Serializable;

public class Edge implements Serializable {
    public String fromNodeId = null;
    public String toNodeId = null;
    public String label = null;

    public Edge () {

    }

    public Edge (String eFrom, String eTo, String eLabel) {
        fromNodeId = eFrom;
        toNodeId = eTo;
        label = eLabel;
    }
}
