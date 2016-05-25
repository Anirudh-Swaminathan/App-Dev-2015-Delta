package com.anirudh.anirudhswami.delta_2015_3;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.anirudh.anirudhswami.delta_2015_3.GetDataXML;
import com.anirudh.anirudhswami.delta_2015_3.R;

public class loadSomeStuff extends AsyncTask<String,Void, String[]> {

    public loadSomeStuff(TextView a,TextView b,TextView c){
        this.temp=a;
        this.pres = b;
        this.hum = c;
    }

    private GetDataXML gdata;
    String[] data;
    String p = null;
    //private final String url = "http://api.openweathermap.org/data/2.5/weather?q=chennai&mode=xml&APPID=0d307ddfc720ee6d1235886a6bad152a";
    TextView temp,pres,hum;

    /*
    protected void onPreExecute(String f){
        //example
        f = "Anirudh";
    }
    */

    @Override
    protected String[] doInBackground(String... params) {
         p= params[0];
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+p+"&mode=xml&APPID=0d307ddfc720ee6d1235886a6bad152a";


        gdata = new GetDataXML(url);

        gdata.getXML();
        while (!gdata.parsingComplete);
        data = gdata.return_data();
        return data;
        //return new String[0];
    }

    /*
        protected void onProgressUpdate(Integer... progress){
            //do nothing
        }
        */
    @Override
    protected void onPostExecute(String[] strings) {
        //super.onPostExecute(strings);

        //temp = (TextView) view.findViewById(R.id.temp);
        //pres = (TextView) view.findViewById(R.id.pres);
        //hum = (TextView) view.findViewById(R.id.hum);

        double t = Double.parseDouble(strings[0]);
        t -= 273.15;
        String apk = String.format("%.5f",t);
        String msg = apk+" \u00B0C";

        temp.setText(msg);
        pres.setText(strings[1]+" hPa");
        hum.setText(strings[2]+" %");
    }
}