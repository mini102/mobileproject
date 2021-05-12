package com.example.mobile_teamproject_bundle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class Select_Diet extends AppCompatActivity {

    private Context mContext;
    private NetworkConnectionStateMonitor networkConnectionStateMonitor;

    private ArrayList<String> dietSeCode = new ArrayList<>();;

    public Select_Diet(){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting);
        init();
        if (networkConnectionStateMonitor != null) {
            getMainCategoryTitleResponse();
        }
    }

    private void init() {
        mContext = this;

        if (networkConnectionStateMonitor == null) {
            networkConnectionStateMonitor = new NetworkConnectionStateMonitor(mContext);
            networkConnectionStateMonitor.register();


            //TODO: Add progress dialog (or bar) after first release
            if (!networkConnectionStateMonitor.isConnected()) {

                final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setCancelable(false)
                        .setMessage(R.string.dialog_network_alert_message)
                        .setPositiveButton("확인", null)
                        .create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (networkConnectionStateMonitor.isConnected()) {
                                    alertDialog.dismiss();
                                    Toast.makeText(mContext, R.string.network_connected_message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, R.string.network_unconnected_message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                alertDialog.show();
            }
        }

    }

    private void getMainCategoryTitleResponse() {
        // Get Api response
        new Thread(new Runnable() {
            @Override
            public void run() {
                getMainCategoryResponse();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i< dietSeCode.size(); i++) {
                            // 각 List의 값들을 MainCategory 객체에 set 해줍니다
                            MainCategory mainCategoryData = new MainCategory();
                            mainCategoryData.setDietSeCode(dietSeCode.get(i));
                        }*/
            //}
        //}).start();

                for (int i = 0; i < dietSeCode.size(); i++) {
                    Log.d("diet", dietSeCode.get(i));
                }
                onClickCategory();
            }
        }).start();
    }

            private void getMainCategoryResponse() {

                dietSeCode.clear();

                String apiKey = "20210430IQ6XOLUFMGFHTTQEMNG1A";

                String apiUrl = "http://api.nongsaro.go.kr/service/recomendDiet/mainCategoryList?apiKey=" + apiKey;

        /*String text = null;

        try {
            text = URLEncoder.encode(apiUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }*/

                try {
                    URL url = new URL(apiUrl);
                    URLConnection urlcon = url.openConnection();
                    Log.d("enterrrrrrrr", "hereeeeeeeeeeeeeee");
                    InputStream inputStream = urlcon.getInputStream();
                    Log.d("sucescc", "hereeeeeeeeeeeeeee");

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new InputStreamReader(inputStream, "UTF-8"));

                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                if (xpp.getName().equals("item")) { // fist research result

                                } else if (xpp.getName().equals("dietSeCode")) {
                                    xpp.next();
                                    dietSeCode.add(xpp.getText());
                                    //Log.d("xpp",xpp.getText());
                                }
                                break;
                        }
                        eventType = xpp.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void onClickCategory() {

                int rand = (int) (Math.random() * 4);
                Log.d("rand", String.valueOf(rand));
                Intent intent;

          switch(rand) {
            case 0:
                Log.d("rand=0",dietSeCode.get(0));
            PreferenceManager.setString(mContext, "dietSeCode", dietSeCode.get(0));
            PreferenceManager.setString(mContext, "mainCategoryName", getString(R.string.study_diet_main_category_name));
            Log.d("열공식단","enterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            intent = new Intent(getApplicationContext(), RecommendDietListActivity.class);
            startActivity(intent);
            break;
            case 1:
                Log.d("rand=1",dietSeCode.get(1));
                PreferenceManager.setString(mContext, "dietSeCode", dietSeCode.get(1));
                PreferenceManager.setString(mContext, "mainCategoryName", getString(R.string.healthy_diet_main_category_name));
                Log.d("건강식단","enterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                intent = new Intent(getApplicationContext(), RecommendDietListActivity.class);
                startActivity(intent);
                break;
            case 2:
                Log.d("rand=2",dietSeCode.get(2));
                PreferenceManager.setString(mContext, "dietSeCode", dietSeCode.get(2));
                PreferenceManager.setString(mContext, "mainCategoryName", getString(R.string.home_meal_main_category_name));
                Log.d("가정식식단","enterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                intent = new Intent(getApplicationContext(), RecommendDietListActivity.class);
                startActivity(intent);
                break;
            case 3:
                Log.d("rand=3",dietSeCode.get(3));
                PreferenceManager.setString(mContext, "dietSeCode", dietSeCode.get(3));
                PreferenceManager.setString(mContext, "mainCategoryName", getString(R.string.event_diet_main_category_name));
                Log.d("이벤트식단","enterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                intent = new Intent(getApplicationContext(), RecommendDietListActivity.class);
                startActivity(intent);
                break;
            case 4:
                Log.d("rand=4",dietSeCode.get(4));
                PreferenceManager.setString(mContext, "dietSeCode", dietSeCode.get(4));
                PreferenceManager.setString(mContext, "mainCategoryName", getString(R.string.refresh_diet_main_category_name));
                Log.d("기분up식단","enterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
                intent = new Intent(getApplicationContext(), RecommendDietListActivity.class);
                startActivity(intent);
                break;
              }
            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                networkConnectionStateMonitor.unRegister();
            }
        }
