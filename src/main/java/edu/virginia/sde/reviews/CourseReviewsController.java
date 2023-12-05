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

public class CourseReviewsController {

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
    private ToggleGroup ratingToggleGroup;

    @FXML
    private TextField commentField;

    private int currentCourseID;

    private Stage primaryStage;

    private ObservableList<Review> reviewsData = FXCollections.observableArrayList();

    private static ReviewDataDriver reviewDataDriver;

//    public CourseReviewsController(int currentCourseID){
//        this.currentCourseID = currentCourseID;
//        String sqliteFileName = Credentials.getSqliteDataName();
//        reviewDataDriver = new ReviewDataDriver(sqliteFileName);
//    }

    public void initialize() {
        // Set up the columns in the TableView
        ratingColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRating()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatetime()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

        reviewDataDriver = new ReviewDataDriver(Credentials.getSqliteDataName());

        // Add button to each row for edit and delete actions
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

        // Add reviews data to TableView
        try {
            reviewDataDriver.connect();
            reviewsData.addAll(reviewDataDriver.findAllReviewsForCourse(currentCourseID));
            reviewDataDriver.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
            // handle database error
        }

        reviewsTable.setItems(reviewsData);
    }
    private void handleEditReview(Review selectedReview) {
        int course_id = selectedReview.courseID;
        int rating = selectedReview.rating;
        String comment = selectedReview.comment;
        try {
            ReviewLogic.editReview(course_id, rating, comment);
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Review newReview = new Review(currentCourseID, getCurrentUsername(), timestamp.toString(), newComment, newRating);

        reviewsData.add(newReview);

        try {
            reviewDataDriver.addReview(currentCourseID, getCurrentUsername(), newRating, newComment);
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database error
        }

        // Clear input fields
        ratingToggleGroup.selectToggle(null);
        commentField.clear();
    }
    private String getCurrentUsername(){
        return Credentials.getUsername();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setCurrentCourseID(int id){
        this.currentCourseID = id;
    }
}


