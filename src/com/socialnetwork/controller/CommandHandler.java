package com.socialnetwork.controller;

import com.socialnetwork.model.User;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.Comment;
import com.socialnetwork.service.FeedService;
import com.socialnetwork.service.UserService;
import com.socialnetwork.exception.UserNotFoundException;
import com.socialnetwork.exception.UserNotLoggedInException;
import com.socialnetwork.exception.PostNotFoundException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandHandler {
    private FeedService feedService;
    private UserService userService;

    public CommandHandler(UserService userService, FeedService feedService) {
        this.userService = userService;
        this.feedService = feedService;
    }

    public void handleCommand(String command) {
        String[] parts = command.split("~");
        String mainCommand = parts[0].toLowerCase();

            switch (mainCommand) {
                case "signup":
                    handleSignup(parts);
                    break;
                case "login":
                    handleLogin(parts);
                    break;
                case "logout":
                    handleLogout();
                    break;
                case "post":
                    handlePost(parts);
                    break;
                case "follow":
                    handleFollow(parts);
                    break;
                case "reply":
                    handleReply(parts);
                    break;
                case "upvote":
                    handleUpvote(parts);
                    break;
                case "downvote":
                    handleDownvote(parts);
                    break;
                case "upvotecmt":
                    handleUpvoteComment(parts);
                    break;
                case "downvotecmt":
                    handleDownvoteComment(parts);
                    break;
                case "shownewsfeed":
                    handleShowNewsFeed(parts);
                    break;
                default:
                    System.out.println("Unknown command.");
            }
    }

    private void handleSignup(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Error: Username is required for signup.");
            return;
        }
        userService.signup(parts[1]);
        System.out.println("Signed up successfully.");
    }

    private void handleLogin(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Error: Username is required for login.");
            return;
        }
        try {
            boolean loggedIn = userService.login(parts[1]);
            if (loggedIn) {
                System.out.println("Logged in as " + parts[1]);
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLogout() {
        try {
            userService.logout();
            System.out.println("Logged out successfully.");
        } catch (UserNotLoggedInException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handlePost(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Error: Post content and timestamp required.");
            return;
        }
        try {
            // Get the current time and format it as a timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String currentTimestamp = LocalDateTime.now().format(formatter);

            // Ensure the user is logged in
            if (userService.getCurrentUser() == null) {
                System.out.println("Error: You must be logged in to post.");
                return;
            }

            String content = parts[1];
            User user = userService.getCurrentUser();
            Post newPost = new Post(content, currentTimestamp, user);
            user.addPost(newPost);
            //feedService.post(user, content, currentTimestamp);
            // Feedback to the user
            System.out.println("Post created successfully at " + currentTimestamp);
        } catch (UserNotLoggedInException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleFollow(String[] parts) {
        try {
            if (parts.length < 2) {
                System.out.println("Error: Username to follow is required.");
                return;
            }
            // Get followed username from command input
            String followedUsername = parts[1];

            // Ensure the current user is logged in
            User follower = userService.getCurrentUser();
            if (follower == null) {
                System.out.println("Error: You must be logged in to follow.");
                return;
            }

            // Fetch the followed user object by username
            User followed = userService.getUserByUsername(followedUsername);

            if (followed == null) {
                System.out.println("Error: Followed user not found.");
                return;
            }

            // Perform the follow action
            feedService.follow(follower, followed);

            // Print success message
            System.out.println(follower.getUsername() + " is now following " + followed.getUsername() + " successfully!");
        } catch (UserNotLoggedInException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleReply(String[] parts) {
        try {
            if (parts.length < 3) {
                System.out.println("Error: Comment text required.");
                return;
            }
            int postId = Integer.parseInt(parts[1]);
            String comment = parts[2];

            // Ensure user is logged in before replying
            User user = userService.getCurrentUser();
            if (user == null) {
                System.out.println("Error: You must be logged in to reply.");
                return;
            }

            // Collect all posts in the feed (from followed users and the current user)
            List<Post> allPosts = new ArrayList<>();

            // Add posts from followed users
            for (User followedUser : user.getFollowing()) {
                allPosts.addAll(followedUser.getPosts());
            }

            // Add posts from the current user
            allPosts.addAll(user.getPosts());

            // Add posts from the global feed
            allPosts.addAll(user.getFeed());

            // Retrieve the post by ID from the combined list of all posts
            Post post = allPosts.stream()
                    .filter(p -> p.getId() == postId) // Compare by post ID
                    .findFirst()
                    .orElse(null);

            if (post == null) {
                System.out.println("Error: Post with ID " + postId + " not found.");
                return;
            }
            // Generate the current timestamp for the reply
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String currentTimestamp = LocalDateTime.now().format(formatter);

            // Add the comment to the post
            feedService.replyToPost(user, post, comment, currentTimestamp);
            System.out.println("Reply posted successfully to Post ID: " + postId);

        } catch (UserNotLoggedInException | PostNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleUpvote(String[] parts) {
        try {
            // Ensure the correct number of parts is provided for the command
            if (parts.length < 2) {
                System.out.println("Error: Post ID is required.");
                return;
            }

            int postId = Integer.parseInt(parts[1]);
            // Ensure the current user is logged in
            User user = userService.getCurrentUser();
            if (user == null) {
                System.out.println("Error: You must be logged in to upvote.");
                return;
            }

            // Collect all posts in the feed (from followed users and the current user)
            List<Post> allPosts = new ArrayList<>();

            // Add posts from followed users
            for (User followedUser : user.getFollowing()) {
                allPosts.addAll(followedUser.getPosts());
            }

            // Add posts from the current user
            allPosts.addAll(user.getPosts());

            // Add posts from the global feed (this will include all posts)
            allPosts.addAll(user.getFeed());

            // Debug: Print all posts in the feed
            System.out.println("All posts in feed:");
            allPosts.forEach(post -> {
                System.out.println("Post ID: " + post.getId() + " | Content: " + post.getContent());
            });

            // Retrieve the post by ID from the combined list of all posts
            Post post = allPosts.stream()
                    .filter(p -> p.getId() == postId) // Compare by post ID
                    .findFirst()
                    .orElse(null);

            if (post == null) {
                System.out.println("Error: Post with ID " + postId + " not found.");
                return;
            }

            // Upvote the post
            feedService.upvote(user, post);
            System.out.println("Post with ID " + postId + " upvoted successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Post ID format. Please enter a valid number.");
        } catch (UserNotLoggedInException | PostNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void handleDownvote(String[] parts) {
        try {
            int postId = Integer.parseInt(parts[1]);
            feedService.downvote(userService.getCurrentUser(), userService.getCurrentUser().getPosts().get(postId));
        } catch (UserNotLoggedInException | PostNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleUpvoteComment(String[] parts) {
        try {
            int postId = Integer.parseInt(parts[1]);
            int commentId = Integer.parseInt(parts[2]);
            feedService.upvoteComment(userService.getCurrentUser(), userService.getCurrentUser().getPosts().get(postId).getComments().get(commentId));
        } catch (UserNotLoggedInException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleDownvoteComment(String[] parts) {
        try {
            int postId = Integer.parseInt(parts[1]);
            int commentId = Integer.parseInt(parts[2]);
            feedService.downvoteComment(userService.getCurrentUser(), userService.getCurrentUser().getPosts().get(postId).getComments().get(commentId));
        } catch (UserNotLoggedInException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleShowNewsFeed(String[] parts) {
        try {
            feedService.showNewsFeed(userService.getCurrentUser());
        } catch (UserNotLoggedInException e) {
            System.out.println(e.getMessage());
        }
    }
}
