# Social Network Feed Sorting System

This is a social network feed system where users can create posts, upvote/downvote posts, and follow other users. The feed is dynamically sorted based on the user's choice of sorting criteria, including **author**, **date**, and **time**.

## Features

- **Post Creation**: Users can create posts that will appear in their feed and the feeds of users they follow.
- **Upvote and Downvote**: Users can upvote or downvote posts. A user can upvote or downvote a post only once.
- **Follow Users**: Users can follow other users, and posts from followed users will appear in their feed.
- **Dynamic Feed Sorting**: Users can sort their feed based on different criteria:
  - **Author**: Sort posts alphabetically by the author's name.
  - **Date**: Sort posts based on the date the post was created.
  - **Time**: Sort posts by the exact timestamp, most recent first.
 
## Requirements

- Java 8 or above
- An IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code)

## Setup Instructions
Clone the Repository

## Sample console commands to give to test

```
Enter a command: signup~lucious
Enter a command: signup~tom
Enter a command: login~tom
Logged in as tom
Enter a command: post~I am going to be the darkest dark wizard of all time
Post created successfully at 2025-01-17 10:00
Enter a command: post~I am lord Voldemort btw 3:)
Post created successfully at 2025-01-17 10:05
Enter a command: login~lucious
Logged in as lucious
Enter a command: follow~tom
lucious is now following tom successfully!
Enter a command: shownewsfeed
```
Post ID: 1 | Content: I am going to be the darkest dark wizard of all time | Upvotes: 0 | Downvotes: 0 | Comments: 0 | Posted: Just now
Post ID: 2 | Content: I am lord Voldemort btw 3:) | Upvotes: 0 | Downvotes: 0 | Comments: 0 | Posted: Just now
Enter a command: reply~1~I am with you dark lord!
Reply posted successfully to Post ID: 1

