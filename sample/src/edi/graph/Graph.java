package edi.graph;


import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph {
    /*Context mContext;
    Graph (Context c) {
        mContext = c;
    }*/

    public ArrayList<Node> nodes = new ArrayList<Node>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();

    public String nodesData = null;
    public String edgesData = null;

    public static Graph createGraph () {
        Graph g = new Graph();

        g.nodes.add(new Node("0", "PM", false, false));
        g.nodes.add(new Node("1", "MK", false, false));
        g.nodes.add(new Node("2", "PG", false, false));
//        g.nodes.add(new Node("3", "ui design", false, true));

        g.edges.add(new Edge ("0", "1", "friend"));
        g.edges.add(new Edge ("0", "2", "friend"));
//        g.edges.add(new Edge ("1", "2", "friend"));
//        g.edges.add(new Edge("2", "3", "friend"));

        return g;
    }

    @JavascriptInterface
    public void sendDataBack (String nd, String ed) {
        nodesData = nd;
        edgesData = ed;
        Log.i("Js2Android", "nodesData=" + nodesData);
        Log.i("Js2Android", "edgesData=" + edgesData);

        nodes.clear();
        edges.clear();

        try {

            // nodes
            JSONObject nodesListJsonObj = new JSONObject(nodesData);
            Iterator<String> it = nodesListJsonObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = nodesListJsonObj.getString(key);
                JSONObject nodeJsonObj = new JSONObject(value);

                String id = nodeJsonObj.getString("id");
                String label = nodeJsonObj.getString("label");
                boolean isPersonalizedNode = false;
                boolean isOutputNode = false;
                if(nodeJsonObj.has("color")) {
                    String color = nodeJsonObj.getString("color");
                    //Log.i("Js2Android", id+"."+label+".color=" + color);
                    if (color.equals("#FFCC33")) isPersonalizedNode = true;
                    else if (color.equals("#99CC33")) isOutputNode = true;
                }

                this.nodes.add(new Node(id, label, isPersonalizedNode, isOutputNode));
            }

            // edges
            JSONObject edgesListJsonObj = new JSONObject(edgesData);
            it = edgesListJsonObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = edgesListJsonObj.getString(key);
                JSONObject edgeJsonObj = new JSONObject(value);

                String from = edgeJsonObj.getString("from");
                String to = edgeJsonObj.getString("to");
                String label = "";
                try {
                  label = edgeJsonObj.getString("label");
                }
                catch (JSONException e) {
                   label= "";
                }

                this.edges.add(new Edge(from, to, label));
                Log.i("Js2Android", "edge finish");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Js2Android", "exception");
        }

    }

    @JavascriptInterface
    public int getNodesCount() {
        //Log.i("Android2Js", "getNodesCount");
        return nodes.size();
    }

    @JavascriptInterface
    public int getEdgesCount () {
        //Log.i("Android2Js", "getEdgesCount");
        return edges.size();
    }

    @JavascriptInterface
    public String getNodeId (int idx) {
        //Log.i("Android2Js", "getNodeId");
        return nodes.get(idx).id;
    }

    @JavascriptInterface
    public String getNodeLabel (int idx) {
        //Log.i("Android2Js", "getNodeLabel");
        return nodes.get(idx).label;
    }

    @JavascriptInterface
    public boolean getNodeIsPersonalized (int idx) {
        //Log.i("Android2Js", "getNodeIsPersonalized");
        return nodes.get(idx).isPersonalizedNode;
    }

    @JavascriptInterface
    public boolean getNodeIsOutput (int idx) {
        //Log.i("Android2Js", "getNodeIsOutput");
        return nodes.get(idx).isOutputNode;
    }

    @JavascriptInterface
    public String getEdgeFromNodeId (int idx) {
        //Log.i("Android2Js", "getEdgeFromNodeId");
        return edges.get(idx).fromNodeId;
    }

    @JavascriptInterface
    public String getEdgeToNodeId (int idx) {
        //Log.i("Android2Js", "getEdgeToNodeId");
        return edges.get(idx).toNodeId;
    }

    @JavascriptInterface
    public String getEdgeLabel (int idx) {
        //Log.i("Android2Js", "getEdgeLabel");
        return edges.get(idx).label;
    }
}