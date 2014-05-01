package edi.graph;

import java.io.Serializable;

public class Node implements Serializable {
    public String id = null;
    public String label = null;
    public boolean isPersonalizedNode = false;
    public boolean isOutputNode = false;

    public Node () {

    }

    public Node (String nid, String nlabel, boolean nPersonalized, boolean nOutput) {
        id = nid;
        label = nlabel;
        isPersonalizedNode = nPersonalized;
        isOutputNode = nOutput;
    }
}
