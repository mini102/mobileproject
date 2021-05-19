package com.example.mobile_teamproject_bundle;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class exerciseListAdapter extends ArrayAdapter {
    private final Activity context;
    String[] exercises;
    //private boolean see = false;

    public  exerciseListAdapter(Activity context, String[] object){//,boolean watch) {
        super(context, R.layout.detail, object);
        this.context = context;
        exercises = object;
        //see = watch;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.rating_exercise, null, false);
        TextView exercise = (TextView) rowView.findViewById(R.id.textView5);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.ratingBar);
        exercise.setText(exercises[position]);
        return rowView;
    }
}
