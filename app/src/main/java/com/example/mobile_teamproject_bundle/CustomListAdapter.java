package com.example.mobile_teamproject_bundle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    String[] titles = new String[60];
    Integer[] prices = new Integer[60];
    String[] marks = new String[60];
    String[] images = new String[60];

    public CustomListAdapter(Activity context,String[] object,String[] img,String[] markets,Integer[] price) {
        super(context, R.layout.detail, object);
        titles = object;
        images = img;
        marks = markets;
        prices = price;
        this.context = context;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.detail, null, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView market = (TextView) rowView.findViewById(R.id.market);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        title.setText(titles[position]);
        market.setText("판매처: "+marks[position]);
        price.setText("가격: "+Integer.toString(prices[position]));
        //imageView.setImageResource(images[position]);
        /*try {
            URL url = new
                    URL(images[position]);
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new
                    BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            imageView.setImageBitmap(bm);
        } catch (IOException e) {
            Log.d("errer","IOException");
        }*/
        //imageView.setImageURI(Uri.parse(images[position]));
        Glide.with(context).load(images[position]).into(imageView);
        return rowView;
    }
}