package com.anirudh.anirudhswami.delta_2015_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anirudh Swami on 15-04-2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    Bitmap[] imags;
    public CustomAdapter(Context context, String[] names,Bitmap[] images) {
        super(context, R.layout.custom_row,names);
        this.imags = images;
    }

    @Override
    public View getView(int position,View convertView , ViewGroup parent){
        LayoutInflater aniInflate = LayoutInflater.from(getContext());
        View customView = aniInflate.inflate(R.layout.custom_row, parent, false);

        String currName = getItem(position);
        //String curr_img = getItem(position);
        TextView tv = (TextView) customView.findViewById(R.id.contName);
        ImageView imageView = (ImageView) customView.findViewById(R.id.contImg);

        tv.setText(currName);

        if(imags[position]==null) imageView.setImageResource(R.mipmap.songy1);
        else {
            imageView.setImageBitmap(imags[position]);
        }
        //Bitmap bm = ;
        //imageView.setImageBitmap();
        return customView;
    }
}
