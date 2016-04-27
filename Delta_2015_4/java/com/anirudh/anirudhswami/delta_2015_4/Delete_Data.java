package com.anirudh.anirudhswami.delta_2015_4;

import android.content.Context;

import android.widget.Toast;


/**
 * Created by Anirudh Swami on 26-04-2016.
 */
public class Delete_Data {
    String name;
    Context context;
    DbHelper AniDb = new DbHelper(context);
    public Delete_Data(String a,Context c){
        this.name = a;
        this.context = c;
    }
    public void del(){
        Integer deleted = AniDb.delete_data(name);
        if(deleted>0){
            Toast.makeText(context, "Data DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Data NOT DELETED ",Toast.LENGTH_SHORT).show();
        }
    }

}
