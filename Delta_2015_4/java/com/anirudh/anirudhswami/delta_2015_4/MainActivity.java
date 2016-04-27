package com.anirudh.anirudhswami.delta_2015_4;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> namesList = new ArrayList<>();
    List<String> numberList = new ArrayList<>();

    String[] names;
    String[] numbers;

    DbHelper AniDb = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getNames();
        names=new String[namesList.size()];
        numbers = new String[numberList.size()];
        for(int i=0; i<namesList.size(); ++i){
            names[i]=namesList.get(i);
            numbers[i]=numberList.get(i);
        }

        ListAdapter aniAdapter = new CustomAdapter(MainActivity.this,names,numbers);
        ListView contList = (ListView) findViewById(R.id.contList);
        contList.setAdapter(aniAdapter);

        contList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = String.valueOf(parent.getItemAtPosition(position));
                ImageView curr_img = (ImageView) view.findViewById(R.id.imageView);
                String number = numbers[position];

                Toast.makeText(MainActivity.this, "Hi. Thanks for clicking", Toast.LENGTH_SHORT).show();

                curr_img.buildDrawingCache();
                Bitmap curri = curr_img.getDrawingCache();
                Bundle extras = new Bundle();
                extras.putParcelable("curri", curri);
                Intent inta = new Intent(MainActivity.this, Contact_Content.class);
                inta.putExtra("name", name).putExtra("number", number);
                inta.putExtras(extras);
                startActivity(inta);

            }
        });
        ((Button) findViewById(R.id.order)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(namesList);
                Collections.reverse(numberList);

                names = new String[namesList.size()];
                numbers = new String[numberList.size()];
                for (int i = 0; i < namesList.size(); ++i) {
                    names[i] = namesList.get(i);
                    numbers[i] = numberList.get(i);
                }

                ListAdapter aniAdapter = new CustomAdapter(MainActivity.this, names, numbers);
                ListView contList = (ListView) findViewById(R.id.contList);
                contList.setAdapter(aniAdapter);
            }
        });

        ((Button) findViewById(R.id.ser_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numb = ((EditText) findViewById(R.id.ser_txt)).getText().toString();
                boolean fond = false;
                for(int i=0; i<numbers.length; i++){
                    if(numbers[i].equals(numb)){
                        Toast.makeText(MainActivity.this,"Contact Found. Name: "+names[i],Toast.LENGTH_SHORT).show();
                        fond = true;
                        break;
                    }
                }
                if(!fond) Toast.makeText(MainActivity.this,"Contact Missing",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.AddIt:
                Toast.makeText(MainActivity.this,"Adding-Data",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,Add_Data.class);
                startActivity(i);
                finish();
                break;
            default:
                Toast.makeText(MainActivity.this,"HELLO!!",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getNames(){
        Cursor res = AniDb.getAllData();
        if(res.getCount()==0){
            return;
        }
        while (res.moveToNext()){
            namesList.add(res.getString(0));
            numberList.add(res.getString(1));
        }
    }
}
