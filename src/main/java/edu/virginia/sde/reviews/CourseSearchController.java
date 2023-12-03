package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class CourseSearchController {
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> titleColumn;
    @FXML
    private TableColumn<Course, Double> ratingColumn;

    @FXML
    private TextField subjectSearchField;
    @FXML
    private TextField numberSearchField;
    @FXML
    private TextField titleSearchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button myReviewsButton;
    @FXML
    private Button logoutButton;

    private ObservableList<Course> courses;

    private LoginLogic loginLogic;
    private CourseLogic courseLogic;

    //private CourseReviewController courseReviewController
    // (implement after CourseReviewController is made)
    public CourseSearchController(LoginLogic loginLogic, CourseLogic courseLogic){
        // arguments should be LoginLogic loginLogic, CourseLogic courseLogic, CourseReviewController courseReviewController
        this.loginLogic = loginLogic;
        this.courseLogic = courseLogic;
        //this.courseReviewController = courseReviewController;
        this.courses = FXCollections.observableArrayList();
    }
    public void initialize(){
        // initialize tables
    }
    private void handleSearch(){
        String subject = subjectSearchField.getText();
        int number = parseNumber(numberSearchField.getText());
        String title = titleSearchField.getText();

        // Perform the search using CourseLogic
        try {
            ObservableList<Course> searchResults = FXCollections.observableArrayList(courseLogic.filterCoursesBy(subject, number, title));
            courseTable.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related errors
        }
    }
    private void handleAdd(){
        try {
            String subject = subjectSearchField.getText();
            int number = parseNumber(numberSearchField.getText());
            String title = titleSearchField.getText();

            courseLogic.addCourse(subject, number, title);

            // Refresh the course list after adding
            courses.clear();
            courses.addAll(courseLogic.getAllCourses());
        } catch (InvalidCourseException | SQLException e) {
            e.printStackTrace();
            // Handle invalid course or database-related errors
        }
    }
    private void switchToMyReviews(){

    }

    private void handleLogout(){

    }
    private int parseNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // handle invalid number format
            return 0;
        }
    }
}
