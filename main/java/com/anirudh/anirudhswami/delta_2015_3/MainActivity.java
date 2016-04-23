package com.anirudh.anirudhswami.delta_2015_3;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView temp,pres,hum;
    private String url = "http://api.openweathermap.org/data/2.5/weather?q=chennai&mode=xml&APPID=0d307ddfc720ee6d1235886a6bad152a";
    private GetDataXML gdata;
    String[] data;


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

                gdata = new GetDataXML(url);

                gdata.getXML();
                while (!gdata.parsingComplete);
                data = gdata.return_data();

                temp.setText(data[0]);
                pres.setText(data[1]);
                hum.setText(data[2]);

            }
        });
        ((Button) findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });
    }
}
