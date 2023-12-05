package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.sql.SQLException;

public class MyReviewsController {
    @FXML
    private TableView<Review> reviewTable;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> datetimeColumn;
    @FXML
    private TableColumn<Course, Double> ratingColumn;
    @FXML
    private TableColumn<Course, Comment> commentsColumn;
    private ObservableList<Review> reviewsData;
    private Stage primaryStage;

    public void initialize(){
        reviewsData = FXCollections.observableArrayList();
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("courseMnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        datetimeColumn.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        // Refresh all reviews for this user
        populateUserReviewsData();
    }
    @FXML
    private void backToCourseSearch() throws IOException {
        FXMLLoader loader = CourseReviewsApplication.openScene(primaryStage, "course-search.fxml", "Course Search");
        CourseSearchController controller = (CourseSearchController) loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    @FXML
    private void handleClickTableView() throws IOException {
        Review review = reviewTable.getSelectionModel().getSelectedItem();
        if (review != null) {
            CourseLogic.setCurrentCourse(review.getCourseID());
            switchToCourse(review.getCourseID());
        }
    }
    private void switchToCourse(int id) throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage, "course-reviews.fxml", "Review of Course");
        CourseReviewsController controller = (CourseReviewsController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    private void populateUserReviewsData(){
        try{
            reviewsData.clear();
            reviewsData.addAll(ReviewLogic.findReviewsByCurrentUser());
        } catch (SQLException e) {e.printStackTrace();}
        reviewTable.setItems(reviewsData);
    }
}


