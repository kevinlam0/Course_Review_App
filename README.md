# JavaFX CRUD Course Review App
Full-stack JavaFX applicaiton for managing course reviews, featuring user authentication, course search functionality, and the ability to add and view reviews of each course. Uses Gradle as the build tool.

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
2. JavaFX: [Link to download](https://gluonhq.com/products/javafx/)
3. Clone repository in within IntelliJ
4. Run `./gradlew build` to install dependencies
5. Set up the Run configuration:
   1. Navigate to the "CourseReviewsApplication.java" file within in the src/main/java directory
   2. From the 'Run' tab, -> 'Edit Configurations..' -> 'Add new run configuration...' -> 'Application'
   3. Name your configuration
   4. Click 'Modify options' -> 'Add VM Options'
   5. In VM Options, paste this `--module-path [Path to JavaFX lib folder] --add-modules javafx.controls,javafx.fxml` and replace the place holder.
      - For example: --module-path "/Users/kevinlam/javafx-sdk-17.0.9/lib" --add-modules javafx.controls,javafx.fxml
   6. Make CourseReviewsApplication the Main Class
   7. Click 'Apply'
6. Run application
   
