package com.example.yingwu.others;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.yingwu.Constants;
import com.example.yingwu.LoginActivity;
import com.example.yingwu.beans.CommentBean;
import com.example.yingwu.beans.emoji.EmojiBean;
import com.example.yingwu.beans.emoji.EmojiDataSource;
import com.example.yingwu.beans.FriendCircleBean;
import com.example.yingwu.beans.OtherInfoBean;
import com.example.yingwu.beans.PraiseBean;
import com.example.yingwu.beans.UserBean;
import com.example.yingwu.utils.SpanUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class DataCenter {

    public static void init() {
        new Thread(DataCenter::loadEmojis).start();
    }

    public static final List<EmojiDataSource> emojiDataSources = new ArrayList<>();
    //显示表情面板，已完善
    public static void loadEmojis() {
        for (int i = 0; i < 2; i++) {
            EmojiDataSource emojiDataSource = new EmojiDataSource();
            List<EmojiBean> typeEmojiBeans = new ArrayList<>();
            if (i == 0) {
                for (int j = 0; j < Constants.TYPE01_EMOJI_NAME.length; j++) {
                    EmojiBean emojiBean = new EmojiBean();
                    emojiBean.setEmojiName(Constants.TYPE01_EMOJI_NAME[j]);
                    emojiBean.setEmojiResource(Constants.TYPE01_EMOJI_DREWABLES[j]);
                    typeEmojiBeans.add(emojiBean);
                }
                emojiDataSource.setEmojiType(Constants.EmojiType.EMOJI_TYPE_01);
            } else {
                for (int j = 0; j < Constants.TYPE02_EMOJI_NAME.length; j++) {
                    EmojiBean emojiBean = new EmojiBean();
                    emojiBean.setEmojiName(Constants.TYPE02_EMOJI_NAME[j]);
                    emojiBean.setEmojiResource(Constants.TYPE02_EMOJI_DREWABLES[j]);
                    typeEmojiBeans.add(emojiBean);
                }
                emojiDataSource.setEmojiType(Constants.EmojiType.EMOJI_TYPE_02);
            }
            emojiDataSource.setEmojiList(typeEmojiBeans);
            emojiDataSources.add(emojiDataSource);
        }
    }
    public static List<FriendCircleBean> makeFriendCircleBeans(Context context) {
        List<FriendCircleBean> friendCircleBeans = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            FriendCircleBean friendCircleBean = new FriendCircleBean();
            int randomValue = (int) (Math.random() * 300);
            if (randomValue < 100) {
                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);
            } else{

                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES);

            }
            friendCircleBean.setCommentBeans(makeCommentBeans(context));
            friendCircleBean.setImageUrls(makeImages());
            List<PraiseBean> praiseBeans = makePraiseBeans();
            friendCircleBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
            friendCircleBean.setPraiseBeans(praiseBeans);
            friendCircleBean.setContent(Constants.CONTENT[(int) (Math.random() * 10)]);
            UserBean userBean = new UserBean();
            userBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            userBean.setUserAvatarUrl(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
            friendCircleBean.setUserBean(userBean);
            OtherInfoBean otherInfoBean = new OtherInfoBean();
            otherInfoBean.setTime(Constants.TIMES[(int) (Math.random() * 20)]);
            int random = (int) (Math.random() * 30);
            if (random < 20) {
                otherInfoBean.setSource(Constants.SOURCE[random]);
            } else {
                otherInfoBean.setSource("");
            }
            friendCircleBean.setOtherInfoBean(otherInfoBean);
            friendCircleBeans.add(friendCircleBean);
        }
        return friendCircleBeans;
    }

    private static List<String> makeImages() {
        List<String> imageBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 9);
        if (randomCount == 0) {
            randomCount = randomCount + 1;
        } else if (randomCount == 8) {
            randomCount = randomCount + 1;
        }
        for (int i = 0; i < randomCount; i++) {
            imageBeans.add(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
        }
        return imageBeans;
    }
    private static List<PraiseBean> makePraiseBeans() {
        List<PraiseBean> praiseBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 20);
        for (int i = 0; i < randomCount; i++) {
            PraiseBean praiseBean = new PraiseBean();
            praiseBean.setPraiseUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
            praiseBeans.add(praiseBean);
        }
        return praiseBeans;
    }

    private static List<CommentBean> makeCommentBeans(Context context) {
        List<CommentBean> commentBeans = new ArrayList<>();
        int randomCount = (int) (Math.random() * 20);
        for (int i = 0; i < randomCount; i++) {
            CommentBean commentBean = new CommentBean();
            commentBean.setCommentUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//            if ((int) (Math.random() * 100) % 2 == 0) {
//                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_SINGLE);
//                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//            } else {
//                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_REPLY);
//                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//                commentBean.setParentUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//            }
            commentBean.setCommentContent(Constants.COMMENT_CONTENT[(int) (Math.random() * 30)]);
            commentBean.build(context);
            commentBeans.add(commentBean);
        }
        return commentBeans;
    }

