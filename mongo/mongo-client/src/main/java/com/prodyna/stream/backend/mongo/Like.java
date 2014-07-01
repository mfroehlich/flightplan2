package com.prodyna.stream.backend.mongo;

import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

/**
 * Describes a like. Is not a seperate entity, but will be embedded in Tweet.
 */
public class Like implements Serializable {

    @Id
    private String id;

    private String tweetId;
    private User author;
    private String created;

    public Like() {
        super();
    }

    public Like(String tweetId, User author, String created) {
        this.tweetId = tweetId;
        this.author = author;
        this.created = created;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        if (author != null ? !author.equals(like.author) : like.author != null) return false;
        if (created != null ? !created.equals(like.created) : like.created != null) return false;
        if (tweetId != null ? !tweetId.equals(like.tweetId) : like.tweetId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tweetId != null ? tweetId.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Like{" +
                "tweetId='" + tweetId + '\'' +
                ", author=" + author +
                ", created='" + created + '\'' +
                '}';
    }
}
