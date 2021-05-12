package com.example.mobile_teamproject_bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobile_teamproject_bundle.*;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
    }

    public void toInsert(View target) {
        Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
        startActivity(intent);
    }

    public void toExercisePlan(View target) {
        Intent intent = new Intent(getApplicationContext(), ExercisePlanActivity.class);
        startActivity(intent);
    }
}
