package com.socialnetwork.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private int id;
    private List<Post> posts;
    private List<User> following;
    private List<Post> feed;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
        this.posts = new ArrayList<>();
        this.following = new ArrayList<>();
        this.feed = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        System.out.println("Adding post with ID: " + post.getId() + " to user: " + this.username); // Debug output
        this.posts.add(post);
        this.feed.add(post);
    }

    public List<User> getFollowing() {
        return following;
    }

    public void follow(User user) {
        if (!this.following.contains(user)) {
            this.following.add(user);
        }
    }

    public List<Post> getFeed() {
        return feed;
    }

    public void addFeed(Post post) {
        this.feed.add(post);
    }
}
