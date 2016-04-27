package com.anirudh.anirudhswami.delta_2015_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateData extends AppCompatActivity {
    DbHelper AniDb = new DbHelper(UpdateData.this);
    EditText edt = (EditText) findViewById(R.id.pNumb);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
    }
}
