package com.socialnetwork.model;

import java.util.*;

public class Post {
    private static int postIdCounter = 1; // Static counter for generating unique post IDs
    private int id;  // Post ID
    private String content;
    private int upvotes;
    private int downvotes;
    private List<Comment> comments;
    private String timestamp;
    private User user;
    private Map<User, String>votes;

    public Post(String content, String timestamp, User user) {
        this.id = postIdCounter++;
        this.content = content;
        this.upvotes = 0;
        this.downvotes = 0;
        this.user = user;
        this.comments = new ArrayList<>();
        this.timestamp = timestamp;
        this.votes=new HashMap<>();
    }



    public int getId() {
        return id;  // Return the unique post ID
    }

    public String getContent() {
        return content;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void upvote() {
        if (votes.containsKey(user)){
            if(votes.get(user).equals("upvote")){
                return;
            }
        }
        upvotes++;
    }

    public void downvote() {
        if (votes.containsKey(user)){
            if(votes.get(user).equals("downvote")){
                return;
            }
        }
        downvotes++;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }
}
