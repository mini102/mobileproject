package com.example.mobile_teamproject_bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ExercisePlanActivity  extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrfs";
    int[] rating = new int[3];
    int[] weeklyRating = new int[21];
    int rate;
    int result;
    ArrayList<Integer> week = new ArrayList<Integer>();
    boolean IsNew = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_weekly);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        int Size = settings.getInt("size", 0);
        for (int j = 0; j < Size; j++) {
            week.add(settings.getInt(String.valueOf(j), 0));
        }

        Intent intent = getIntent();
            int size = intent.getIntExtra("size", 0);
            String[] exersixes = intent.getStringArrayExtra("exercise");
            for (int j = 0; j < size; j++) {
                rating[j] = intent.getIntExtra(String.valueOf(j), 0);
                Log.d("j", String.valueOf(rating[j]));
                rate += rating[j];
                if (IsNew) {
                    week.add(j, rating[j]);
                } else {
                    week.set(j, week.get(j) + rating[j]);
                }
            }

        ListView monday = (ListView) findViewById(R.id.mon);
        ListView t = (ListView) findViewById(R.id.Tues);
        ListView w = (ListView) findViewById(R.id.W);
        ListView thur= (ListView) findViewById(R.id.Thursday);
        ListView fri = (ListView) findViewById(R.id.Fri);
        ListView sat = (ListView) findViewById(R.id.Satur);
        ListView sun = (ListView) findViewById(R.id.Sun);

        ProgressBar Mprogress = findViewById(R.id.Monday3);
        ProgressBar Tprogress = findViewById(R.id.Tuesday4);
        ProgressBar Wprogress = findViewById(R.id.W5);
        ProgressBar Thprogress = findViewById(R.id.ThursDay6);
        ProgressBar Fprogress = findViewById(R.id.FriDay7);
        ProgressBar Saprogress = findViewById(R.id.Satur8);
        ProgressBar Sunprogress = findViewById(R.id.Sun9);

    }
    public void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("size",week.size());
        for(int i=0;i<week.size();i++) {
            //Log.d("save", String.valueOf(rating[i]));
            editor.putInt(String.valueOf(i), week.get(i));
        }
        editor.commit();
    }
}
