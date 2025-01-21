package com.socialnetwork.service;

import com.socialnetwork.model.User;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.Comment;
import com.socialnetwork.exception.PostNotFoundException;
import com.socialnetwork.exception.UserNotLoggedInException;
import com.socialnetwork.util.TimeFormatter;

import java.util.*;
import java.util.stream.Collectors;

public class FeedService {
    private UserService userService;

    public FeedService(UserService userService) {
        this.userService = userService;
    }

    public void post(User user, String content, String timestamp) throws UserNotLoggedInException {
        if (user == null) {
            throw new UserNotLoggedInException("User is not logged in.");
        }
        Post post = new Post(content, timestamp,user);
        user.addPost(post);
    }

    public void follow(User follower, User followed) throws UserNotLoggedInException {
        if (follower == null || followed == null) {
            throw new UserNotLoggedInException("You need to be logged in to follow someone.");
        }
        follower.follow(followed);
        followed.getPosts().forEach(follower::addFeed);
    }

    public void replyToPost(User user, Post post, String commentContent, String timestamp) throws UserNotLoggedInException, PostNotFoundException {
        if (user == null) {
            throw new UserNotLoggedInException("You need to be logged in to comment.");
        }
        if (post == null) {
            throw new PostNotFoundException("Post not found.");
        }
        Comment comment = new Comment(commentContent, timestamp);
        post.addComment(comment);
    }

    public void replyToComment(User user, Comment parentComment, String commentContent, String timestamp) throws UserNotLoggedInException {
        if (user == null) {
            throw new UserNotLoggedInException("You need to be logged in to reply.");
        }
        parentComment.addReply(new Comment(commentContent, timestamp));
    }

    public void upvote(User user, Post post) throws UserNotLoggedInException, PostNotFoundException {
        if (user == null) {
            throw new UserNotLoggedInException("You need to be logged in to upvote.");
        }
        if (post == null) {
            throw new PostNotFoundException("Post not found.");
        }
        post.upvote();
    }

    public void downvote(User user, Post post) throws UserNotLoggedInException, PostNotFoundException {
        if (user == null) {
            throw new UserNotLoggedInException("You need to be logged in to downvote.");
        }
        if (post == null) {
            throw new PostNotFoundException("Post not found.");
        }
        post.downvote();
    }

    public void upvoteComment(User user, Comment comment) throws UserNotLoggedInException {
        if (user == null) {
            throw new UserNotLoggedInException("You need to be logged in to upvote a comment.");
        }
        comment.upvote();
    }

    public void downvoteComment(User user, Comment comment) throws UserNotLoggedInException {
        if (user == null) {
            throw new UserNotLoggedInException("You need to be logged in to downvote a comment.");
        }
        comment.downvote();
    }

    public void showNewsFeed(User user) {
        // Ensure the user is logged in
        if (user == null) {
            System.out.println("Error: You must be logged in to view the news feed.");
            return;
        }

        // Get the list of posts from followed users and the user’s own posts
        List<Post> allPosts = new ArrayList<>();

        // Add posts from followed users
        for (User followedUser : user.getFollowing()) {
            allPosts.addAll(followedUser.getPosts());
        }

        // Add posts from the current user as well
        allPosts.addAll(user.getPosts());

        // Sort posts based on the criteria
        List<Post> sortedPosts = allPosts.stream()
                .sorted((p1, p2) -> {
                    // Sort by: Followed user (1 if followed, 0 if not)
                    boolean p1Followed = user.getFollowing().contains(p1.getUser());
                    boolean p2Followed = user.getFollowing().contains(p2.getUser());

                    if (p1Followed && !p2Followed) {
                        return -1; // p1 should come before p2
                    } else if (!p1Followed && p2Followed) {
                        return 1; // p2 should come before p1
                    }

                    // If both are followed or both are not followed, sort by score
                    int score1 = p1.getUpvotes() - p1.getDownvotes();
                    int score2 = p2.getUpvotes() - p2.getDownvotes();
                    if (score1 != score2) {
                        return score2 - score1; // Higher score first
                    }

                    // Then sort by number of comments
                    int comments1 = p1.getComments().size();
                    int comments2 = p2.getComments().size();
                    if (comments1 != comments2) {
                        return comments2 - comments1; // More comments first
                    }

                    // Finally, sort by timestamp (most recent first)
                    return p2.getTimestamp().compareTo(p1.getTimestamp());
                })
                .collect(Collectors.toList());

        // Display the sorted posts
        sortedPosts.forEach(post -> {
            String formattedPostTime = TimeFormatter.timeAgo(post.getTimestamp());
            System.out.println("Post ID: " + post.getId() + " | " +
                    "Content: " + post.getContent() +
                    " | Upvotes: " + post.getUpvotes() +
                    " | Downvotes: " + post.getDownvotes() +
                    " | Comments: " + post.getComments().size() +
                    " | Posted: " + formattedPostTime);
            post.getComments().forEach(comment -> {
                String formattedCommentTime = TimeFormatter.timeAgo(comment.getTimestamp());
                System.out.println("  Comment: " + comment.getContent() +
                        " | Upvotes: " + comment.getUpvotes() +
                        " | Downvotes: " + comment.getDownvotes() +
                        " | Commented: " + formattedCommentTime);
                comment.getReplies().forEach(reply -> {
                    String formattedReplyTime = TimeFormatter.timeAgo(reply.getTimestamp());
                    System.out.println("    Reply: " + reply.getContent() +
                            " | Replied: " + formattedReplyTime);
                });
            });
        });
    }
}
