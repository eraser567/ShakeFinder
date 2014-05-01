package edi.query;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import edi.data.GlobalData;
import edi.graph.Graph;
import edi.graph.GraphSerializable;
import edi.graph.OutputNode;

public class Query {

    public static ArrayList<OutputNode> query (final Graph g, final double latitude, final double longitude, Handler h) {

//        new Thread() {
//
//        }.start();

        Log.i("Server2Client", "thread start!");

        MyThread mt = new MyThread(g, latitude, longitude, h);
        mt.start();

        return null;
    }

}

class MyThread extends Thread {
    public Graph g;
    public double latitude;
    public double longitude;
    boolean hasStop = false;
    Handler h = null;

    public MyThread(final Graph g, final double latitude, final double longitude, Handler h) {
        super();
        this.g = g;
        this.latitude = latitude;
        this.longitude = longitude;
        this.h = h;
    }

    @Override
    public void run() {
        Log.i("Server2Client", "thread start, really!");
        try {
            if (g.nodes.size()==0) {
                return;
            }

            Socket s = new Socket ("115.28.203.82", 30000);
            s.setSoTimeout(3000);
            Log.i("Server2Client", "thread start, here!");

            GraphSerializable gs = new GraphSerializable(g.nodes, g.edges, latitude, longitude);
            /*g.nodes.add(new NodeSerializable("0", "Me (Ruizhe Huang)", true, false));
            g.nodes.add(new NodeSerializable("1", "database, big data", false, false));
            g.nodes.add(new NodeSerializable("2", "android, app", false, false));
            g.nodes.add(new NodeSerializable("3", "ui design", false, true));

            g.edges.add(new EdgeSerializable ("0", "1", "friend"));
            g.edges.add(new EdgeSerializable("0", "2", "friend"));
            g.edges.add(new EdgeSerializable ("1", "2", "friend"));
            g.edges.add(new EdgeSerializable("2", "3", "friend"));*/

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            oos.writeObject(gs);
            ArrayList<OutputNode> resultList = null;
            try {
                resultList = (ArrayList<OutputNode>) ois.readObject();
            } catch (Exception e) {
                resultList = new ArrayList<OutputNode>();
                Log.i("Server2Client", "object read error");
            }
            if (resultList.size()>0) {
                GlobalData.time = resultList.get(resultList.size()-1).latitude;
                GlobalData.gridVistedCount=resultList.get(resultList.size()-1).label;
                resultList.remove(resultList.size()-1);
                Log.i("Server2Client", "resultList.size=" + resultList.size());
            }
            else {
                GlobalData.time=0;
                GlobalData.gridVistedCount="";
                Log.i("Server2Client", "resultList.size=" + 0);
            }
            GlobalData.resultList=resultList;
            GlobalData.isUpdated = true;
            hasStop = true;

            Message msg = new Message();
            msg.what = 0x123;
            msg.obj = resultList;
            h.sendMessage(msg);

            Log.i("Server2Client", "answer is set!");

            oos.close();
            ois.close();
        }
        catch (IOException e) {
            Log.i("Server2Client", "thread start, sorry, exception!");
            e.printStackTrace();
        }

        Log.i("Server2Client", "thread finish!");
    }
}