//    public static List<FriendCircleBean> makeFriendCircleBeans(Context context) {
//        List<FriendCircleBean>friendCircleBeans=new ArrayList<>();
////        //服务器
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                String path = "http://10.0.2.2:8080/AndroidTest2/mustShowCircles?";
////                try {
////                    try {
////                        URL url = new URL(path); //新建url并实例化
////                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////                        connection.setRequestMethod("GET");//获取服务器数据
////                        connection.setReadTimeout(8000);//设置读取超时的毫秒数
////                        connection.setConnectTimeout(8000);//设置连接超时的毫秒数
////                        InputStream in = connection.getInputStream();
////                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
////                        while(true){
////                            String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
////                            if(result!=null){
////                                String userName="";
////                                circleId="";
////                                String content="";
////                                String time="";
////                                String location="";
////                                String [] arrayStr=result.split("\\s+");
////                                userName=arrayStr[0];
////                                circleId=arrayStr[1];
////                                content=arrayStr[2];
////                                time=arrayStr[3];
////                                location=arrayStr[4];
////                                if(time.equals("null"))
////                                {
////                                    time="未获取时间";
////                                }
////                                if(location.equals("null"))
////                                {
////                                    location="未获取地点";
////                                }
////                                FriendCircleBean friendCircleBean=new FriendCircleBean();
////                                UserBean userBean=new UserBean();
////                                userBean.setUserName(userName);
////                                userBean.setUserAvatarUrl("http://pic.qiantucdn.com/58pic/22/06/55/57b2d98e109c6_1024.jpg");
////                                friendCircleBean.setUserBean(userBean);
////                                friendCircleBean.setCircleId(circleId);
////                                friendCircleBean.setContent(content);
////                                OtherInfoBean otherInfoBean=new OtherInfoBean();
////                                otherInfoBean.setTime(time);
////                                otherInfoBean.setSource(location);
////                                friendCircleBean.setOtherInfoBean(otherInfoBean);
////                                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);
////                                friendCircleBean.setCommentBeans(makeCommentBeans(context,circleId));//显示评论
////                                //显示点赞
////                                List<PraiseBean> praiseBeans = makePraiseBeans(circleId);
////                                friendCircleBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
////                                friendCircleBean.setPraiseBeans(praiseBeans);
////                                friendCircleBeans.add(friendCircleBean);
////                            }
////                            else
////                                break;
////                        }
////                    } catch (MalformedURLException e) {
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////            }
////        }).start();
//
////        FriendCircleBean friendCircleBean=new FriendCircleBean();
////        friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);
////        friendCircleBean.setCommentBeans(makeCommentBeans(context));//显示评论
////        //显示点赞
////        List<PraiseBean> praiseBeans = makePraiseBeans();
////        friendCircleBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
////        friendCircleBean.setPraiseBeans(praiseBeans);
////        friendCircleBean.setContent("hahaha");
////        //显示朋友圈内用户信息
////        UserBean userBean = new UserBean();
////        userBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
////        userBean.setUserAvatarUrl(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
////        friendCircleBean.setUserBean(userBean);
////
////        //显示时间、位置等信息
////        OtherInfoBean otherInfoBean = new OtherInfoBean();
////        otherInfoBean.setTime(Constants.TIMES[(int) (Math.random() * 20)]);
////        friendCircleBean.setOtherInfoBean(otherInfoBean);
////        friendCircleBeans.add(friendCircleBean);
//        for (int i = 0; i < 1000; i++) {
//            FriendCircleBean friendCircleBean = new FriendCircleBean();
//            friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES);
//            friendCircleBean.setCommentBeans(makeCommentBeans(context));//显示评论
//            friendCircleBean.setImageUrls(makeImages());
//            //显示点赞
//            List<PraiseBean> praiseBeans = makePraiseBeans();
//            friendCircleBean.setPraiseSpan(SpanUtils.makePraiseSpan(context, praiseBeans));
//            friendCircleBean.setPraiseBeans(praiseBeans);
//            friendCircleBean.setContent(Constants.CONTENT[i]);
////            friendCircleBean.setContent(Constants.CONTENT[(int) (Math.random() * 10)]);//显示朋友圈文字内容
//            //显示朋友圈内用户信息
//            UserBean userBean = new UserBean();
//            userBean.setUserName(Constants.USER_NAME[i]);
//            userBean.setUserAvatarUrl(Constants.IMAGE_URL[i]);
////            userBean.setUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
////            userBean.setUserAvatarUrl(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
//            friendCircleBean.setUserBean(userBean);
//
//            //显示时间、位置等信息
//            OtherInfoBean otherInfoBean = new OtherInfoBean();
//            otherInfoBean.setTime(Constants.TIMES[i]);
//            otherInfoBean.setSource(Constants.SOURCE[i]);
////            otherInfoBean.setTime(Constants.TIMES[(int) (Math.random() * 20)]);
////            int random = (int) (Math.random() * 30);
////            if (random < 20) {
////                otherInfoBean.setSource(Constants.SOURCE[random]);
////            } else {
////                otherInfoBean.setSource("");
////            }
////            friendCircleBean.setOtherInfoBean(otherInfoBean);
//            friendCircleBeans.add(friendCircleBean);
////            int randomValue = (int) (Math.random() * 300);
////            if (randomValue < 100) {
////                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD);//只评论了文字
////            } else if (randomValue < 200) {
////                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES);//还评论了表情
////            }
////            else {
////                friendCircleBean.setViewType(Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL);
////            }
//
//         }
//        return friendCircleBeans;
//    }
//
//
//    private static List<String> makeImages() {
//        List<String> imageBeans = new ArrayList<>();
////        int randomCount = (int) (Math.random() * 9);
////        if (randomCount == 0) {
////            randomCount = randomCount + 1;
////        } else if (randomCount == 8) {
////            randomCount = randomCount + 1;
////        }
////        for (int i = 0; i < randomCount; i++) {
////            imageBeans.add(Constants.IMAGE_URL[(int) (Math.random() * 50)]);
////        }
//        for(int i=0;i<9;i++){
//            imageBeans.add(Constants.IMAGE_URL[i]);
//        }
//        return imageBeans;
//    }
//
//
//    private static List<PraiseBean> makePraiseBeans() {
//        List<PraiseBean>praiseBeans=new ArrayList<>();
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                String path = "http://10.0.2.2:8080/AndroidTest2/mustShowPraise?circleId="+circleId;
////                try {
////                    try {
////                        URL url = new URL(path); //新建url并实例化
////                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////                        connection.setRequestMethod("GET");//获取服务器数据
////                        connection.setReadTimeout(8000);//设置读取超时的毫秒数
////                        connection.setConnectTimeout(8000);//设置连接超时的毫秒数
////                        InputStream in = connection.getInputStream();
////                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
////                        while(true){
////                            String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
////                            if(result!=null){
////                                PraiseBean praiseBean=new PraiseBean();
////                                praiseBean.setPraiseUserName(result);
////                                praiseBeans.add(praiseBean);
////                            }
////                            else
////                                break;
////                        }
////                    } catch (MalformedURLException e) {
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////            }
////        }).start();
//
//        int praiseCount=6;
//        for(int i=0;i<praiseCount;i++){
//            PraiseBean praiseBean=new PraiseBean();
//            praiseBean.setPraiseUserName(Constants.USER_NAME[i]);
//            praiseBeans.add(praiseBean);
//        }
////        int randomCount = (int) (Math.random() * 20);
////        for (int i = 0; i < randomCount; i++) {
////            PraiseBean praiseBean = new PraiseBean();
////           praiseBean.setPraiseUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
////            //===========================================================================
//////            praiseBean.setPraiseUserId("123");
//////            praiseBean.setPraiseUserName("樱武");
////            praiseBeans.add(praiseBean);
////        }
//
////        PraiseBean praiseBean=new PraiseBean();
////        praiseBean.setPraiseUserName("praise");
////        praiseBeans.add(praiseBean);
//
//        return praiseBeans;
//    }
//
//
//
//    private static List<CommentBean> makeCommentBeans(Context context) {
//        List<CommentBean> commentBeans = new ArrayList<>();
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                String path = "http://10.0.2.2:8080/AndroidTest2/mustShowComment?circleId="+circleId;
////                try {
////                    try {
////                        URL url = new URL(path); //新建url并实例化
////                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////                        connection.setRequestMethod("GET");//获取服务器数据
////                        connection.setReadTimeout(8000);//设置读取超时的毫秒数
////                        connection.setConnectTimeout(8000);//设置连接超时的毫秒数
////                        InputStream in = connection.getInputStream();
////                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
////                        while(true){
////                            String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
////                            if(result!=null){
////                                String commentUserName="";
////                                String commentContent="";
////                                String []arrayStr=result.split("\\s+");
////                                commentUserName=arrayStr[0];
////                                commentContent=arrayStr[1];
////                                CommentBean commentBean=new CommentBean();
////                                commentBean.setCommentUserName(commentUserName);
////                                commentBean.setCommentContent(commentContent);
////                                commentBean.build(context);
////                                commentBeans.add(commentBean);
////                            }
////                            else
////                                break;
////                        }
////                    } catch (MalformedURLException e) {
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////            }
////        }).start();
//        for(int i=0;i<6;i++){
//            CommentBean commentBean = new CommentBean();
//            commentBean.setCommentUserName(Constants.USER_NAME[i]);
//            commentBean.setCommentContent(Constants.COMMENT_CONTENT[i]);
//            commentBean.build(context);
//            commentBeans.add(commentBean);
//        }
////        int randomCount = (int) (Math.random() * 20);
////        for (int i = 0; i < randomCount; i++) {
////            CommentBean commentBean = new CommentBean();
////            commentBean.setCommentUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//////            if ((int) (Math.random() * 100) % 2 == 0) {
//////                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_SINGLE);
//////
//////                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//////            } else {
//////                commentBean.setCommentType(Constants.CommentType.COMMENT_TYPE_REPLY);
//////                commentBean.setChildUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//////                commentBean.setParentUserName(Constants.USER_NAME[(int) (Math.random() * 30)]);
//////            }
////
////            commentBean.setCommentContent(Constants.COMMENT_CONTENT[(int) (Math.random() * 30)]);
////            commentBean.build(context);
////            commentBeans.add(commentBean);
////        }
//        return commentBeans;
//    }
}


