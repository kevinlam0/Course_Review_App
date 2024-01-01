package edu.virginia.sde.reviews;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseReviewsController {
    @FXML
    public Line dynamicLine;
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
    private TextArea fullCommentTextArea;

    @FXML
    public Label errorLabel;
    private Stage primaryStage;

    public void initialize() {
        reviewsData = FXCollections.observableArrayList();
        setUpTableColumns();
        setUpRadioButtons();

        ((VBox) dynamicLine.getParent()).widthProperty().addListener((obs, oldWidth, newWidth) -> {
            dynamicLine.setEndX(newWidth.doubleValue()); });

        reviewsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fullCommentTextArea.setVisible(true);
            if (newValue != null) { fullCommentTextArea.setText(newValue.getComment()); }
            else { fullCommentTextArea.setText(""); }
        });

        try {
            findAndDisplayCourseInformation();
            displayInitialCourseAndUserRatings();
        }
        catch (SQLException e) { e.printStackTrace(); }
        reviewsTable.setItems(reviewsData);
    }

    @FXML
    private void handleReviewSubmission() {
        try {
            // Get fields
            RadioButton selectedRadioButton = (RadioButton) ratingToggleGroup.getSelectedToggle();
            int newRating = Integer.parseInt(selectedRadioButton.getText());
            String newComment = commentField.getText();

            ArrayList<Review> reviews = CourseLogic.getCurrentReview();
            // If there is no review existing for this course and user
            if (reviews.isEmpty()) { CourseLogic.addReviewToCourse(newRating, newComment); }
            else { editReview(newRating, newComment); }

            updateDisplayOfReviewDataAndAverageRating("");
        }
        catch (SQLException e) {e.printStackTrace();}
        catch (NullPointerException e) {errorLabel.setText("You cannot submit a review with no rating. Please select a rating 1-5");}
    }
    @FXML
    private void handleDeleteReview() {
        try {
            // See if review exists
            ArrayList<Review> reviews = CourseLogic.getCurrentReview();
            if (reviews.isEmpty()) {
                throw new IllegalStateException("You cannot delete a review if you do not have one currently submitted");
            }

            CourseLogic.deleteCurrentReview();
            updateDisplayOfReviewDataAndAverageRating("Review deletion successful!");
            ratingToggleGroup.selectToggle(null);
            commentField.clear();
        }

        catch (SQLException e) {e.printStackTrace();}
        catch (IllegalStateException e){errorLabel.setText(e.getMessage());}
    }


    /** SCENE SWITCHING */
    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = CourseReviewsApplication.openScene(primaryStage, "course-search.fxml", Credentials.getAppName());
        CourseSearchController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    @FXML
    private void handleSwitchToMyReviews() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage,"my-reviews.fxml", "My Reviews");
        MyReviewsController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    @FXML
    private void handleLogout() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage, "log-in.fxml", "Course Review Application");
        LoginController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Credentials.setUsername("");
    }



    private void findAndDisplayCourseInformation() throws SQLException {
        Course course = CourseLogic.getCurrentCourse();
        mnemonicLabel.setText(course.getMnemonic());
        numberLabel.setText(" " + course.getNumber());
        titleLabel.setText(" " + course.getTitle());
        averageRatingLabel.setText("Average rating: " + course.getAverage());
    }
    private void setUpRadioButtons() {
        ratingToggleGroup = new ToggleGroup();
        rating1.setToggleGroup(ratingToggleGroup);
        rating2.setToggleGroup(ratingToggleGroup);
        rating3.setToggleGroup(ratingToggleGroup);
        rating4.setToggleGroup(ratingToggleGroup);
        rating5.setToggleGroup(ratingToggleGroup);
    }
    private void setUpTableColumns() {
        ratingColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRating()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatetime()));
        commentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));
    }
    private void editReview(int newRating, String newComment) {
        try {CourseLogic.editCurrentReview(newRating, newComment);}
        catch (SQLException e) {e.printStackTrace();}
    }
    private void displayInitialCourseAndUserRatings() throws SQLException {
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
    }
    private void updateDisplayOfReviewDataAndAverageRating(String message) throws SQLException {
        Course course = CourseLogic.getCurrentCourse();
        errorLabel.setText(message);
        reviewsData.clear();
        reviewsData.addAll(CourseLogic.getAllReviews());
        averageRatingLabel.setText("Average score: " + course.getAverage());
    }
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
}


