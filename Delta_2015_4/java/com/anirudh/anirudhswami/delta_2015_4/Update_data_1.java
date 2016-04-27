package com.anirudh.anirudhswami.delta_2015_4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Update_data_1 extends AppCompatActivity {

    DbHelper aniDb = new DbHelper(this);
    EditText nu;
    Button up,upPic;
    Bitmap theImage;
    byte[] image_he;
    ImageView image_here;

    String numb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_1);

        nu = (EditText) findViewById(R.id.phoneda);
        up=(Button) findViewById(R.id.confItUp);
        upPic = (Button) findViewById(R.id.upPic);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        final String name  = b.getString("nameIt");
        numb = b.getString("numb");
        nu.setText(numb);
        //image_he = b.getByteArray("img");
        theImage = (Bitmap) i.getParcelableExtra("img");
        setTitle(name);
        image_here = (ImageView) findViewById(R.id.imageView3);
        image_here.setImageBitmap(theImage);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        theImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        image_he = baos.toByteArray();

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numb = nu.getText().toString();
                if (numb.equals("")) {
                    Toast.makeText(Update_data_1.this, "Enter data to update", Toast.LENGTH_SHORT).show();
                } else {
                    boolean updated = aniDb.update_data(name, numb, image_he);
                    if (updated)
                        Toast.makeText(Update_data_1.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Update_data_1.this, "Data could NOT be updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Update_data_1.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        upPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            Bundle bdn = data.getExtras();
            Bitmap btm = (Bitmap) bdn.get("data");
            image_here.setImageBitmap(btm);
            theImage = btm;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            theImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            image_he = baos.toByteArray();
        }
    }


}
