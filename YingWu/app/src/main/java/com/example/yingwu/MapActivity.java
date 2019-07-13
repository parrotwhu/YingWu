package com.example.yingwu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentPoi;

import java.util.List;
public class MapActivity extends AppCompatActivity implements TencentLocation ,TencentLocationListener {

    public static String address;

    //声明各个控件
    private WebView webView;
    LinearLayout searchLayout;
    ImageView searchBtn,user_map;
    TextInputEditText searchText;
    public double latitude,longitude;

    //注册位置监听器
    public void init(TencentLocationRequest request){
        Context context = this;
        TencentLocationListener listener =this;
        TencentLocationManager locationManager=TencentLocationManager.getInstance(context);
        int error = locationManager.requestLocationUpdates(request,listener);
        if(error==0){
            Log.v("this","注册位置监听器成功");
        }else {
            //error返回为1、2、3分别代表不同类型的错误，腾讯地图官方文档有详细介绍
            Log.v(String.valueOf(error),"注册位置监听器失败");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //初始化各个控件
        webView=findViewById(R.id.webView);
        searchLayout=findViewById(R.id.search);
        searchBtn=findViewById(R.id.searchBtn);
        user_map=findViewById(R.id.user_map);
        user_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginActivity.isLogin){
                    startActivity(new Intent(MapActivity.this,MainActivity.class));
                }
                else
                    startActivity(new Intent(MapActivity.this,LoginActivity.class));
            }
        });
        searchText=findViewById(R.id.searchText);
        //判断安卓版本，如果在6.0以上则动态申请权限，注意首先得在Manefest.xml文件中配置权限
        if(Build.VERSION.SDK_INT>=23){
            String[] permissions={
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            if(checkSelfPermission(permissions[0])!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(permissions,0);
            }
        }
        //创建请求，官方文档给的代码，我也不太懂
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(20000).setRequestLevel(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION).setAllowCache(true);
        init(request);

        //调用函数，声明在下方
        onLocationChanged(this,0,null);
        WebSettings webSettings = webView.getSettings();
        //不加这行会出现网页无法获取的错误
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        //缓存
        webSettings.setDomStorageEnabled(true);

        // 如果加载网页： webView.loadUrl("http://www.baidu.com/");
        //加载本地文件，使用随机数保证了每次显示的网页都不一样
        webView.loadUrl("file:///android_asset/map.html");

        searchBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String  keyword = searchText.getText().toString();
                                             if(keyword.isEmpty()){
                                                 Toast.makeText(MapActivity.this, "请先输入搜索信息", Toast.LENGTH_SHORT).show();
                                             }
                                             else{
                                                 webView.loadUrl("javascript:searchKeyword('"+keyword+"')");
                                                 Log.v(keyword,"输入信息");
                                             }
                                         }
                                     }

        );
    }

    //每次onLocationChanged函数执行都会调用这个函数并且把经纬度传过来
    public void setPosition(double lng, double lat){
        //把获取到的经纬度传给javascript中的setPosition函数。html文件放在assets文件夹中
        webView.loadUrl("javascript:setPosition("+lat+","+lng+")");
        webView.loadUrl("javascript:isPositionSite("+lat+","+lng+")");
        webView.loadUrl("javascript:walkLine("+lat+","+lng+")");
    }




    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public double getLatitude() {
        return 0;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public double getAltitude() {
        return 0;
    }

    @Override
    public float getAccuracy() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getNation() {
        return null;
    }

    @Override
    public String getProvince() {
        return null;
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getDistrict() {
        return null;
    }

    @Override
    public String getTown() {
        return null;
    }

    @Override
    public String getVillage() {
        return null;
    }

    @Override
    public String getStreet() {
        return null;
    }

    @Override
    public String getStreetNo() {
        return null;
    }

    @Override
    public Integer getAreaStat() {
        return null;
    }

    @Override
    public List<TencentPoi> getPoiList() {
        return null;
    }

    @Override
    public float getBearing() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public long getElapsedRealtime() {
        return 0;
    }

    @Override
    public int getGPSRssi() {
        return 0;
    }

    @Override
    public String getIndoorBuildingId() {
        return null;
    }

    @Override
    public String getIndoorBuildingFloor() {
        return null;
    }

    @Override
    public int getIndoorLocationType() {
        return 0;
    }

    @Override
    public double getDirection() {
        return 0;
    }

    @Override
    public String getCityCode() {
        return null;
    }

    @Override
    public String getCityPhoneCode() {
        return null;
    }

    @Override
    public int getCoordinateType() {
        return 0;
    }

    @Override
    public int isMockGps() {
        return 0;
    }

    @Override
    public Bundle getExtra() {
        return null;
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (TencentLocation.ERROR_OK==error){
            Log.v("this","定位成功");
            if(location!=null){
                longitude=location.getLongitude();
                latitude=location.getLatitude();
                setPosition(longitude,latitude);
                Log.v(String.valueOf(location.getLongitude()),"定位成功，经度");
                Log.v(String.valueOf(location.getLatitude()),"定位成功,纬度");
                Log.v(location.getAddress(),"定位成功，地址");
                address=location.getAddress();
            }
        }else {
            Log.v("this","定位失败");
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                        Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                super.onBackPressed();
//            }
//
//        }
//    }
}
