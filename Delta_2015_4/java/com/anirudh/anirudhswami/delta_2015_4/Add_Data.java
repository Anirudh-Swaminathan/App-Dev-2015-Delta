package com.anirudh.anirudhswami.delta_2015_4;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Add_Data extends AppCompatActivity {
    EditText name;
    EditText number;
    DbHelper AniDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__data);

         name= (EditText) findViewById(R.id.nameAdd);
        number = (EditText) findViewById(R.id.numberAdd);
        AniDb = new DbHelper(this);
        ((Button) findViewById(R.id.addData)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String na = name.getText().toString();
                String nu = number.getText().toString();
                if(na.equals("")||nu.equals("")){
                    Toast.makeText(Add_Data.this,"Enter a name and number ",Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean inser = AniDb.insertData(na,nu);
                    if(inser == true){
                        Toast.makeText(Add_Data.this,"Data Inserted SCCESSFULLY!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Add_Data.this,"Data Could'nt be inserted Successfully",Toast.LENGTH_SHORT).show();
                    }



                }
                //update();
                Intent i = new Intent(Add_Data.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void update(){
        List<String> namesList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();

        String[] names;
        String[] numbers;

        DbHelper AniDb = new DbHelper(getApplicationContext());

        Cursor res = AniDb.getAllData();
        if(res.getCount()==0){
            return;
        }
        while (res.moveToNext()){
            namesList.add(res.getString(0));
            numberList.add(res.getString(1));
        }

        names=new String[namesList.size()];
        numbers = new String[numberList.size()];
        for(int i=0; i<namesList.size(); ++i){
            names[i]=namesList.get(i);
            numbers[i]=numberList.get(i);
        }

        ListAdapter aniAdapter = new CustomAdapter(getApplicationContext(),names,numbers);
        ListView contList = (ListView) findViewById(R.id.contList);
        contList.setAdapter(aniAdapter);
    }
}
