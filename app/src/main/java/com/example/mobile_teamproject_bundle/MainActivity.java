package com.example.mobile_teamproject_bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
    private static final int NOTIFICATION_ID = 12;
    public static final String PREFS_NAME = "Prefs";

    String foodNm;
    String recipeOrder;
    String ingredients;
    String foodimg;
    String category;

    String exercise1;
    String exercise2;
    String exercise3;
    boolean New_recommend;
    int first = 0;

    //ArrayList<String> ex_list = new ArrayList<String>();
    //ArrayList<String> x_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int Size = settings.getInt("size", 0);
        for(int i=0;i<Size;i++) {
            x_list.add(settings.getString(String.valueOf(i),"전 리스트가 없음"));
        }*/
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
        category = intent.getStringExtra("calorieInfo");

        food_name.setText(intent.getStringExtra("foodName"));
        if(foodimg==null) {
            food_img.setImageResource(R.drawable.noimg);
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
        int randomValue;
        int randomValue_exercise;

        ArrayList<Exercise> exercise_list = new ArrayList<Exercise>();
        Exercise_File read_exercise = new Exercise_File();
        read_exercise.Exercise_Read(exercise_list);
        System.out.println(exercise_list.get(0).exercise_name);
        System.out.println(exercise_list.get(0).exercise_names.get(0));
        randomValue = (int) (Math.random()*(user.diseases.size()-1));
        System.out.println(user.diseases.get(randomValue));

        ListView ex_view = (ListView) findViewById(R.id.exercise_listview);
        ArrayList<String> ex_list = new ArrayList<String>();

        for(int i=0;i<exercise_list.size();i++){
            if(user.diseases.get(randomValue).equals(exercise_list.get(i).disease_name)){
                int a[] = new int[3];
                for(int k=0; k<3; k++) {
                    //Log.d("k", String.valueOf(k));
                    a[k]=(int) (Math.random()*(exercise_list.get(i).exercise_names.size()-1));
                    //Log.d("a[k]", String.valueOf(a[k]));
                    for(int j=0; j<k;j++) {
                        //Log.d("j", String.valueOf(j));
                        if(a[k]==a[j]) {
                            //Log.d("k","--");
                            //k--;
                            break;
                        }
                    }
                }
                //Log.d("j","end");
                exercise1 = exercise_list.get(i).exercise_names.get(a[0]);
                exercise2 = exercise_list.get(i).exercise_names.get(a[1]);
                exercise3 = exercise_list.get(i).exercise_names.get(a[2]);
                ex_list.add(exercise_list.get(i).exercise_names.get(a[0]));
                ex_list.add(exercise_list.get(i).exercise_names.get(a[1]));
                ex_list.add(exercise_list.get(i).exercise_names.get(a[2]));

                //Log.d("before", String.valueOf(x_list));
                Log.d("after", String.valueOf(ex_list));
                //Log.d("first", String.valueOf(first));

                ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.simple_list_item_1,ex_list);
                ex_view.setAdapter(ad);
                ex_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id){
                        Intent intent = new Intent(getApplicationContext(), ExercisingActivity.class);
                        //boolean back = intent.getBooleanExtra("back",false);
                        //Log.d("back", String.valueOf(back));
                        if(first!=0 ){//|| x_list.equals(ex_list) && ex_list.containsAll(x_list)) {
                            Log.d("flase","haha");
                            New_recommend = false; //rating복구
                        }
                        else if(first==0){
                            Log.d("true","haha");
                            New_recommend = true;  //0
                            first++;
                        }
                        intent.putExtra("운동",ad.getItem(position).toString());
                        String[] array = ex_list.toArray(new String[ex_list.size()]);
                        intent.putExtra("recommended",array);
                        intent.putExtra("IsNew",New_recommend);
                        startActivity(intent);//startActivity(intent);
//                        Toast.makeText(getApplicationContext(),
//                                myAdapter.getItem(position).getMovieName(),
//                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
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
        Log.d("ingredient",ingredients);
        intent.putExtra("ingredients",ingredients);
        intent.putExtra("image",foodimg);
        intent.putExtra("info",category);
        startActivity(intent);
    }

    public void toWeek(View target) {
        Intent intent = new Intent(getApplicationContext(), ExercisePlanActivity.class);
        startActivity(intent);
    }
    //    public void toExercising(View target) {
//        Intent intent = new Intent(getApplicationContext(), ExercisingActivity.class);
//        startActivity(intent);
//    }
    public void modifyUserInformation(View target) {
        Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
        startActivity(intent);
    }

    public void onStop(){
        super.onStop();
        /*SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("size",ex_list.size());
        for(int i=0;i<ex_list.size();i++) {
            editor.putString(String.valueOf(i), ex_list.get(i));
        }
        editor.commit();*/
    }
}