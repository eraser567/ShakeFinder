package com.shakefinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edi.data.GlobalData;


public class ResultListFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    TextView myTextView = null;
    ListView myListView = null;
    List<Map<String, Object>> itemsList = null;

    public static ResultListFragment newInstance(int position) {
        ResultListFragment f = new ResultListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public void onResume () {
        super.onResume();
        Log.i("Server2Client", "onResume! GlobalData.isShake=" + GlobalData.isShake);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
                .getDisplayMetrics());*/

        View rootView = inflater.inflate(R.layout.fragment_findings, container, false);

        // TextView
        myTextView = (TextView) (rootView.findViewById(R.id.result_time));
        //myTextView.setText("About 110 results (0.48 seconds)");

        // ListView
        myListView = (ListView) (rootView.findViewById(R.id.result_listView));

       itemsList = new ArrayList<Map<String, Object>>();
        //把该显示的内容放到list中
        /*if (GlobalData.resultList==null || GlobalData.resultList.size()==0) {
            myTextView.setText("Hmm...It seems no such people is around ("+GlobalData.time+" seconds)");
        }
        else {
            myTextView.setText(""+GlobalData.resultList.size()+" people are found around you ("+GlobalData.time+" seconds)");
            for (OutputNode mn : GlobalData.resultList) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", R.drawable.usericon);
            item.put("name", mn.id);
            item.put("distance", mn.distance);
            item.put("keyword", mn.label);
            itemsList.add(item);

            }
        }*/


//        for (int i = 0; i < 10; i++)
//        {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("image", R.drawable.usericon);
//            item.put("name", "Harry Taylor");
//            item.put("distance", "800m");
//            item.put("keyword", "ui design");
//            itemsList.add(item);
//        }

//        SimpleAdapter simpleAdapter = new SimpleAdapter(
//            this.getActivity(),
//            itemsList,
//            R.layout.resultlist,
//            new String[]{ "image", "name", "distance", "keyword" },
//            new int[]{ R.id.result_image,  R.id.result_textview_name, R.id.result_textview_distance, R.id.result_textview_keyword}
//        );
//        myListView.setAdapter(simpleAdapter);

        return rootView;

    }

};

