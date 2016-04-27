package com.anirudh.anirudhswami.delta_2015_4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by Anirudh Swami on 26-04-2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    public String[] numbs;
    public Bitmap[] imgs;
    Context context;
    //DbHelper AniDb = new DbHelper(context);
    public CustomAdapter(Context context,String[] names,String[] numbers,Bitmap[] photos) {
        super(context,R.layout.custom_row,names);
        this.numbs = numbers;
        this.context = context;
        this.imgs = photos;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        LayoutInflater aniInflater = LayoutInflater.from(getContext());
        View customView = aniInflater.inflate(R.layout.custom_row, parent, false);
        final String currName = getItem(position);

        TextView na = (TextView) customView.findViewById(R.id.nameInList);
        ImageView ima = (ImageView) customView.findViewById(R.id.imageView);
        TextView nu = (TextView) customView.findViewById(R.id.numbInList);
        Button upda = (Button) customView.findViewById(R.id.upData);
        Button dele = (Button) customView.findViewById(R.id.del);

        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder abui = new AlertDialog.Builder(context);
                abui.setMessage("Do You Want to delete this Data??").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbHelper db = new DbHelper(context);
                        Integer i = db.delete_data(currName);
                        if (i > 0) {
                            Toast.makeText(context, "Data Deleted SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            Intent p = new Intent(context, MainActivity.class);
                            context.startActivity(p);
                        } else {
                            Toast.makeText(context, "Data NOT Deleted", Toast.LENGTH_SHORT).show();
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

        upda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder abui = new AlertDialog.Builder(context);
                abui.setMessage("Do You Want to update this Data??").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DbHelper db = new DbHelper(context);
                        Bitmap bitmap = imgs[position];
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        //byte[] photo = baos.toByteArray();

                        Intent i = new Intent(context, Update_data_1.class);
                        i.putExtra("nameIt", currName).putExtra("img",bitmap).putExtra("numb",numbs[position]);
                        context.startActivity(i);
                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = abui.create();
                alertDialog.setTitle("UPDATE!!");
                alertDialog.show();
            }
        });

        na.setText(currName);
        nu.setText(numbs[position]);
        //ima.setImageResource(R.mipmap.songy1);
        ima.setImageBitmap(imgs[position]);
        return customView;
    }
}
