package com.swufe.sugar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class MovieDetial extends AppCompatActivity implements Runnable{
    String TAG = "MovieDetial";
    String movieurl;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detial);
        //通过map获得对应view控件中的内容
        String moviename = getIntent().getStringExtra("MovieName");
        String moviescore = getIntent().getStringExtra("MovieScore");
        movieurl = getIntent().getStringExtra("MovieUrl");

        Log.i(TAG, "onCreate: MovieName = " + moviename);
        Log.i(TAG, "onCreate: MovieScore=" + moviescore);
        Log.i(TAG, "onCreate: movieurl=" + movieurl);
        ((TextView)findViewById(R.id.detial_name)).setText(moviename);
        ((TextView)findViewById(R.id.detial_url)).setText(movieurl);





        //开启子线程
        Thread t = new Thread(this);   //注意有this
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i("dsad","dd");
                if(msg.what==7){
                    //获得map中的数据
                    HashMap<String, String> detial = (HashMap<String, String>)msg.obj;
                    String dre = detial.get("dre");
                    String con = detial.get("con");
                    String country = detial.get("country");
                    String time = detial.get("time");
                    String lan = detial.get("lan");
                    String year = detial.get("year");

                    //显示文本内容
                    ((TextView)findViewById(R.id.detial_con)).setText(con);
                    ((TextView)findViewById(R.id.detial_dre)).setText(dre);
                    ((TextView)findViewById(R.id.detial_country)).setText(country);
                    ((TextView)findViewById(R.id.detial_time)).setText(time);
                    ((TextView)findViewById(R.id.detial_lan)).setText(lan);
                    ((TextView)findViewById(R.id.detial_year)).setText(year);
                }
            }
        };


    }

    @Override
    public void run() {
        Message msg = handler.obtainMessage();

        //获得网络数据
        Document doc = null;
        String url = movieurl;
        HashMap<String, String> map = null;
        try {
            map = new HashMap<String, String>();
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            String con = doc.getElementsByClass("details-content vod-details-content").text();
            Log.i(TAG, "run: " + con);
            String dre = doc.getElementsByClass("col-md-12 text").text();
            String country = doc.getElementsByClass("col-md-6 col-sm-6 col-xs-4 text hidden-xs").text();
            String time = doc.getElementsByClass("col-md-6 col-sm-6 col-xs-12  text").text();
            String lan = doc.getElementsByClass("col-md-6 col-sm-6 col-xs-6 text hidden-xs").text();
            String year = doc.getElementsByClass("col-md-6 col-sm-6 col-xs-6 text hidden-xs").text();
            Log.i(TAG, "run: " + dre);
            Log.i(TAG, "run: " + country);
            Log.i(TAG, "run: " + time);
            Log.i(TAG, "run: " + lan);
            Log.i(TAG, "run: " + year);

            //使用map保存获得的数据
            map.put("dre", dre);
            map.put("con", con);
            map.put("country", country);
            map.put("time", time);
            map.put("lan", lan);
            map.put("year", year);
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg.obj = map;
        msg.what = 7;
        handler.sendMessage(msg);

    }
}