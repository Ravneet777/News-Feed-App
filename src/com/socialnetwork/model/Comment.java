package com.socialnetwork.model;

import java.util.List;
import java.util.ArrayList;

public class Comment {
    private String content;
    private String timestamp;
    private int upvotes;
    private int downvotes;
    private List<Comment> replies;

    public Comment(String content, String timestamp) {
        this.content = content;
        this.timestamp = timestamp;
        this.upvotes = 0;
        this.downvotes = 0;
        this.replies = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void addReply(Comment reply) {
        this.replies.add(reply);
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }
}
