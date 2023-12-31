package edu.virginia.sde.reviews;
import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class CourseSearchController {
    @FXML
    public Label errorLabel;
    @FXML
    public Line dynamicLine;
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
    private ObservableList<Course> courses;
    private Stage primaryStage;

    public void initialize(){
        setUpTableColumns();
        initializeDataDrivers();
        // Line separator that extends and shrinks as user adjusts window
        ((VBox) dynamicLine.getParent()).widthProperty().addListener((obs, oldWidth, newWidth) -> {
            dynamicLine.setEndX(newWidth.doubleValue());});

        try {
            courses = FXCollections.observableArrayList();
            courses.addAll(CourseLogic.getAllCourses());
            courseTable.setItems(courses);
        }
        catch (SQLException e) { e.printStackTrace(); }

    }

    @FXML
    private void handleSearch(){
        errorLabel.setText("");

        String subject = subjectField.getText().strip();
        int number = parseNumber(numberField.getText().strip());
        String title = titleField.getText().strip();

        try {
            ObservableList<Course> searchResults = FXCollections.observableArrayList(CourseLogic.filterCoursesBy(subject, number, title));
            courseTable.setItems(searchResults);
        }
        catch (SQLException e) {e.printStackTrace();}
        catch (InvalidCourseException e) {errorLabel.setText(e.getMessage());}
    }
    @FXML
    private void handleClearing() {
        try {
            courses.addAll(CourseLogic.getAllCourses());
            courseTable.setItems(courses);
            subjectField.clear();
            numberField.clear();
            titleField.clear();
        }
        catch (SQLException e) {e.printStackTrace();}
    }
    @FXML
    private void handleClickTableView() throws IOException {
        Course course = courseTable.getSelectionModel().getSelectedItem();
        if (course != null) {
            CourseLogic.setCurrentCourse(course.getId());
            switchToCourse();
        }
    }


    /** SWITCHING SCENES */
    @FXML
    private void handleSwitchToAddCourse() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage,"add-course.fxml", "Add Course");
        AddCourseController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
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
        LoginController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Credentials.setUsername("");
    }
    private void switchToCourse() throws IOException {
        FXMLLoader fxmlLoader = CourseReviewsApplication.openScene(primaryStage,"course-reviews.fxml", "Review of Course");
        CourseReviewsController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }



    private static void initializeDataDrivers() {
        ReviewLogic.setReviewDataDriver(new ReviewDataDriver(Credentials.getSqliteDataName()));
        CourseLogic.setCourseDataDriver(new CourseDataDriver(Credentials.getSqliteDataName()));
        CourseLogic.setReviewDataDriver(new ReviewDataDriver(Credentials.getSqliteDataName()));
    }
    private void setUpTableColumns() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
    }
    private int parseNumber(String input){
        try { return Integer.parseInt(input); }
        catch (NumberFormatException e) { return 0; }
    }
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
}
