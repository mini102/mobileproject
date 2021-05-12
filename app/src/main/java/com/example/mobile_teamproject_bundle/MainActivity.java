package com.example.mobile_teamproject_bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    //String keyword;
    //private final Activity context;

    /*public MainActivity(Activity context) {
        this.context = context;
    }*/

    String foodNm;
    String recipeOrder;
    String ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
                TextView main_name = findViewById(R.id.ented_name);
                TextView main_age = findViewById(R.id.ented_age);
                TextView main_height = findViewById(R.id.ented_height);
                TextView main_weight = findViewById(R.id.ented_weight);
                TextView main_disease = findViewById(R.id.ented_disease);
                TextView food_name = findViewById(R.id.food_name);
                ImageView food_img = findViewById(R.id.food_img);
                TextView food_info = findViewById(R.id.food_script);

        Intent intent = getIntent();
        foodNm = intent.getStringExtra("foodName");
        recipeOrder = intent.getStringExtra("recipeOrder");
        ingredients = intent.getStringExtra("materialInfo");
        /*Log.d("음식이름",foodNm);
        Log.d("레시피",recipeOrder);
        Log.d("재료",ingredients);*/
        food_name.setText(intent.getStringExtra("foodName"));
        GlideApp.with(this).load(intent.getStringExtra("foodImage")).into(food_img);
        food_info.setText(intent.getStringExtra("recipeOrder"));
//                main_name.setText();
//                main_age.setText();
//                main_height.setText();
//                main_weight.setText();
//                main_disease.setText();
    }

    public void toRecipe(View target) {
        Intent intent = new Intent(getApplicationContext(), SubActivity.class);
        intent.putExtra("name",foodNm);
        intent.putExtra("recipe",recipeOrder);
        intent.putExtra("ingredients",ingredients);
        startActivity(intent);
    }
    public void toExercising(View target) {
        Intent intent = new Intent(getApplicationContext(), ExercisingActivity.class);
        startActivity(intent);
    }

}
