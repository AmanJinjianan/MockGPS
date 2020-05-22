package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

    //设置激活状态和激活时间
    public static void setFlag(Context con,boolean flag){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("flag",flag);
        editor.putLong("startTime",System.currentTimeMillis());
        editor.commit();//提交修改
    }
    //得到激活状态
    public static boolean getFlag(Context con){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
        sharedPreferences.getString("code","000000000000");
        return sharedPreferences.getBoolean("flag",false);
    }
    //得到激活时间点
    public static long getActiveTime(Context con){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getLong("startTime",0000000000);
    }
    //设置经纬度
    public static void setJingWei(Context con,String jing,String wei){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("jing", jing);
        editor.putString("wei",wei);
        editor.commit();//提交修改
    }
    //得到经纬度
    public static String getJingWei(Context con){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
       return sharedPreferences.getString("jing", "")+"#"+sharedPreferences.getString("wei","");
    }
    //设置活跃时长
    public static void setActiveDay(Context con,int day){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putInt("day", day);
        editor.commit();//提交修改
    }
    //得到活跃时长
    public static int getActiveDay(Context con){
        SharedPreferences sharedPreferences = con.getSharedPreferences("Aman", Context.MODE_PRIVATE); //私有数据
        return sharedPreferences.getInt("day", 0);
    }
}
