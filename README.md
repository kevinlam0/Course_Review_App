# JavaFX CRUD Course Review App
Full-stack JavaFX applicaiton for managing course reviews, featuring user authentication, course search functionality, and the ability to add and view reviews of each course. 

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
1. Java Development Kit (JDK) 17.0.9 needs to be installed. The location of this directory needs to be known.
2. Clone repository in within IntelliJ
3. Run `./gradlew build` to install dependencies
4. Set up the Run configuration:
   1. Navigate to the "CourseReviewsApplication.java" file within in the src/main/java directory
   2. From the 'Run' tab, -> 'Edit Configurations..' -> 'Add new run configuration...' -> 'Application'
   3. Name your configuration
   4. Click 'Modify options' -> 'Add VM Options'
   
