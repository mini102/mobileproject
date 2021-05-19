package com.example.mobile_teamproject_bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
    private static final int NOTIFICATION_ID = 12;

    String foodNm;
    String recipeOrder;
    String ingredients;
    String foodimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        TextView main_name = findViewById(R.id.ented_name);
        TextView main_age = findViewById(R.id.ented_age);
        TextView main_height = findViewById(R.id.ented_height);
        TextView main_weight = findViewById(R.id.ented_weight);
        TextView main_disease = findViewById(R.id.ented_disease);
        TextView main_BMI = findViewById(R.id.ented_bfr);
        TextView main_body = findViewById(R.id.che);
        ImageView profile_picture = findViewById(R.id.imageView3);
        User user = new User();
        User_File file = new User_File();
        file.User_Read(user);
        main_name.setText(user.name);
        main_age.setText(user.age);
        main_height.setText(user.height);
        main_weight.setText(user.weight);
        main_body.setText(user.body);
        main_BMI.setText(user.body_fat_rate);
        main_disease.setText(user.disease);
        profile_picture.setImageResource(R.drawable.logo);
        TextView food_name = findViewById(R.id.food_name);
        ImageView food_img = findViewById(R.id.food_img);
        TextView food_info = findViewById(R.id.food_script);

        Body_File read_body = new Body_File();
        Disease_File read_disease = new Disease_File();
        ArrayList<Body> body = new ArrayList<Body>();
        ArrayList<Disease> disease = new ArrayList<Disease>();
        read_body.Body_Read(body);
        read_disease.Disease_Read(disease);

        System.out.println(disease.get(0).disease_name);
        System.out.println(disease.get(0).disease_foods.get(0));
        System.out.println(disease.get(0).disease_foods.get(1));

        Intent intent = getIntent();
        foodNm = intent.getStringExtra("foodName");
        recipeOrder = intent.getStringExtra("recipeOrder");
        ingredients = intent.getStringExtra("materialInfo");
        foodimg = intent.getStringExtra("foodImage");
        food_name.setText(intent.getStringExtra("foodName"));
        if(foodimg==null) {
            food_img.setImageResource(R.drawable.ic_lipid_icon);
        }
        else GlideApp.with(this).load(foodimg).into(food_img);
        food_info.setText(intent.getStringExtra("recipeOrder"));

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh");
        String getTime = simpleDateFormat.format(mDate);
        Log.d("currentTime",getTime);
        if(getTime == "12"){  //점심
            sendlaunchNotification();
        }
        else if(getTime == "18")  //저녁
            senddinnerNotification();
    }

    public void sendlaunchNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle("오늘의 추천 음식을 확인해보세요!");
        builder.setContentText("당신의 완벽한 점심을 위해 준비했습니다");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }

    public void senddinnerNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle("오늘의 추천 음식을 확인해보세요!");
        builder.setContentText("당신의 완벽한 저녁을 위해 준비했습니다");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }

    public void toRecipe(View target) {
        Intent intent = new Intent(getApplicationContext(), SubActivity.class);
        intent.putExtra("name",foodNm);
        intent.putExtra("recipe",recipeOrder);
        intent.putExtra("ingredients",ingredients);
        intent.putExtra("image",foodimg);
        startActivity(intent);
    }
    public void toExercising(View target) {
        Intent intent = new Intent(getApplicationContext(), ExercisingActivity.class);
        startActivity(intent);
    }
}
