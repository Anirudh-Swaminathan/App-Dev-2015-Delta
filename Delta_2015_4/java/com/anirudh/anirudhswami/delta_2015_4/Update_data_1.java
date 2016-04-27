package com.anirudh.anirudhswami.delta_2015_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_data_1 extends AppCompatActivity {

    DbHelper aniDb = new DbHelper(this);
    EditText nu;
    Button up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_1);

        nu = (EditText) findViewById(R.id.phoneda);
        up=(Button) findViewById(R.id.confItUp);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        final String name  = b.getString("nameIt");
        setTitle(name);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numb = nu.getText().toString();
                if(numb.equals("")){
                    Toast.makeText(Update_data_1.this,"Enter data to update",Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean updated = aniDb.update_data(name,numb);
                    if(updated) Toast.makeText(Update_data_1.this,"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(Update_data_1.this,"Data could NOT be updated",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Update_data_1.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
