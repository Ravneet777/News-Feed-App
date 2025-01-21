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

1. Sign Up Users:
Command: signup~lucious

Action: Creates a new user with the username lucious.
Command: signup~tom

Action: Creates a new user with the username tom.
2. Login as tom:
Command: login~tom
Action: Logs in the user with the username tom.
3. Post Creation by tom:
Command: post~I am going to be the darkest dark wizard of all time

Action: tom creates a post with the content: "I am going to be the darkest dark wizard of all time".
Command: post~I am lord Voldemort btw 3:)

Action: tom creates another post with the content: "I am lord Voldemort btw 3:)".
4. Login as lucious:
Command: login~lucious
Action: Logs in the user with the username lucious.
5. Follow tom:
Command: follow~tom
Action: The user lucious follows tom, meaning lucious will now see tom’s posts in their feed.
6. Show News Feed for lucious:
Command: shownewsfeed
Action: Displays the news feed for lucious, which includes both lucious's posts and the posts from tom (since lucious follows tom).
7. Reply to tom's Post:
Command: reply~1~I am with you dark lord!
Action: lucious replies to tom's post with ID 1 (which has the content "I am going to be the darkest dark wizard of all time") with the comment: "I am with you dark lord!".

Follow~tom
Shownewsfeed


reply~1~I am with you dark lord!
