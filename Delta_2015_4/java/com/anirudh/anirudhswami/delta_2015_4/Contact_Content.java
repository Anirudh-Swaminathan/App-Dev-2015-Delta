package com.anirudh.anirudhswami.delta_2015_4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Contact_Content extends AppCompatActivity {
    ImageView img_here;
    TextView phone;
    Button call,update,delete;
    Bitmap btm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__content);

        Intent inta = getIntent();
        Bundle b = inta.getExtras();
        Bitmap btm = (Bitmap) b.get("curri");
        btm1=btm;
        final String name  = b.getString("name");
        final String number = b.getString("number");

        setTitle(name);

        img_here = (ImageView) findViewById(R.id.pic);
        phone = (TextView) findViewById(R.id.the_num);
        call = (Button) findViewById(R.id.call);
        update = (Button) findViewById(R.id.upitda);
        delete = (Button) findViewById(R.id.deleit);

        img_here.setImageBitmap(btm);
        phone.setText(number);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder abui = new AlertDialog.Builder(Contact_Content.this);
                abui.setMessage("Do You Want to delete this Data??").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbHelper db = new DbHelper(Contact_Content.this);
                        Integer i = db.delete_data(name);
                        if (i > 0) {
                            Toast.makeText(Contact_Content.this, "Data Deleted SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            Intent p = new Intent(Contact_Content.this, MainActivity.class);
                            startActivity(p);
                        } else {
                            Toast.makeText(Contact_Content.this, "Data NOT Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = abui.create();
                alertDialog.setTitle("DELETE!!");
                alertDialog.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap =btm1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                Intent p = new Intent(Contact_Content.this,Update_data_1.class);
                p.putExtra("nameIt", name).putExtra("img",bitmap).putExtra("numb",number);
                startActivity(p);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));
                startActivity(call);
            }
        });
    }
}
