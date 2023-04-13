package com.zengqy.httpstudy.domain;

/**
 * @包名: com.zengqy.httpstudy
 * @USER: zengqy
 * @DATE: 2022/4/22 10:31
 * @描述:
 */
public class CommentItem {


    /**
     * articleId : 234123
     * commentContent : 这是评论内容
     */

    public CommentItem(String articleId, String commentContent) {
        this.articleId = articleId;
        this.commentContent = commentContent;
    }

    private String articleId;
    private String commentContent;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "articleId='" + articleId + '\'' +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}

