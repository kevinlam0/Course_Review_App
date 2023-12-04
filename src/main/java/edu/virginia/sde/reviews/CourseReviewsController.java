package edu.virginia.sde.reviews;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
public class CourseReviewsController {
    @FXML
    private Label courseInfoLabel;

    @FXML
    private Label mnemonicLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label averageRatingLabel;

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
    private TextField ratingField;

    @FXML
    private TextField commentField;

    @FXML
    private Button submitReviewButton;

    @FXML
    private Button backButton;

    private String sqliteFileName;

    private int currentCourseID;  // Placeholder for the current course ID

    private ObservableList<Review> reviewsData = FXCollections.observableArrayList();

    private ReviewDataDriver reviewDataDriver;

    public CourseReviewsController(){
        reviewDataDriver = new ReviewDataDriver(sqliteFileName);
    }

    public void initialize() {
        // Set up the columns in the TableView
        ratingColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRating()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatetime()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

        // Add button to each row for actions (edit/delete)
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    Review selectedReview = getTableRow().getItem();
                    if (selectedReview != null) {
                        handleEditReview(selectedReview);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Populate the TableView with reviews data
        try {
            reviewsData.addAll(reviewDataDriver.findAllReviewsForCourse(currentCourseID));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }

        reviewsTable.setItems(reviewsData);
    }
    private void handleEditReview(Review selectedReview) {

    }
    @FXML
    private void handleBack() {
        // Implement navigation logic to go back to the Course Search Screen
        // You can use the FXMLLoader to load the Course Search FXML file and show the scene
    }
    @FXML
    private void handleReviewSubmission() {
        // Get user input for new review
        int newRating = Integer.parseInt(ratingField.getText());
        String newComment = commentField.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Create a new review object
        Review newReview = new Review(currentCourseID, getCurrentUsername(), timestamp.toString(), newComment, newRating);

        // Add the new review to the TableView
        reviewsData.add(newReview);

        // Insert the new review into the database (replace with your data insertion logic)
        try {
            reviewDataDriver.addReview(currentCourseID, getCurrentUsername(), newRating, newComment);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        }

        // Clear input fields
        ratingField.clear();
        commentField.clear();
    }
    private String getCurrentUsername(){
        return "user"; // IMPLEMENT
    }
}


