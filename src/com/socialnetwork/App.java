package com.socialnetwork;

import com.socialnetwork.controller.CommandHandler;
import com.socialnetwork.service.FeedService;
import com.socialnetwork.service.UserService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        FeedService feedService = new FeedService(userService);
        CommandHandler commandHandler = new CommandHandler(userService, feedService);

        System.out.println("Welcome to the News Feed simulation!");

        while (true) {
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            commandHandler.handleCommand(command);
        }
        scanner.close();
    }
}
