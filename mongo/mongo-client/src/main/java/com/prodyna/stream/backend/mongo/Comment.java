package com.prodyna.stream.backend.mongo;

import java.io.Serializable;

/**
 * Details about a comment.
 */
public class Comment implements Serializable {

    private String commentId;
    private String created;
    private User author;
    private String text;

    public Comment() {
        super();
    }

    public Comment(String commentId, String created, User author, String text) {
        this.commentId = commentId;
        this.created = created;
        this.author = author;
        this.text = text;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (author != null ? !author.equals(comment.author) : comment.author != null) return false;
        if (commentId != null ? !commentId.equals(comment.commentId) : comment.commentId != null) return false;
        if (created != null ? !created.equals(comment.created) : comment.created != null) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId='" + commentId + '\'' +
                ", created='" + created + '\'' +
                ", author=" + author +
                ", text='" + text + '\'' +
                '}';
    }
}
