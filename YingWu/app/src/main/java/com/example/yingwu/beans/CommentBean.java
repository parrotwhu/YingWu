package com.example.yingwu.beans;

import android.content.Context;
import android.text.SpannableStringBuilder;

import com.example.yingwu.Constants;
import com.example.yingwu.utils.SpanUtils;
import com.example.yingwu.enums.TranslationState;

public class CommentBean {

//    private String commentType;//有无人回复的状态
//
//    private String parentUserName;//回复评论的人
//
//    private String childUserName;//评论的人
//
//    private String parentUserId;//回复评论的人
//
//    private String childUserId;//评论的人
    private String commentUserName;

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
    public String getCommentUserName(){
        return commentUserName;
    }

    private String commentContent;//评论内容

//    private int commentId=0;

//    public int getCommentId(){return commentId;}

    private TranslationState translationState = TranslationState.START;

    public void setTranslationState(TranslationState translationState) {
        this.translationState = translationState;
    }

    public TranslationState getTranslationState() {
        return translationState;
    }

//    public String getCommentType() {
//        return commentType;
//    }
//
//    public void setCommentType(String commentType) {
//        this.commentType = commentType;
//    }
//
//    public String getParentUserName() {
//        return parentUserName;
//    }
//
//    public void setParentUserName(String parentUserName) {
//        this.parentUserName = parentUserName;
//    }
//
//    public String getChildUserName() {
//        return childUserName;
//    }
//
//    public void setChildUserName(String childUserName) {
//        this.childUserName = childUserName;
//    }
//
//    public String getParentUserId() {
//        return parentUserId;
//    }
//
//    public void setParentUserId(String parentUserId) {
//        this.parentUserId = parentUserId;
//    }
//
//    public String getChildUserId() {
//        return childUserId;
//    }
//
//    public void setChildUserId(String childUserId) {
//        this.childUserId = childUserId;
//    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }


    /**
     * 富文本内容
     */
    private SpannableStringBuilder commentContentSpan;

    public SpannableStringBuilder getCommentContentSpan() {
        return commentContentSpan;
    }
    //评论内容
    public void build(Context context) {
        commentContentSpan = SpanUtils.makeSingleCommentSpan(context, commentUserName, commentContent);
//        if (commentType.equals(Constants.CommentType.COMMENT_TYPE_SINGLE)) {
//            commentId+=1;
//            commentContentSpan = SpanUtils.makeSingleCommentSpan(context, childUserName, commentContent);
//        } else {
//            commentId+=1;
//            commentContentSpan = SpanUtils.makeReplyCommentSpan(context, parentUserName, childUserName, commentContent);
//        }
    }
}

