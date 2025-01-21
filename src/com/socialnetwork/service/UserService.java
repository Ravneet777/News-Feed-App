package com.socialnetwork.service;

import com.socialnetwork.model.User;
import com.socialnetwork.exception.UserNotFoundException;
import com.socialnetwork.exception.UserNotLoggedInException;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<Integer, User> users;
    private int userIdCounter;
    private User currentUser; // Track the logged-in user

    public UserService() {
        this.users = new HashMap<>();
        this.userIdCounter = 1;
        this.currentUser = null; // No user logged in initially
    }

    public User signup(String username) {
        User user = new User(username, userIdCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(int userId) {
        return users.get(userId);
    }

    // Login method
    public boolean login(String username) throws UserNotFoundException {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                this.currentUser = user; // Set the logged-in user
                return true;
            }
        }
        throw new UserNotFoundException("User with username " + username + " not found.");
    }

    public User getCurrentUser() throws UserNotLoggedInException {
        if (currentUser == null) {
            throw new UserNotLoggedInException("No user is logged in.");
        }
        return currentUser;
    }

    public User getUserByUsername(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user; // Return the user if username matches
            }
        }
        return null; // Return null if no user is found with the given username
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public void logout() throws UserNotLoggedInException {
        if (currentUser == null) {
            throw new UserNotLoggedInException("No user is logged in.");
        }
        this.currentUser = null; // Clear the logged-in user
    }
}
