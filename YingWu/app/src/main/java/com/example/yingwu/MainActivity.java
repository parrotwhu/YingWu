package com.example.yingwu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yingwu.adapters.FriendCircleAdapter;
import com.example.yingwu.beans.CommentBean;
import com.example.yingwu.beans.FriendCircleBean;
import com.example.yingwu.beans.OtherInfoBean;
import com.example.yingwu.beans.PraiseBean;
import com.example.yingwu.beans.UserBean;
import com.example.yingwu.interfaces.OnPraiseOrCommentClickListener;
import com.example.yingwu.others.DataCenter;
import com.example.yingwu.others.FriendsCircleAdapterDivideLine;
import com.example.yingwu.others.GlideSimpleTarget;
import com.example.yingwu.utils.SpanUtils;
import com.example.yingwu.utils.Utils;
import com.example.yingwu.widgets.EmojiPanelView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnPraiseOrCommentClickListener, ImageWatcher.OnPictureLongPressListener, ImageWatcher.Loader {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Disposable mDisposable;
    public static FriendCircleAdapter mFriendCircleAdapter;
    private ImageWatcher mImageWatcher;
    private EmojiPanelView mEmojiPanelView;
    private TextView circle_username;
    private TextView send_comment;
    private EditText comment_content;
    public static RecyclerView recyclerView;
    private ImageView imagePortrait;
    private List<CommentBean> commentBeans=new ArrayList<>();
    private FriendCircleBean friendCircleBean;

    //换头像
    private static final int TAKE_PHOTO=1;
    private static final int SELECT_PHOTO=2;
    public void select_photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            openAlbum();
        }
    }
    /**
     * 打开相册的方法
     * */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    /**
     *4.4以下系统处理图片的方法
     * */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
    /**
     * 4.4及以上系统处理图片的方法
     * */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的uri，则使用普通方式处理
                imagePath = getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
            //根据图片路径显示图片
            displayImage(imagePath);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO :
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
//                    try {
//                        Toast.make
////                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
////                        imagePortrait.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
            case SELECT_PHOTO :
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImgeOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    /**
     * 根据图片路径显示图片的方法
     * */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imagePortrait.setImageBitmap(bitmap);
        }else {
            Toast.makeText(MainActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过uri和selection来获取真实的图片路径
     * */
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }else {
                    Toast.makeText(MainActivity.this,"failed to get image",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
//===========================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send_comment=findViewById(R.id.send_comment);
        comment_content=findViewById(R.id.edit_text);

        imagePortrait=findViewById(R.id.image_portrait);
        imagePortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_photo();
            }
        });
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent=comment_content.getText().toString().trim();
                CommentBean commentBean=new CommentBean();
                commentBean.setCommentUserName(LoginActivity.userBean.getUserName());
                commentBean.setCommentContent(commentContent);
                commentBeans.add(commentBean);
                friendCircleBean.setCommentBeans(commentBeans);
                recyclerView.setAdapter(mFriendCircleAdapter);

            }
        });
        mEmojiPanelView = findViewById(R.id.emoji_panel_view);
        mEmojiPanelView.initEmojiPanel(DataCenter.emojiDataSources);
        mSwipeRefreshLayout = findViewById(R.id.swpie_refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        circle_username=findViewById(R.id.circle_username);
        circle_username.setText(LoginActivity.userBean.getUserName());
        findViewById(R.id.img_back).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MapActivity.class)));

//        findViewById(R.id.image_camera).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,UploadCircleActivity.class));
////                //=====================================================
////                String content=getIntent().getStringExtra("content");
////                FriendCircleBean friendCircleBean=new FriendCircleBean();
////                friendCircleBean.setUserBean(LoginActivity.userBean);
////                OtherInfoBean otherInfoBean=new OtherInfoBean();
////                otherInfoBean.setSource(MapActivity.address);
////                otherInfoBean.setTime(getIntent().getStringExtra("time"));
////                friendCircleBean.setOtherInfoBean(otherInfoBean);
////                List<FriendCircleBean> friendCircleBeans=new ArrayList<>();
////                friendCircleBeans.add(friendCircleBean);
////                mFriendCircleAdapter.setFriendCircleBeans(friendCircleBeans);
//////                List<FriendCircleBean> friendCircleBeans=DataCenter.makeFriendCircleBeans(MainActivity.this);
//////                        friendCircleBeans.add(friendCircleBean);
////
////                //======================================================
//            }
//        });




        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(MainActivity.this).resumeRequests();
                } else {
                    Glide.with(MainActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mImageWatcher = findViewById(R.id.image_watcher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new FriendsCircleAdapterDivideLine());
        mFriendCircleAdapter = new FriendCircleAdapter(this, recyclerView, mImageWatcher);
        recyclerView.setAdapter(mFriendCircleAdapter);
        mImageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(this));
        mImageWatcher.setErrorImageRes(R.mipmap.error_picture);
        mImageWatcher.setOnPictureLongPressListener(this);
        mImageWatcher.setLoader(this);
        Utils.showSwipeRefreshLayout(mSwipeRefreshLayout, this::asyncMakeData);
    }


    private void asyncMakeData() {
        mDisposable = Single.create((SingleOnSubscribe<List<FriendCircleBean>>) emitter ->
                emitter.onSuccess(DataCenter.makeFriendCircleBeans(this)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((friendCircleBeans, throwable) -> {
                    Utils.hideSwipeRefreshLayout(mSwipeRefreshLayout);
                    if (friendCircleBeans != null && throwable == null) {
                        mFriendCircleAdapter.setFriendCircleBeans(friendCircleBeans);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onRefresh() {
        asyncMakeData();

        Toast.makeText(this, "You refreshed!", Toast.LENGTH_SHORT).show();
    }
//    List<FriendCircleBean> newfriendCircleBeans=new ArrayList<>();
//        newfriendCircleBeans.add((FriendCircleBean)UploadCircleActivity.friendCircleBean);
//        mFriendCircleAdapter.addFriendCircleBeans(newfriendCircleBeans);

    @Override
    public void onPraiseClick(int position) {
//        String circleId= mFriendCircleAdapter.getmFriendCircleBeans().get(position).getCircleId();
        Toast.makeText(this, "You Click Praise!", Toast.LENGTH_SHORT).show();
        PraiseBean praiseBean=new PraiseBean();
        praiseBean.setPraiseUserName(LoginActivity.userBean.getUserName());
        List<PraiseBean> praiseBeans=mFriendCircleAdapter.getmFriendCircleBeans().get(position).getPraiseBeans();
        praiseBeans.add(praiseBean);
        mFriendCircleAdapter.getmFriendCircleBeans().get(position).setPraiseSpan(SpanUtils.makePraiseSpan(this,praiseBeans));
        recyclerView.setAdapter(mFriendCircleAdapter);
    }

//    public static String commentCircleId;
    //评论按钮点击事件
    @Override
    public void onCommentClick(int position) {
//        Toast.makeText(this, "you click comment", Toast.LENGTH_SHORT).show();
        friendCircleBean=mFriendCircleAdapter.getmFriendCircleBeans().get(position);
        commentBeans= mFriendCircleAdapter.getmFriendCircleBeans().get(position).getCommentBeans();
        Toast.makeText(this, "You Click Comment!", Toast.LENGTH_SHORT).show();
        mEmojiPanelView.showEmojiPanel();

    }

    @Override
    public void onBackPressed() {
        if (!mImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {

    }

    @Override
    public void load(Context context, String url, ImageWatcher.LoadCallback lc) {
        Glide.with(context).asBitmap().load(url).into(new GlideSimpleTarget(lc));
    }
}

