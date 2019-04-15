package com.betterwifisignal.betterwifisignal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView recyclerView;
    private WifiManager wifiManager;
    private List<WifiElementModel> nets; //tao mang dung de chua cac thong so can lay
    private TextView tv;
    private List<ScanResult> wifiList;
    static Context d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d = getApplicationContext();
        startService(new Intent(this, Service.class));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        initViews();


    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Service.YOUR_CUSTOM_ACTION);
        registerReceiver(mReceiver, filter);
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<? extends WifiElementModel> nets = intent.getParcelableArrayListExtra("s");
            loadList((List<WifiElementModel>) nets);
        }
    };


    // Initialize the view
    private void initViews() {

        recyclerView = (RecyclerView)
                findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //Set RecyclerView type according to intent value
        getSupportActionBar().setTitle("WFIFI LIST");
        recyclerView
                .setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed
    }

    public static void loadList(List<WifiElementModel> nets) {
        recyclerView.setAdapter(new ListAdapterWifi(d, nets));
        recyclerView.smoothScrollToPosition(0);
    }


}
