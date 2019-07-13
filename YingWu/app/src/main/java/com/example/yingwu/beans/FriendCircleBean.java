package com.example.yingwu.beans;
import android.text.SpannableStringBuilder;

import com.example.yingwu.utils.Utils;
import com.example.yingwu.enums.TranslationState;

import java.util.List;

public class FriendCircleBean {

    private int viewType;//发送的动态是只有文字还是有图片
    public int getViewType() {
        return viewType;
    }
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    private String content;//文字内容
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
        setContentSpan(new SpannableStringBuilder(content));
    }

    //朋友圈Id
    private String circleId;
    public void setCircleId(String circleId) {this.circleId = circleId; }
    public String getCircleId(){ return circleId; }

    //评论内容
    private List<CommentBean> commentBeans;
    private boolean isShowComment;
    public boolean isShowComment() {
        return isShowComment;
    }
    public List<CommentBean> getCommentBeans() {
        return commentBeans;
    }
    public void setCommentBeans(List<CommentBean> commentBeans) {
        isShowComment = commentBeans != null && commentBeans.size() > 0;
        this.commentBeans = commentBeans;
    }

    //点赞信息
    private List<PraiseBean> praiseBeans;
    private boolean isShowPraise;//???
    public boolean isShowPraise() {
        return isShowPraise;
    }
    public List<PraiseBean> getPraiseBeans() {
        return praiseBeans;
    }
    public void setPraiseBeans(List<PraiseBean> praiseBeans) {
        isShowPraise = praiseBeans != null && praiseBeans.size() > 0;
        this.praiseBeans = praiseBeans;
    }

    //发布图片
    private List<String> imageUrls;
    public List<String> getImageUrls() {
        return imageUrls;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    //用户信息
    private UserBean userBean;
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    public UserBean getUserBean() {
        return userBean;
    }

    //位置、时间信息
    private OtherInfoBean otherInfoBean;
    public OtherInfoBean getOtherInfoBean() {
        return otherInfoBean;
    }
    public void setOtherInfoBean(OtherInfoBean otherInfoBean) { this.otherInfoBean = otherInfoBean; }

    //翻译状态
    private TranslationState translationState = TranslationState.START;
    public void setTranslationState(TranslationState translationState) {
        this.translationState = translationState;
    }
    public TranslationState getTranslationState() {
        return translationState;
    }

    private boolean isExpanded;//???
    public boolean isExpanded() {
        return isExpanded;
    }
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    private boolean isShowCheckAll;
    public boolean isShowCheckAll() {
        return isShowCheckAll;
    }
    public void setShowCheckAll(boolean showCheckAll) {
        isShowCheckAll = showCheckAll;
    }

    public SpannableStringBuilder getContentSpan() {
        return contentSpan;
    }
    public void setContentSpan(SpannableStringBuilder contentSpan) {
        this.contentSpan = contentSpan;
        this.isShowCheckAll = Utils.calculateShowCheckAllText(contentSpan.toString());
    }
    private SpannableStringBuilder contentSpan;

    public void setPraiseSpan(SpannableStringBuilder praiseSpan) {
        this.praiseSpan = praiseSpan;
    }
    public SpannableStringBuilder getPraiseSpan() {
        return praiseSpan;
    }
    private SpannableStringBuilder praiseSpan;


}

