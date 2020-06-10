package com.swufe.sugar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onclick(View btn){
        //跳转页面，使用intent对象
        Log.i(TAG, "onclick: ");
        Intent pagechange = new Intent(this, PageChange.class);
        startActivity(pagechange);

    }
}
