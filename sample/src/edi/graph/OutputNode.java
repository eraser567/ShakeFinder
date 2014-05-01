package edi.graph;

import java.io.Serializable;

public class OutputNode implements Serializable, Comparable<OutputNode> {
    private static final long serialVersionUID = 1L;
    public String id;
    public String label;
    public double latitude;
    public double longitude;
    public int grid_id = -1;
    public int distance = 0;

    public OutputNode(String id, String label, double latitude, double longitude, int grid_id, int distance) {
        this.id = id;
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
        this.grid_id = grid_id;
        this.distance = distance;
    }

    public String toString () {
        return "[id:"+id+", label="+label+", latitude="+latitude+", longitude="+longitude+", grid="+grid_id+"]";
    }

    public int compareTo(OutputNode o) {
        int d=this.distance-o.distance;
        if (d==0) {
            return this.id.compareTo(o.id);
        }
        else {
            return d;
        }
    }
}
