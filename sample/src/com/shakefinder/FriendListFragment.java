package com.shakefinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FriendListFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static FriendListFragment newInstance(int position) {
        FriendListFragment f = new FriendListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
                .getDisplayMetrics());

        /*TextView v = new TextView(getActivity());
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundResource(R.drawable.background_card);
        v.setText("CARD " + (position + 1));

        fl.addView(v);*/

        //Log.v("MyMessage", "web view");

        ListView myListView = new ListView(this.getActivity());//(ListView) (getActivity().findViewById(R.id.listView));

        List<Map<String, Object>> itemsList = new ArrayList<Map<String, Object>>();
        //把该显示的内容放到list中
//        for (int i = 0; i < 10; i++)
//        {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("image", R.drawable.usericon);
//            item.put("textview1", "John Smith");
//            itemsList.add(item);
//        }
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Yang Cao");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Chao Tian");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Daifei Wang");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "John Smith");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Sharon Shen");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Roger Federer");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Charlotte Brontë");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Ming Li");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Xingxing Zhang");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Sheldon Cooper");
        itemsList.add(item);
        item = new HashMap<String, Object>();
        item.put("image", R.drawable.usericon);
        item.put("textview1", "Franklin Yang");
        itemsList.add(item);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this.getActivity(),
                itemsList,
                R.layout.friendlist,
                new String[]{ "textview1", "image" },
                new int[]{ R.id.textview1,  R.id.image}
        );

        myListView.setAdapter(simpleAdapter);
        fl.addView(myListView);

        return fl;

    }

}
