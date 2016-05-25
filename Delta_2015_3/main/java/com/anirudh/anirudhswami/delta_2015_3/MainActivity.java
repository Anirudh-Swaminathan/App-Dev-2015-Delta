package com.anirudh.anirudhswami.delta_2015_3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView temp,pres,hum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = (TextView) findViewById(R.id.temp);
        pres = (TextView) findViewById(R.id.pres);
        hum = (TextView) findViewById(R.id.hum);

        ((Button) findViewById(R.id.getData)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String place = ((EditText) findViewById(R.id.place)).getText().toString();
                if(place.equals("")) place = "chennai";

                //Checking for wifi connectivity
                ConnectivityManager connMana = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                //boolean is3g = connMana.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
                //boolean isWifi = connMana.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                boolean is3g=false;
                boolean isWifi = false;
                NetworkInfo netac = connMana.getActiveNetworkInfo();
                if(netac!=null) {
                    if (netac.getType() == ConnectivityManager.TYPE_MOBILE) is3g = true;
                    if(netac.getType() == ConnectivityManager.TYPE_WIFI) isWifi = true;

                    if (is3g) {
                        Toast.makeText(MainActivity.this, "Fetching data for "+place+" using "+netac.getTypeName(), Toast.LENGTH_SHORT).show();
                        //loadSomeStuff loadSome = new loadSomeStuff();
                        new loadSomeStuff(temp,pres,hum).execute(place);
                        Toast.makeText(MainActivity.this, "Executed!!", Toast.LENGTH_SHORT).show();
                    } else if (isWifi) {

                        WifiManager wii = (WifiManager) MainActivity.this.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wii.getConnectionInfo();

                        String wifiName = null;

                        if (wifiInfo != null) {
                            NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                            if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                                wifiName = wifiInfo.getSSID();
                            }
                        }

                        Toast.makeText(MainActivity.this, "Fetching data for "+place+" using "+netac.getTypeName()+wifiName, Toast.LENGTH_SHORT).show();
                        new loadSomeStuff(temp,pres,hum).execute(place);
                        Toast.makeText(MainActivity.this, "Executed!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Not Connected to any Network!!!",Toast.LENGTH_SHORT).show();
                    WifiManager wii = (WifiManager) MainActivity.this.getSystemService(Context.WIFI_SERVICE);
                    //WifiInfo wifiInfo = wii.getConnectionInfo();
                    /*
                        NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                        if (!(state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR)) {
                            wii.setWifiEnabled(true);
                        }
                    */
                    boolean wifiEnabled = wii.isWifiEnabled();
                    if(!wifiEnabled){
                        wii.setWifiEnabled(true);
                        Toast.makeText(MainActivity.this,"Wifi has been enabled",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        ((Button) findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });
    }


}
