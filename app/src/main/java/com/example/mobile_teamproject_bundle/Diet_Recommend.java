package com.example.mobile_teamproject_bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Diet_Recommend extends AppCompatActivity {
   /* String[] dietName = new String[6000000];
    Integer[] ingredients = new Integer[600000];
    String[] cookingInfo = new String[6000000];
    String[] images = new String[6000000];
    String[] frontImage = new String[6000000];  //썸네일, main의 레시피 부분*/


    private Context mContext;
    private String cntntsNo;

    String foodNm;
    String foodImg;
    String foodInfo;
    String body_check = "정상체중";
    User user;

    private ArrayList<String> foodName;
    private ArrayList<String> foodImage;
    private ArrayList<String> materialInfo;
    private ArrayList<String> recipeOrder;
    private ArrayList<String> calorieInfo;
    private ArrayList<String> carbohydratesInfo;
    private ArrayList<String> proteinInfo;
    private ArrayList<String> lipidInfo;
    public ArrayList<Body> body = new ArrayList<Body>();
    public ArrayList<Disease> disease = new ArrayList<Disease>();
    private ArrayList<String> Dingredient = new ArrayList<>();

    FoodInfo_Parser Parser = new FoodInfo_Parser();
    ArrayList<PriceDataStruct> Data = new ArrayList<PriceDataStruct>();
    RecommendDietDetail xmlData = new RecommendDietDetail();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);
        mContext = this;

        user = new User();
        User_File file = new User_File();
        file.User_Read(user);

        Body_File read_body = new Body_File();
        Disease_File read_disease = new Disease_File();
        read_body.Body_Read(body);
        read_disease.Disease_Read(disease);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getAPI();
                Recommed();
            }
    }).start();
    }


    private void getAPI() {

        String apiKey = "20210430IQ6XOLUFMGFHTTQEMNG1A";

        foodName = new ArrayList<>();
        foodImage = new ArrayList<>();
        materialInfo = new ArrayList<>();
        recipeOrder = new ArrayList<>();
        calorieInfo = new ArrayList<>();
        carbohydratesInfo = new ArrayList<>();
        proteinInfo = new ArrayList<>();
        lipidInfo = new ArrayList<>();

        cntntsNo = PreferenceManager.getString(mContext, "cntntsNo");
        String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/recomendDietDtl?apiKey=" + apiKey
                + "&cntntsNo=" + cntntsNo;

        try {
            URL url = new URL(apiUrl);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(inputStream, "UTF-8"));

            int eventType = xpp.getEventType();
            String tag = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                tag = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tag.equals("fdNm")) {
                            xpp.next();
                            foodName.add(xpp.getText());
                        } else if (tag.equals("rtnImageDc")) {
                            xpp.next();
                            foodImage.add(xpp.getText());
                        } else if (tag.equals("matrlInfo")) {
                            xpp.next();
                            materialInfo.add(xpp.getText());
                        } else if (tag.equals("ckngMthInfo")) {
                            xpp.next();
                            recipeOrder.add(xpp.getText());
                        } else if (tag.equals("clriInfo")) {
                            xpp.next();
                            calorieInfo.add(xpp.getText());
                        } else if (tag.equals("crbhInfo")) {
                            xpp.next();
                            carbohydratesInfo.add(xpp.getText());
                        } else if (tag.equals("protInfo")) {
                            xpp.next();
                            proteinInfo.add(xpp.getText());
                        } else if (tag.equals("ntrfsInfo")) {
                            xpp.next();
                            lipidInfo.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        private void Recommed () {
            //DB의 정보와 재료 비교하여 맞는 레시피 반환
            int cursor=0;
            int num = 3;
            int position = (int) (Math.random() * (num - 1));
            /*Log.d("질병", disease.get(0).disease_name);
            Log.d("좋은 식재료", disease.get(0).disease_foods.get(0));
            Log.d("좋은 식재료2", disease.get(0).disease_foods.get(1));*/
            String[] sickness = user.disease.split("_");  //당뇨,암
            for (int i=0;i<disease.size();i++) {
                for (int j =0; j < sickness.length; j++) {
                    if (disease.get(i).disease_name.contains(sickness[j])) {
                        //num = disease.get(i).disease_foods.size();
                        //position = (int) (Math.random() * (num - 1));
                        //Log.d("질병",disease.get(i).disease_name);
                        //Log.d("num", String.valueOf(disease.get(i).disease_foods.size()));
                        for(int k =0 ;k<disease.get(i).disease_foods.size();k++) {
                            //Log.d("add",disease.get(i).disease_foods.get(k));
                            Dingredient.add(disease.get(i).disease_foods.get(k));
                        }
                    }
                }
            }
            Log.d("질병 좋은 재료", String.valueOf(Dingredient));

            float body_fat =  Float.parseFloat(user.body_fat_rate);  //체지방률
            if(body_fat<18){
                body_check = "저체중";
            }else if(23<body_fat&&body_fat<25) body_check = "과체중";
            else if(25<body_fat) body_check = "비만";

            Log.d("체질",body_check);
            
            for (int i=0;i<body.size();i++) {
                    if (body.get(i).body_name.contains(body_check)) {
                        for(int k =0 ;k<3;k++) {
                            //Log.d("body",body.get(i).body_foods.get(k));
                            Dingredient.add(body.get(i).body_foods.get(k));
                        }
                    }
            }
            //Dingredient.add("마늘");
            Log.d("+체질 좋은 재료",String.valueOf(Dingredient));

            num = foodName.size();
            position = (int) (Math.random() * (num - 1));

            Log.d("size", String.valueOf(materialInfo.size()));
            Log.d("position", String.valueOf(position));
            //Log.d("mat", materialInfo.get(position));
            if(materialInfo.size()!=0) {
                for (int i = 0; i < Dingredient.size(); i++) {
                    if (materialInfo.get(position).contains(Dingredient.get(i))) {
                        //Log.d("Find!!!!","Yeah");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("foodName", foodName.get(position));
                        intent.putExtra("foodImage", foodImage.get(position));
                        intent.putExtra("materialInfo", materialInfo.get(position));
                        intent.putExtra("recipeOrder", recipeOrder.get(position));
                        intent.putExtra("calorieInfo", calorieInfo.get(position));
                        intent.putExtra("carbohydratesInfo", carbohydratesInfo.get(position));
                        intent.putExtra("proteinInfo", proteinInfo.get(position));
                        intent.putExtra("lipidInfo", lipidInfo.get(position));
                        startActivity(intent);
                        return;
                    }
                }
            }
            Intent intent = new Intent(getApplicationContext(), Select_Diet.class);
            startActivity(intent);
        }
    }
