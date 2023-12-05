package edu.virginia.sde.reviews;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MyReviewsController {
    @FXML
    private TableView<Review> reviewTable;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> titleColumn;
    @FXML
    private TableColumn<Course, Double> ratingColumn;
    @FXML
    private TableColumn<Course, Comment> commentsColumn;

    @FXML
    private ListView<String> reviewsListView;

    @FXML
    private Button backButton;

    private void initialize(){

            subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
            numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
            commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

            //add logic to get data from database
        try {
            Object DatabaseHelper;
            List<Review> userReviews = DatabaseHelper.getUserReviews("username");
            reviewTable.getItems().addAll(userReviews);

            // Make each row clickable
            reviewTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    handleReviewClick(); // Handle the click event
                }
            });

        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }

        //missing try-catch for database errors(?)
    }

    private void handleReviewClick() {
        Review selectedReview = reviewTable.getSelectionModel().getSelectedItem();

        // Assuming you have a method to get the Course details based on the review
        Course selectedCourse = getCourseDetails(selectedReview.getCourseID());

        // Navigate to the CourseReview scene or perform any other desired action
        navigateToCourse(selectedCourse);
    }

    private void navigateToCourse(Course selectedCourse)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("course-review.fxml"));
        Parent root = loader.load();
        Object controller = loader.getController();
        controller.initialize(course); // Pass the selected Course to the CourseReviewController if needed

        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch ( IOException e) {
        e.printStackTrace(); // Handle FXML loading errors
    }
    }

    private Course getCourseDetails(int courseID) {
        //need to get the logic to get the course details from the data source
        //return a course object based on the id
        return null;
    }

    /*
    * // scene switch to log in screen
        // make sure previous log in data is cleared
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource("log-in.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginController controller = (LoginController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Credentials.setUsername("");*/
    @FXML
    private void backToCourseSearch(){
        // scene switch CourseSearch scene

            // Load the FXML file for the CourseSearch scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course-search.fxml"));
            Scene scene = new Scene(fxmlLoader.load());



            // Get the Stage from the current button (assuming the button is part of a Scene)
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);


            stage.show();

    }
    }


