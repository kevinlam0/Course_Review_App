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
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource(
                        "log-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Course Reviews");
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginLogic.setLoginDataDriver(new LoginDataDriver("LoginDataDriverTester.sqlite"));
    }

}
