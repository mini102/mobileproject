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
    String[] dietName = new String[6000000];
    Integer[] ingredients = new Integer[600000];
    String[] cookingInfo = new String[6000000];
    String[] images = new String[6000000];
    String[] frontImage = new String[6000000];  //썸네일, main의 레시피 부분

    private Context mContext;
    private String cntntsNo;

    String foodNm;
    String foodImg;
    String foodInfo;

    private ArrayList<String> foodName;
    private ArrayList<String> foodImage;
    private ArrayList<String> materialInfo;
    private ArrayList<String> recipeOrder;
    private ArrayList<String> calorieInfo;
    private ArrayList<String> carbohydratesInfo;
    private ArrayList<String> proteinInfo;
    private ArrayList<String> lipidInfo;

    FoodInfo_Parser Parser = new FoodInfo_Parser();
    ArrayList<PriceDataStruct> Data = new ArrayList<PriceDataStruct>();
    RecommendDietDetail xmlData = new RecommendDietDetail();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);
        mContext = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                getAPI();
                /*for (int i = 0; i < foodName.size(); i++) {
                    Log.d("음식이름:", foodName.get(i));
                    Log.d("재료:", materialInfo.get(i));
                }*/
                Recommed();
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("Name",foodName);
                //intent.putExtra("Image",foodImage);
                //intent.putExtra("Info",recipeOrder);
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
            for (int i = 0; i < foodName.size(); i++) {
                Log.d("fdNm", foodName.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        private void Recommed () {
            //DB의 정보와 재료 비교하여 맞는 레시피 반환, 일단은 random
            int num = foodName.size();
            int position = (int) (Math.random() * (num - 1)); //0~num-1
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
        }
    }
