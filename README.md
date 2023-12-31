# JavaFX CRUD Course Review App
Full-stack JavaFX application for managing course reviews, featuring user authentication, course search functionality, and the ability to add and view reviews of each course. Uses Gradle as the build tool.

## Features
### Log-in Actions:
- Users can log in with an existing username and password or create new accounts with a unique username.
### Course Search: 
- Display list of courses that are clickable, leading to its course review screen
- Users can search and filter courses by subject mnemonic, number, and/or title.
- Users can add new courses to the list of courses
### Course Review:
- Display of course details, average rating, and other reviews
- Add review
### My reviews:
- Manage all reviews written by the user along with details
### Navigation:
- Seamless navigation between reviews, course search, and login pages. 

## To run:
1. Java Development Kit (JDK) 17.0.9 needs to be installed.
2. Clone repository in within your IDE
3. Run `./gradlew build` in your IDE terminal to install dependencies
4. Run application in your terminal with `./gradlew run`

## Database
Uses SQLite as the local database for this app. When cloned, there is already a default database with pre-entered reviews and courses. Users may continue using this local database or create a new database for personal use. 
   
