package com.swufe.sugar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    //定义一个字符串数组，用于显示页面名称
    private String[] title = new String[]{"Movies","Teleplay"};

    public MyPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    //确定每一个fragment出现的位置，使用position连接
    public Fragment getItem(int position) {
        if(position==0){
            return new Movies();
        }else{
            return new Teleplay();
        }
    }

    @Override
    //共有三个页面可以滑动，返回的数量即为滑动的数量
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //根据字符串数组返回每一个页面的名称
        return title[position];
    }
}

