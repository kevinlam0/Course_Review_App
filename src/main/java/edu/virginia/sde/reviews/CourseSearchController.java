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

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseSearchController {
    public Button searchButton;
    public ListView courseListView;
    public Label errorLabel;
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

    private LoginLogic loginLogic;
    private Stage primaryStage;

    private String[] prevQuery = {"", "", ""};

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
        errorLabel.setText("Hello "+Credentials.getUsername());

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
        errorLabel.setText("");

        String subject = subjectField.getText().strip();
        int number = parseNumber(numberField.getText().strip());
        String title = titleField.getText().strip();

        prevQuery[0] = subject;
        prevQuery[1] = numberField.getText().strip();
        prevQuery[2] = title;




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
            prevSearch();

        } catch (InvalidCourseException e) {
            errorLabel.setText(e.getMessage());
            // Handle invalid course or database errors
        }
        catch (SQLException e) {
            e.printStackTrace();
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
    private void handleLogout() throws IOException {
        // scene switch to log in screen
        // make sure previous log in data is cleared
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource("log-in.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginController controller = (LoginController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Credentials.setUsername("");

    }

    private void switchToCourse() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource("course-reviews.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        CourseReviewsController controller = (CourseReviewsController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

    }
    @FXML
    private void handleClickTableView() throws IOException {
        Course course = courseTable.getSelectionModel().getSelectedItem();
        if (course != null) {
            CourseLogic.setCurrentCourse(course.getId());
            switchToCourse();

        }
    }
    private int parseNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // handle invalid number format
            return 0;
        }
    }

    private void prevSearch(){
        errorLabel.setText("");
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


}
