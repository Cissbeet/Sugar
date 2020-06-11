package com.swufe.sugar;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Movies extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    SimpleAdapter simpleAdapter;
    String TAG = "Movies";


    /**
     *   布局文件和framagement 关联
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_movies,container,false);
        listView =view.findViewById(R.id.movies);

        simpleAdapter = new SimpleAdapter(getActivity(),getData(),R.layout.activity_list_item,new String[]{"title","image"},new int[]{R.id.myMenu_name,R.id.myMenu_image});
        listView.setAdapter(simpleAdapter);

//        简单adapter
//        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
//        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    private List<Map<String,Object>> getData() {
        String [] titles={"热门电影","最新电影","豆瓣高分","冷门佳片"};
        int [] images={R.mipmap.kele,R.mipmap.yanjing,R.mipmap.shupian,
                R.mipmap.popcorn};
        List<Map<String,Object>> list= new ArrayList<>();
        for(int i=0;i<4;i++){
            Map  map = new HashMap();
            map.put("title",titles[i]);
            map.put("image",images[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: ");
        String text = listView.getAdapter().getItem(position).toString();
        Log.i(TAG, "onItemClick: "+position+text);
        if(position==0){
            Log.i(TAG, "onItemClick: "+"HotMovies");
            Intent hotmovies = new Intent(getActivity(), HotMovies.class);
            startActivity(hotmovies);
        }else if(position==1){
            Log.i(TAG, "onItemClick: "+"NewMovies");
            Intent newmovies = new Intent(getActivity(), NewMovies.class);
            startActivity(newmovies);
        }else if(position==2){
            Log.i(TAG, "onItemClick: "+"HightMovies");
            Intent hightmovies = new Intent(getActivity(), HightMovies.class);
            startActivity(hightmovies);
        }else{
            Log.i(TAG, "onItemClick: "+"ColdMovies");
            Intent coldmovies = new Intent(getActivity(), ColdMovies.class);
            startActivity(coldmovies);
        }

    }
}
