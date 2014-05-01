package com.shakefinder;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;


import java.util.HashMap;
import java.util.Map;

import edi.data.GlobalData;
import edi.graph.OutputNode;
import edi.query.Query;


public class WebViewFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public WebView webview = null;
    public ViewPager viewPager = null;

    private SensorManager sensorManager;
    private Vibrator vibrator;
    private static final String TAG = "TestSensorActivity";
    private static final int SENSOR_SHAKE = 10;

    private LocationManager locationManager;

    public ResultListFragment rlf = null;

    public static WebViewFragment newInstance(int position, ViewPager vp, ResultListFragment rlf) {
        WebViewFragment f = new WebViewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        f.setViewPager(vp);
        f.rlf = rlf;
        return f;
    }

    public void setViewPager (ViewPager vp) {
        viewPager = vp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);

        // ----- vibration -----
        sensorManager = (SensorManager) this.getActivity().getSystemService(this.getActivity().SENSOR_SERVICE);
        vibrator = (Vibrator) this.getActivity().getSystemService(this.getActivity().VIBRATOR_SERVICE);

        // ----- location -----
        locationManager = (LocationManager) this.getActivity().getSystemService(this.getActivity().LOCATION_SERVICE);//获取手机位置信息
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                GlobalData.latitude = location.getLatitude();
                GlobalData.longitude = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);//GPS_PROVIDER
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
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

        webview = new WebView(getActivity());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/vis2/examples/graph/21_data_manipulation.html");
        webview.addJavascriptInterface(GlobalData.graphPatternQuery, "graphPatternQuery");

        fl.addView(webview);

        return fl;

    }

    /**
     * onPause() The system calls this method as the first indication
     * that the user is leaving the fragment (though it does not always
     * mean the fragment is being destroyed). This is usually where
     * you should commit any changes that should be persisted beyond
     * the current user session (because the user might not come back).
     */
    @Override
    public void onPause() {
        //Log.d("MyMessage", "onPause!!!!!!!!!!!!!");

        webview.loadUrl("javascript:onPauseCall()");

        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }

        super.onPause();
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        // 两次检测的时间间隔
        private static final int UPTATE_INTERVAL_TIME = 2000;
        private long lastUpdateTime = 0;

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            //Log.i(TAG, "x轴方向的重力加速度" + x +  "；y轴方向的重力加速度" + y +  "；z轴方向的重力加速度" + z);
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 14;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了

            // 现在检测时间
            long currentUpdateTime = System.currentTimeMillis();
            // 两次检测的时间间隔
            long timeInterval = currentUpdateTime - lastUpdateTime;
            // 判断是否达到了检测时间间隔
            if (timeInterval < UPTATE_INTERVAL_TIME)
                return;
            // 现在的时间变成last时间
            lastUpdateTime = currentUpdateTime;

            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                shakeHandler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 动作执行
     */
    Handler shakeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:
                    Log.i("Server2Client", "检测到摇晃，执行操作！");

                    webview.loadUrl("javascript:onPauseCall()");

                    // graph pattern matching

                    //Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    //if (lastKnownLocation==null) Log.i("location", "lastKnownLocation=null!!!!!!!!!!!!");
//                    Log.i("location", "global:"+GlobalData.latitude);
//                    Log.i("location", "global:"+GlobalData.longitude);
//                    Log.i("location", lastKnownLocation.toString());
//                    Log.i("location", ""+lastKnownLocation.getLatitude());
//                    Log.i("location", ""+lastKnownLocation.getLongitude());

//                    GlobalData.resultList=null;
//                    GlobalData.isUpdated = false;
//                    Query.query(GlobalData.graphPatternQuery, GlobalData.latitude, GlobalData.longitude);

                    Log.i("Server2Client", "set GlobalData.isShake=0");
                    GlobalData.isShake = 0;

                    if (GlobalData.isShake == 0) {
                        rlf.myTextView.setText("Wait a second, huh?");

                        GlobalData.resultList=null;
                        GlobalData.isUpdated = false;

                        SimpleAdapter simpleAdapter = new SimpleAdapter(
                                rlf.getActivity(),
                                rlf.itemsList,
                                R.layout.resultlist,
                                new String[]{ "image", "name", "distance", "keyword" },
                                new int[]{ R.id.result_image,  R.id.result_textview_name, R.id.result_textview_distance, R.id.result_textview_keyword}
                        );
                        rlf.itemsList.clear();
                        rlf.myListView.setAdapter(simpleAdapter);



                        Handler handler = new Handler() {
                            public void handleMessage (Message msg) {
                                Log.i("Server2Client", " I am in the handler!");

                                if (msg.what == 0x123) {
                                    if (!GlobalData.isUpdated) {
                                        rlf.myTextView.setText("Result is not update :(");
                                    }
                                    else if (GlobalData.resultList==null || GlobalData.resultList.size()==0) {
                                        rlf.myTextView.setText("Hmm...It seems no such people is around ("+GlobalData.time+" seconds)");
                                    }
                                    else {
                                        rlf.itemsList.clear();
                                        String num = Integer.toString(GlobalData.resultList.size());
                                        if (GlobalData.resultList.size() == 100) {
                                            num += "+";
                                        }
                                        rlf.myTextView.setText(""+num+" people are found around you ("+GlobalData.time+" seconds)\n" + GlobalData.gridVistedCount);
                                        for (OutputNode mn : GlobalData.resultList) {
                                            Map<String, Object> item = new HashMap<String, Object>();
                                            item.put("image", R.drawable.usericon);
                                            item.put("name", mn.id);
                                            item.put("distance", mn.distance + " m");
                                            item.put("keyword", mn.label);
                                            rlf.itemsList.add(item);
                                        }
                                        SimpleAdapter simpleAdapter = new SimpleAdapter(
                                                rlf.getActivity(),
                                                rlf.itemsList,
                                                R.layout.resultlist,
                                                new String[]{ "image", "name", "distance", "keyword" },
                                                new int[]{ R.id.result_image,  R.id.result_textview_name, R.id.result_textview_distance, R.id.result_textview_keyword}
                                        );
                                        rlf.myListView.setAdapter(simpleAdapter);
                                    }
                                }
                            }
                        };

                        Query.query(GlobalData.graphPatternQuery, GlobalData.latitude, GlobalData.longitude, handler);
                        GlobalData.isShake=1;
                    }


                    viewPager.setCurrentItem(3, true);
                    break;
            }
        }

    };

}
