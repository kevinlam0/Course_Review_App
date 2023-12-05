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

    @FXML
    public Label courseInfoLabel;
    @FXML
    public Label mnemonicLabel;
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
    private ToggleGroup ratingToggleGroup;
    @FXML
    public Label numberLabel;
    @FXML
    public Label titleLabel;
    @FXML
    public Label averageRatingLabel;
    @FXML
    public Button submitReviewButton;
    @FXML
    private TableView<Review> reviewsTable;

    @FXML
    private TableColumn<Review, Integer> ratingColumn;

    @FXML
    private TableColumn<Review, String> timestampColumn;

    @FXML
    private TableColumn<Review, String> commentColumn;

    @FXML
    private TableColumn<Review, Void> actionsColumn;


    @FXML
    private TextField commentField;


    private Stage primaryStage;

    private ObservableList<Review> reviewsData = FXCollections.observableArrayList();

    private static ReviewDataDriver reviewDataDriver;
    private static CourseDataDriver courseDataDriver;

    public void initialize() {
        // Set up the columns in the TableView
        ratingColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRating()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatetime()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

        reviewDataDriver = new ReviewDataDriver(Credentials.getSqliteDataName());
        courseDataDriver = new CourseDataDriver(Credentials.getSqliteDataName());

        ratingToggleGroup = new ToggleGroup();
        rating1.setToggleGroup(ratingToggleGroup);
        rating2.setToggleGroup(ratingToggleGroup);
        rating3.setToggleGroup(ratingToggleGroup);
        rating4.setToggleGroup(ratingToggleGroup);
        rating5.setToggleGroup(ratingToggleGroup);


        // Add button to each row for edit and delete actions


        // Add reviews data to TableView
        try {
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


            Course course = CourseLogic.getCurrentCourse();
            mnemonicLabel.setText(course.getMnemonic());
            numberLabel.setText(course.getNumber());
            titleLabel.setText(course.getTitle());
            averageRatingLabel.setText(course.getAverage());

        } catch (SQLException e) {
            e.printStackTrace();
            // handle database error
        }

        reviewsTable.setItems(reviewsData);
    }
    private void handleEditReview(int newRating, String newComment) {

        try {
            CourseLogic.editCurrentReview(newRating, newComment);
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database error
        }
    }
    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(CourseReviewsApplication.class.getResource("course-search.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        var controller = (CourseSearchController) loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    @FXML
    private void handleReviewSubmission() {
        RadioButton selectedRadioButton = (RadioButton) ratingToggleGroup.getSelectedToggle();
        int newRating = Integer.parseInt(selectedRadioButton.getText());
        String newComment = commentField.getText();


        try {
            ArrayList<Review> reviews = CourseLogic.getCurrentReview();

            if (reviews.isEmpty()){
                CourseLogic.addReviewToCourse(newRating, newComment);
                reviewsData.clear();
                reviewsData.addAll(CourseLogic.getAllReviews());
            }
            else {
                handleEditReview(newRating, newComment);
                reviewsData.clear();
                reviewsData.addAll(CourseLogic.getAllReviews());
            }

            Course course = CourseLogic.getCurrentCourse();
            averageRatingLabel.setText(course.getAverage());



        } catch (SQLException e) {
            e.printStackTrace();
            // handle database error
        }


        // Clear input fields
//        ratingField.clear();
//        commentField.clear();
    }
    private String getCurrentUsername(){
        return Credentials.getUsername();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


}


