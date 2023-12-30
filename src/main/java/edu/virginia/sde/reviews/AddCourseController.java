package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.Exceptions.InvalidCourseException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddCourseController {
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
    private void handleAdd(){
        try {
            // Getting the fields for adding
            String subject = addSubjectField.getText().strip();
            int number = parseCourseNumber(addNumberField.getText().strip());
            String title = addTitleField.getText().strip();
            // Add to the database
            CourseLogic.addCourse(subject, number, title);
            // Refresh the course list after adding
//            courses.clear();
//            courses.addAll(CourseLogic.getAllCourses());
//            prevSearch();
        }
        catch (InvalidCourseException | NumberFormatException e) {
//            errorLabel.setText(e.getMessage());
        }
        catch (SQLException e) {e.printStackTrace();}
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
