package com.example.mockgps;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.example.utils.LocationUtils;
import com.example.utils.SharePreferenceUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

public class ActivitionActivity extends AppCompatActivity {

    public static BaiduMap mBaiduMap = null;
    public static LatLng currentPt = new LatLng(30.547743718042415, 104.07018449827267);
    private TextView tv,tvJing,tvWei;
    Location location2;
    String activeDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activition);
        tvJing = findViewById(R.id.tv_jing);
        tvWei = findViewById(R.id.tv_wei);
        location2 = LocationUtils.getInstance(ActivitionActivity.this).showLocation();
        if (location2!=null){
            String address = "纬度："+location2.getLatitude()+"经度："+location2.getLongitude();
            tvJing.setText("经度："+location2.getLongitude());
            tvWei.setText("纬度："+location2.getLatitude());
            //SharePreferenceUtils.setJingWei(ActivitionActivity.this,"114","45");
            Log.d("FLY.onCreate",address);
        }else {
            setPosi("0","0");
            Toast.makeText(ActivitionActivity.this,"不能获取坐标位置!",Toast.LENGTH_SHORT).show();
            this.finish();
        }
        tv = findViewById(R.id.et_code);
        long data = System.currentTimeMillis();

        findViewById(R.id.btn_recode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(null != tv.getText().toString() && tv.getText().toString().trim().length() != 0 && tv.getText().toString().length()>12){
                    try {
                        String codeString = tv.getText().toString().trim();
                        activeDay = codeString.substring(1,4);
                        int dd = Integer.valueOf(activeDay);
                        getRealDay(activeDay);
                        long gotValue = Long.parseLong(tv.getText().toString().trim().substring(4,codeString.length()));
                        long cha = Math.abs(System.currentTimeMillis()-gotValue);
                        if(cha < 120000){
                            Toast.makeText(ActivitionActivity.this,"激活成功！"+cha,Toast.LENGTH_SHORT).show();
                            SharePreferenceUtils.setFlag(ActivitionActivity.this,true);
                            SharePreferenceUtils.setActiveDay(ActivitionActivity.this,getRealDay(activeDay));
                            if(location2 != null){
                                SharePreferenceUtils.setJingWei(ActivitionActivity.this,String.valueOf(location2.getLongitude()),String.valueOf(location2.getLatitude()));
                            }
                            ActivitionActivity.this.finish();
                        }else {
                            Toast.makeText(ActivitionActivity.this,"激活码无效！请联系客户经理！"+cha,Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(ActivitionActivity.this,"异常！",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(ActivitionActivity.this,"激活码异常！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setPosi(String lng,String lat){
        try {
            double dialog_lng_double = Double.valueOf(lng);
            double dialog_lat_double = Double.valueOf(lat);
//                    DisplayToast("经度: " + dialog_lng_str + ", 纬度: " + dialog_lat_str);
            if (dialog_lng_double > 180.0 || dialog_lng_double < -180.0 || dialog_lat_double > 90.0 || dialog_lat_double < -90.0) {
                DisplayToast("经纬度超出限制!\n-180.0<经度<180.0\n-90.0<纬度<90.0");
            } else {
                currentPt = new LatLng(dialog_lat_double, dialog_lng_double);
                MapStatusUpdate mapstatusupdate = MapStatusUpdateFactory.newLatLng(currentPt);
                //对地图的中心点进行更新
                //mBaiduMap.setMapStatus(mapstatusupdate);
                //updateMapState();
                //transformCoordinate(lng,lat);
            }
        } catch (Exception e) {
            DisplayToast("获取经纬度出错,请检查输入是否正确");
            e.printStackTrace();
        }
    }
    public void DisplayToast(String str) {
        Toast toast = Toast.makeText(ActivitionActivity.this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 220);
        toast.show();
    }
    private int getRealDay(String day){
        char[] datArr = day.toCharArray();
        char[] returnData = new char[datArr.length];
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<datArr.length;i++){
            returnData[returnData.length-1-i] = datArr[i];
        }
        for (char a : returnData){
            sb.append(a);
        }
        return Integer.valueOf(sb.toString());
    }
}
