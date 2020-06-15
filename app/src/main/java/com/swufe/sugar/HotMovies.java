package com.swufe.sugar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotMovies extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ArrayAdapter adapter;
    String TAG = "HotMovies";
    Handler handler;
    List<HashMap<String,String>> ListItem;
    private SimpleAdapter listItemAdapter;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    private ArrayList<HashMap<String,String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hot_movies);



        //开启子线程
        Thread t = new Thread(this);   //注意有this
        t.start();

        //获得子线程数据，并且安排布局
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i("dsad","dd");
                if(msg.what==7){
                    Log.i("dsad","dd");
                    listItem = (ArrayList<HashMap<String,String>>) msg.obj;
                    int i=listItem.size();
                    Log.i("dsad","length:"+i);
                    listItemAdapter = new SimpleAdapter(HotMovies.this, listItem ,R.layout.activity_show_list,//准备布局
                            new String[] {"MovieName","MovieScore","MovieUrl"},//数据安排在布局当中
                            new  int[] {R.id.showlist_name,R.id.showlist_score,R.id.showlist_url});
                    setListAdapter(listItemAdapter);
                }
            }
        };
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
        //创建listview用于储存数据

    }

    //开启子线程
    @Override
    public void run() {
        Log.i("run", "running......");
//        获取时间对比
        Message msg = handler.obtainMessage();

        List<MovieItem> MovieList = new ArrayList<>();;
       //获取网络数据
        Document doc = null;
       //map用于显示数据，传回msg
       HashMap<String, String> map = null;
       int i=0;
       //movieList用于把数据保存在DB中
       List<MovieItem> movieList = new ArrayList<MovieItem>();
        try {
            listItem = new ArrayList<HashMap<String, String>>();
            String url = "http://www.xigua16.com/dongzuopian/";
            doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Element div = doc.getElementById("content");
            //            Log.i(TAG, "run: " + div);
            Elements movies = div.getElementsByClass("video-pic loading");
            Log.i(TAG, "run: "+movies);
            for (Element movie : movies) {
                map = new HashMap<String, String>();
                String name = movie.attr("title");
                Log.i(TAG, "run: " + name);
                String score = movie.text();
                Log.i(TAG, "run: " + score);
                String movieurl = "http://www.xigua16.com/" + movie.attr("href");
                Log.i(TAG, "run: "+movieurl);
                map.put("MovieName", name);//压入电影名字
                map.put("MovieScore", score);//分数
                map.put("MovieUrl",movieurl);//网址
                listItem.add(map);
                MovieItem MovieItem = new MovieItem(name, score);
                MovieList.add(MovieItem);
            }
            String aa=listItem.get(2).get("MovieName");
            Log.i(TAG, "firstMovie :"+aa);


        } catch (IOException e) {
            e.printStackTrace();
        }

    //将数据传回主线程
//        map.put("num",i+"");
    msg.obj = listItem;
    msg.what = 7;
    handler.sendMessage(msg);
    }







        //点击执行的操作
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String TAG = "onItemClick";
//        Log.i(TAG, "onItemClick: parent=" + parent);
//        Log.i(TAG, "onItemClick: view=" + view);
//        Log.i(TAG, "onItemClick: position=" + position);
//        Log.i(TAG, "onItemClick: id=" + id);

        //从ListView中获取选中数据
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String MovieName = map.get("MovieName");
        String MovieSocre = map.get("MovieSocre");
        String MovieUrl = map.get("MovieUrl");
        Log.i(TAG, "onItemClick: MovieName=" + MovieName);
        Log.i(TAG, "onItemClick: MovieSocre=" + MovieSocre);
        Log.i(TAG, "onItemClick: MovieUrl=" + MovieUrl);
        //打开新的页面，传入参数
        Intent moviedetial = new Intent(this,MovieDetial.class);
        moviedetial.putExtra("MovieName",MovieName);
        moviedetial.putExtra("MovieSocre",MovieSocre);
        moviedetial.putExtra("MovieUrl",MovieUrl);
        startActivity(moviedetial);
    }

    //长按执行的操作
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.i(TAG, "onItemLongClick: ");


//        ListItem.remove(position);
//        listItemAdapter.notifyDataSetChanged();
        //创建提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("是否删除信息：").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListItem.remove(position);
                listItemAdapter.notifyDataSetChanged();
            }
        })
                .setNegativeButton("否",null);

        return false;//选择false则是可以执行点击操作，选择true则是只执行长按操做
    }
}