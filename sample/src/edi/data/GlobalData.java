package edi.data;

import java.util.ArrayList;

import edi.graph.Graph;
import edi.graph.OutputNode;

public class GlobalData {

    public static Graph graphPatternQuery = null;
    public static double latitude = 39.980885;
    public static double longitude = 116.351676;

    public static ArrayList<OutputNode> resultList = null;
    public static double time = -1;
    public static boolean isUpdated = false;
    public static String gridVistedCount = "";

    public static int isShake = 0;
}
