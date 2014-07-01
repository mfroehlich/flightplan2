package com.prodyna.stream.backend.mongo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.io.Serializable;
import java.util.List;

/**
 * Entity for Tweet. Also contains all likes, that are stripped on Json side.
 */
@Entity("tweets")
public class Tweet implements Serializable {

    @Id
    private String id;

    @Reference(lazy = false)
    private User author;
    private String text;
    private String created;
    private String updated;

    @JsonIgnore
    private List<Like> likes;

    private List<String> channels;
    private List<Comment> comments;
    private int commentsCount;
    private List<Image> images;
    private boolean userLiked;

    public Tweet() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public boolean isUserLiked() {
        return userLiked;
    }

    public void setUserLiked(boolean userLiked) {
        this.userLiked = userLiked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tweet)) return false;

        Tweet tweet = (Tweet) o;

        if (commentsCount != tweet.commentsCount) return false;
        if (userLiked != tweet.userLiked) return false;
        if (author != null ? !author.equals(tweet.author) : tweet.author != null) return false;
        if (channels != null ? !channels.equals(tweet.channels) : tweet.channels != null) return false;
        if (comments != null ? !comments.equals(tweet.comments) : tweet.comments != null) return false;
        if (created != null ? !created.equals(tweet.created) : tweet.created != null) return false;
        if (id != null ? !id.equals(tweet.id) : tweet.id != null) return false;
        if (images != null ? !images.equals(tweet.images) : tweet.images != null) return false;
        if (likes != null ? !likes.equals(tweet.likes) : tweet.likes != null) return false;
        if (text != null ? !text.equals(tweet.text) : tweet.text != null) return false;
        if (updated != null ? !updated.equals(tweet.updated) : tweet.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (channels != null ? channels.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + commentsCount;
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (userLiked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", likes=" + likes +
                ", channels=" + channels +
                ", comments=" + comments +
                ", commentsCount=" + commentsCount +
                ", images=" + images +
                ", userLiked=" + userLiked +
                '}';
    }


}
