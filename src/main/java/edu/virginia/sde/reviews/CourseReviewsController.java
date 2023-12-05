package edu.virginia.sde.reviews;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CourseReviewsController {
    /* GROUP FOR THE RATING IN REVIEW */
    @FXML
    private ToggleGroup ratingToggleGroup;
    @FXML
    public RadioButton rating1;
    @FXML
    public RadioButton rating2;
    @FXML
    public RadioButton rating3;
    @FXML
    public RadioButton rating4;
    @FXML
    public RadioButton rating5;
    @FXML
    private TextField commentField;

    /* BUTTONS */
    @FXML
    public Button deleteReviewButton;
    @FXML
    public Button submitReviewButton;

    /* COURSE INFORMATION DISPLAY */
    @FXML
    public Label courseInfoLabel;
    @FXML
    public Label mnemonicLabel;
    @FXML
    public Label numberLabel;
    @FXML
    public Label titleLabel;
    @FXML
    public Label averageRatingLabel;

    /* TABLE STUFF */
    @FXML
    private TableView<Review> reviewsTable;
    @FXML
    private TableColumn<Review, Integer> ratingColumn;
    @FXML
    private TableColumn<Review, String> timestampColumn;
    @FXML
    private TableColumn<Review, String> commentColumn;
    private ObservableList<Review> reviewsData;

    @FXML
    public Label errorLabel;
    private Stage primaryStage;

    public void initialize() {
        reviewsData = FXCollections.observableArrayList();
        // Set up the columns in the TableView
        ratingColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRating()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatetime()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));
        // Set up rating radio buttons
        ratingToggleGroup = new ToggleGroup();
        rating1.setToggleGroup(ratingToggleGroup);
        rating2.setToggleGroup(ratingToggleGroup);
        rating3.setToggleGroup(ratingToggleGroup);
        rating4.setToggleGroup(ratingToggleGroup);
        rating5.setToggleGroup(ratingToggleGroup);

        try {
            reviewsData.clear();
            reviewsData.addAll(CourseLogic.getAllReviews());
            ArrayList<Review> review = CourseLogic.getCurrentReview();
            if (!review.isEmpty()){
                commentField.setText(review.get(0).comment);
                switch (review.get(0).rating) {
                    case 1 -> ratingToggleGroup.selectToggle(rating1);
                    case 2 -> ratingToggleGroup.selectToggle(rating2);
                    case 3 -> ratingToggleGroup.selectToggle(rating3);
                    case 4 -> ratingToggleGroup.selectToggle(rating4);
                    case 5 -> ratingToggleGroup.selectToggle(rating5);
                }
            }
            // Course Details
            Course course = CourseLogic.getCurrentCourse();
            mnemonicLabel.setText(course.getMnemonic());
            numberLabel.setText(course.getNumber());
            titleLabel.setText(course.getTitle());
            averageRatingLabel.setText(course.getAverage());
        } catch (SQLException e) {e.printStackTrace();}
        reviewsTable.setItems(reviewsData);
    }
    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = CourseReviewsApplication.openScene(primaryStage, "course-search.fxml", Credentials.getAppName());
        var controller = (CourseSearchController) loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    @FXML
    private void handleReviewSubmission() {
        // Get fields
        RadioButton selectedRadioButton = (RadioButton) ratingToggleGroup.getSelectedToggle();
        int newRating = Integer.parseInt(selectedRadioButton.getText());
        String newComment = commentField.getText();

        try {
            ArrayList<Review> reviews = CourseLogic.getCurrentReview();
            // If there is no review existing for this course and user
            if (reviews.isEmpty()){CourseLogic.addReviewToCourse(newRating, newComment);}
            else {handleEditReview(newRating, newComment);}
            // Display
            reviewsData.clear();
            reviewsData.addAll(CourseLogic.getAllReviews());
            // Recalculate the average
            Course course = CourseLogic.getCurrentCourse();
            averageRatingLabel.setText(course.getAverage());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDeleteReview() {

        try {
            // See if the review exists
            ArrayList<Review> reviews = CourseLogic.getCurrentReview();
            if (reviews.isEmpty()){
                throw new IllegalStateException("You cannot delete a review if you do not have one currently submitted");
            }

            else {
                // Perform the delete
                CourseLogic.deleteCurrentReview();
                // Refresh reviews
                reviewsData.clear();
                reviewsData.addAll(CourseLogic.getAllReviews());
                // Clears the review fields
                ratingToggleGroup.selectToggle(null);
                commentField.clear();
            }
            // Display new average
            Course course = CourseLogic.getCurrentCourse();
            averageRatingLabel.setText(course.getAverage());
        }
        catch (SQLException e) {e.printStackTrace();}
        catch (IllegalStateException e){errorLabel.setText(e.getMessage());}
    }
    private void handleEditReview(int newRating, String newComment) {
        try {CourseLogic.editCurrentReview(newRating, newComment);}
        catch (SQLException e) {e.printStackTrace();}
    }
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
}


