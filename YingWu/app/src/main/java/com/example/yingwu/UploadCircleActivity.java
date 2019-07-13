package com.example.yingwu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yingwu.beans.FriendCircleBean;
import com.example.yingwu.beans.OtherInfoBean;
import com.example.yingwu.utils.IdGenerator;
import com.example.yingwu.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UploadCircleActivity extends AppCompatActivity {

    private ImageView upload_image_back;
    private EditText circle_content;
    private Button btn_upload;
    private FriendCircleBean friendCircleBean=new FriendCircleBean();
    public static OtherInfoBean otherInfoBean=new OtherInfoBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_circle);

        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        upload_image_back=findViewById(R.id.upload_img_back);
        btn_upload=findViewById(R.id.btn_upload);
        circle_content=findViewById(R.id.circle_content);
        upload_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadCircleActivity.this,MainActivity.class));
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content=circle_content.getText().toString().trim();
                if(content.isEmpty()){
                    Toast.makeText(UploadCircleActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    otherInfoBean.setSource(MapActivity.address);
                    Calendar cal=Calendar.getInstance();
//                    String time=""+cal.get(Calendar.YEAR)+"//"+cal.get(Calendar.MONTH)+"//"+cal.get(Calendar.DATE)+"-"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
                    Intent intent=new Intent(UploadCircleActivity.this,MainActivity.class);
//                    intent.putExtra("content",content);
//                    intent.putExtra("time",time);
                    otherInfoBean.setTime(""+cal.get(Calendar.YEAR)+"//"+cal.get(Calendar.MONTH)+"//"+cal.get(Calendar.DATE)+"-"+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
                    friendCircleBean.setContent(content);
                    friendCircleBean.setUserBean(LoginActivity.userBean);
                    friendCircleBean.setCircleId(IdGenerator.getId());
                    friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);//0
                    friendCircleBean.setOtherInfoBean(otherInfoBean);
//                    List<FriendCircleBean> friendCircleBeans=MainActivity.mFriendCircleAdapter.getmFriendCircleBeans();

//                    MainActivity.mFriendCircleAdapter.addFriendCircleBeans(friendCircleBeans);
//                    //后台数据库
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String path = "http://10.0.2.2:8080/AndroidTest2/mustUploadCircle?username="
//                                    + friendCircleBean.getUserBean().getUserName()
//                                    +"&circleId=" +friendCircleBean.getCircleId()
//                                    +"&userAvatarUrl="+friendCircleBean.getUserBean().getUserAvatarUrl()
//                                    +"&content="+friendCircleBean.getContent()
//                                    +"&time="+friendCircleBean.getOtherInfoBean().getTime()
//                                    +"&location="+friendCircleBean.getOtherInfoBean().getSource();
//                            try {
//                                try {
//                                    URL url = new URL(path); //新建url并实例化
//                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                                    connection.setRequestMethod("GET");//获取服务器数据
//                                    connection.setReadTimeout(8000);//设置读取超时的毫秒数
//                                    connection.setConnectTimeout(8000);//设置连接超时的毫秒数
//                                    InputStream in = connection.getInputStream();
//                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                                    String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
//                                    Log.d("UploadCircleActivity", "run: " + result);
//                                    if (result.equals("upload successfully!")) {
//                                        Log.d("UploadCircleActivity", "run2: " + result);
//                                        Looper.prepare();
//                                        Log.d("UploadCircleActivity", "run3: " + result);
//                                        Toast.makeText(UploadCircleActivity.this, "You upload successfully!", Toast.LENGTH_SHORT).show();
//                                        Log.d("UploadCircleActivity", "run4: " + result);
//                                        Looper.loop();
//                                    }
//                                } catch (MalformedURLException e) {
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
                    startActivity(intent);
                }
            }
        });
    }
}
