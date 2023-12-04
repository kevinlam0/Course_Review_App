package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class CourseSearchController {
    public Button searchButton;
    public ListView courseListView;
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

    //objects for search
    @FXML
    private TextField subjectField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField titleField;

    //objects for add
    @FXML
    private TextField addSubjectField;
    @FXML
    private TextField addNumberField;
    @FXML
    private TextField addTitleField;

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
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        try {
            courses.addAll(courseLogic.getAllCourses());
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database errors
        }
        courseTable.setItems(courses);
    }
    @FXML
    private void handleSearch(){
        String subject = subjectField.getText();
        int number = parseNumber(numberField.getText());
        String title = titleField.getText();

        try {
            ObservableList<Course> searchResults = FXCollections.observableArrayList(courseLogic.filterCoursesBy(subject, number, title));
            courseTable.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database errors
        }
    }
    @FXML
    private void handleAdd(){
        try {
            String subject = addSubjectField.getText();
            int number = parseNumber(addNumberField.getText());
            String title = addTitleField.getText();

            courseLogic.addCourse(subject, number, title);

            // Refresh the course list after adding
            courses.clear();
            courses.addAll(courseLogic.getAllCourses());
        } catch (InvalidCourseException | SQLException e) {
            e.printStackTrace();
            // Handle invalid course or database errors
        }
    }
    @FXML
    private void switchToMyReviews(){
        // scene switch my reviews screen
    }
    @FXML
    private void handleLogout(){
        // scene switch to log in screen
        // make sure previous log in data is cleared
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
