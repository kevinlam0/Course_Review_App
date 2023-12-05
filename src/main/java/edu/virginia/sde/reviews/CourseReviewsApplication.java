package edu.virginia.sde.reviews;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CourseReviewsApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Credentials.setSqliteDataName("LoginDataDriverTester.sqlite");
        CourseDataDriver firstCourseDataDriver = new CourseDataDriver(Credentials.getSqliteDataName());
        firstCourseDataDriver.connect();
        ArrayList<Course> courses = firstCourseDataDriver.getAllCourses();
        firstCourseDataDriver.disconnect();
        List<Integer> IDS = courses.stream().map(Course::getId).toList();
        firstCourseDataDriver.connect();
        for (Integer id: IDS) {
            double avg = CourseLogic.calculateReviewAverage(id);
            firstCourseDataDriver.updateCourseAverage(id, avg);
        }
        firstCourseDataDriver.disconnect();

        /* MAINtest START SCREEN */
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource(
                        "log-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Course Reviews");
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginLogic.setLoginDataDriver(new LoginDataDriver(Credentials.getSqliteDataName()));
        var controller = (LoginController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        /* STARTING ON THE MAIN SCREEN */
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                CourseReviewsApplication.class.getResource("course-search.fxml")
//        );
//        CourseDataDriver cdd = new CourseDataDriver("LoginDataDriverTester.sqlite");
//        CourseLogic.setCourseDataDriver(cdd);
//
//        Scene scene = new Scene(fxmlLoader.load());
//        primaryStage.setTitle("Course Reviews");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        var controller = (CourseSearchController) fxmlLoader.getController();
//        controller.setPrimaryStage(primaryStage);
    }

}
