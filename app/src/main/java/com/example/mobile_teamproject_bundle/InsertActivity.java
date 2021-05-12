package com.example.mobile_teamproject_bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mobile_teamproject_bundle.*;
import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {
    public DbOpenHelper user = new DbOpenHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);
        Button finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*EditText name = (EditText) findViewById(R.id.ent_name);
                String user_name = String.valueOf(name.getText());
                //System.out.printf(user_name);
                EditText age = (EditText) findViewById(R.id.ent_age);
                String user_age = String.valueOf(age.getText());
                EditText height = (EditText) findViewById(R.id.ent_height);
                String user_height = String.valueOf(height.getText());
                EditText weight = (EditText) findViewById(R.id.ent_weight);
                String user_weight = String.valueOf(weight.getText());
                EditText disease = (EditText) findViewById(R.id.ent_disease);
                String user_disease = String.valueOf(disease.getText());*/

//                TextView main_name = findViewById(R.id.ented_name);
//                TextView main_age = findViewById(R.id.ented_age);
//                TextView main_height = findViewById(R.id.ented_height);
//                TextView main_weight = findViewById(R.id.ented_weight);
//                TextView main_disease = findViewById(R.id.ented_disease);
//                main_name.setText("kimt");
        //        main_age.setText(user_age);
        //        main_height.setText(user_height);
        //        main_weight.setText(user_weight);
        //        main_disease.setText(user_disease);


                //user.insertColumn(user_name,user_age,user_height,user_weight,user_disease);
                Intent intent = new Intent(InsertActivity.this, Select_Diet.class);
                startActivity(intent);
                //intent = new Intent(InsertActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

    }
}
