package edu.virginia.sde.reviews;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseReviewsApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        /* MAIN START SCREEN */
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource(
                        "log-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Course Reviews");
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginLogic.setLoginDataDriver(new LoginDataDriver("LoginDataDriverTester.sqlite"));
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
