package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

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

    private LoginLogic loginLogic;
    private Stage primaryStage;

    //private CourseReviewController courseReviewController
    // (implement after CourseReviewController is made)
    public CourseSearchController(){
        // arguments should be LoginLogic loginLogic, CourseLogic courseLogic, CourseReviewController courseReviewController
        //this.courseReviewController = courseReviewController;
        this.courses = FXCollections.observableArrayList();
    }
    public void initialize(){
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

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
        String subject = subjectField.getText();
//        System.out.println("This is the subject: " + "\""+subject+" \"");
        int number = parseNumber(numberField.getText());
        System.out.println("This is the subject: " + "\""+number+" \"");
        String title = titleField.getText();

        try {
            ArrayList<Course> courses = CourseLogic.filterCoursesBy(subject, number, title);
            System.out.println(courses.size());
            courses.forEach(System.out::println);
            ObservableList<Course> searchResults = FXCollections.observableArrayList(CourseLogic.filterCoursesBy(subject, number, title));
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

            CourseLogic.addCourse(subject, number, title);

            // Refresh the course list after adding
            courses.clear();
            courses.addAll(CourseLogic.getAllCourses());
        } catch (InvalidCourseException | SQLException e) {
            e.printStackTrace();
            // Handle invalid course or database errors
        }
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
