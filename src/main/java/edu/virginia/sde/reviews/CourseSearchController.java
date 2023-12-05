package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CourseSearchController {
    public Button searchButton;
    public Label errorLabel;
    public Button myReviewButton;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> titleColumn;
    @FXML
    private TableColumn<Course, String> ratingColumn;
    @FXML
    private TextField subjectField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField addSubjectField;
    @FXML
    private TextField addNumberField;
    @FXML
    private TextField addTitleField;
    private ObservableList<Course> courses;
    private Stage primaryStage;
    private String[] prevQuery = {"", "", ""};

    public void initialize(){
        courses = FXCollections.observableArrayList();
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
        errorLabel.setText("Hello "+Credentials.getUsername());
        ReviewLogic.setReviewDataDriver(new ReviewDataDriver(Credentials.getSqliteDataName()));
        CourseLogic.setCourseDataDriver(new CourseDataDriver(Credentials.getSqliteDataName()));
        CourseLogic.setReviewDataDriver(new ReviewDataDriver(Credentials.getSqliteDataName()));

        try {
            courses.addAll(CourseLogic.getAllCourses());
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database errors
        }
        courseTable.setItems(courses);
    }
    @FXML
    private void handleSearch(){
        // Resets message when we search
        errorLabel.setText("");
        // Get the information from the fields
        String subject = subjectField.getText().strip();
        int number = parseNumber(numberField.getText().strip());
        String title = titleField.getText().strip();
        try {
            // Display the courses
            ObservableList<Course> searchResults = FXCollections.observableArrayList(CourseLogic.filterCoursesBy(subject, number, title));
            courseTable.setItems(searchResults);
            // Saves the previous valid query
            prevQuery[0] = subject;
            prevQuery[1] = numberField.getText().strip();
            prevQuery[2] = title;
        }
        catch (SQLException e) {e.printStackTrace();}
        catch (InvalidCourseException e) {errorLabel.setText(e.getMessage());}
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
            // Refresh the course list after adding
            courses.clear();
            courses.addAll(CourseLogic.getAllCourses());
            prevSearch();
        }
        catch (InvalidCourseException | NumberFormatException e) {errorLabel.setText(e.getMessage());}
        catch (SQLException e) {e.printStackTrace();}
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
    private void handleClickTableView() throws IOException {
        Course course = courseTable.getSelectionModel().getSelectedItem();
        if (course != null) {
            CourseLogic.setCurrentCourse(course.getId());
            switchToCourse();
        }
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
    private int parseNumber(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // handle invalid number format
            return 0;
        }
    }
    private void switchToCourse() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage,"course-reviews.fxml", "Review of Course");
        CourseReviewsController controller = (CourseReviewsController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }
    private void prevSearch(){
        errorLabel.setText("Your course has been successfully added.");
        String subject = prevQuery[0].strip();
        int number = parseNumber(prevQuery[1].strip());
        String title = prevQuery[2].strip();
        try {
            ObservableList<Course> searchResults = FXCollections.observableArrayList(CourseLogic.filterCoursesBy(subject, number, title));
            courseTable.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database errors
        }
        catch (InvalidCourseException e) {
            errorLabel.setText(e.getMessage());
        }
    }
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
}
