package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddCourseController {
    public Label errorLabel;
    @FXML
    private TextField addSubjectField;
    @FXML
    private TextField addNumberField;
    @FXML
    private TextField addTitleField;
    private Stage primaryStage;
    public void initialize() {

    }
    @FXML
    private void handleSwitchToMyReviews() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage,"my-reviews.fxml", "My Reviews");
        MyReviewsController controller = (MyReviewsController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    @FXML
    private void handleLogout() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage, "log-in.fxml", "Course Review Application");
        LoginController controller = (LoginController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Credentials.setUsername("");
    }
    @FXML
    private void handleAdd(){
        try {
            // Getting the fields for adding
            String subject = addSubjectField.getText().strip();
            int number = parseCourseNumber(addNumberField.getText().strip());
            String title = addTitleField.getText().strip();
            // Add to the database
            CourseLogic.addCourse(subject, number, title);
            errorLabel.setText("Course added successfully!");
            // Refresh the course list after adding
//            courses.clear();
//            courses.addAll(CourseLogic.getAllCourses());
//            prevSearch();
        }
        catch (InvalidCourseException | NumberFormatException e) {
            errorLabel.setText(e.getMessage());
        }
        catch (SQLException e) {e.printStackTrace();}
    }
    @FXML
    private void handleBack() throws IOException {
        FXMLLoader loader = CourseReviewsApplication.openScene(primaryStage, "course-search.fxml", Credentials.getAppName());
        CourseSearchController controller = (CourseSearchController) loader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    private int parseCourseNumber(String input) throws NumberFormatException{
        try {
            if (input.length() != 4)
                throw new InvalidCourseException("The course number needs to be exactly 4-digits");
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("The course number has to be numbers only");
        }
    }
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
}
