package com.example.yingwu.beans;

public class UserBean {
    private String userAvatarUrl;

    private String userName;

//    private String userId;

    private String password;

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public void setPassword(String password){this.password=password;}

    public String getPassword(){return password;}

}


