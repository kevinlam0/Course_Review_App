package edu.virginia.sde.reviews;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseReviewsApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Credentials.setSqliteDataName("LocalDatabase.sqlite");
        Credentials.setAppName("Course Review");

        createTables();
        recalculateRatingAverageForAllCourses();
        /* MAIN START SCREEN */
        LoginLogic.setLoginDataDriver(new LoginDataDriver(Credentials.getSqliteDataName()));
        FXMLLoader fxmlLoader = openScene(primaryStage, "log-in.fxml", "Course Reviews");
        LoginController controller = (LoginController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        // Shows up on top
        primaryStage.setAlwaysOnTop(true);
        // Turns off the show on top
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
//            primaryStage.setAlwaysOnTop(false);
//        }));
//        timeline.play();
    }

    private static void createTables() {
        LoginDataDriver loginCreator = new LoginDataDriver(Credentials.getSqliteDataName());
        CourseDataDriver courseCreator = new CourseDataDriver(Credentials.getSqliteDataName());
        ReviewDataDriver reviewCreator = new ReviewDataDriver(Credentials.getSqliteDataName());

        try {
            loginCreator.connect();
            loginCreator.createTable();
            loginCreator.disconnect();
            courseCreator.connect();
            courseCreator.createTable();
            courseCreator.disconnect();
            reviewCreator.connect();
            reviewCreator.createTable();
            reviewCreator.disconnect();
        }
        catch (SQLException e){ e.printStackTrace(); }
    }

    private static void recalculateRatingAverageForAllCourses() throws SQLException {
        CourseDataDriver firstCourseDataDriver = new CourseDataDriver(Credentials.getSqliteDataName());
        // Getting all the courses in the database
        firstCourseDataDriver.connect();
        ArrayList<Course> courses = firstCourseDataDriver.getAllCourses();
        firstCourseDataDriver.disconnect();
        // Calculate the average and update
        List<Integer> IDS = courses.stream().map(Course::getId).toList();
        firstCourseDataDriver.connect();
        for (Integer id: IDS) {
            double avg = CourseLogic.calculateReviewAverage(id);
            firstCourseDataDriver.updateCourseAverage(id, avg);
        }
        firstCourseDataDriver.disconnect();
    }
    public static FXMLLoader openScene(Stage primaryStage, String fxmlFile, String stageTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CourseReviewsApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle(stageTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
        return fxmlLoader;
    }
}
