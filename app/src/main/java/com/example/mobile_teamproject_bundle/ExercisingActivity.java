package com.example.mobile_teamproject_bundle;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExercisingActivity extends AppCompatActivity {
    String key = "하체운동";
    String[] exercise = {"하체운동","상체운동","허리운동","요가"};
    float rating =0;
    //boolean watch = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_video);

        watching();
        rating++;

        exerciseListAdapter Adapter  = new
                exerciseListAdapter(this, exercise);

        ListView listview = (ListView) findViewById(R.id.ex_list);
        listview.setAdapter((ListAdapter) Adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                key = exercise[position];
                watching();
                rating++;
                RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
                ratingBar.setRating(rating);
            }
        });
    }

    public void watching(){
        WebView mWebView = (WebView) findViewById(R.id.webView2);

        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        WebSettings mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        mWebView.loadUrl("https://www.youtube.com/results?search_query="+ key);
    }
}
