package edu.virginia.sde.reviews;
import edu.virginia.sde.reviews.Exceptions.PasswordIncorrectException;
import edu.virginia.sde.reviews.Exceptions.UserAlreadyExistsException;
import edu.virginia.sde.reviews.Exceptions.UserNotFoundException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField newUsernameField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Label errorLabel;

    private Stage primaryStage;




    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (LoginLogic.isLoginSuccessful(username, password)) {
                System.out.println("Login successful");
                switchToCourseSearch();
            } else {
                // handle unsuccessful login
                errorLabel.setText("Invalid username or password");

            }
        } catch (SQLException e){
            e.printStackTrace();
            errorLabel.setText("Database error");
        } catch (PasswordIncorrectException e){
            errorLabel.setText("Invalid Password");
        } catch (UserNotFoundException e){
            errorLabel.setText("Invalid Username");
        }


    }
    public void handleCreateAccount(){
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();

        try {
            if (newPassword.length() < 8)
                errorLabel.setText("Password must be at least 8 characters");
            else {
                LoginLogic.createUser(newUsername, newPassword);
                // handle successful user creation
                System.out.println("User created successfully");
            }

        } catch (UserAlreadyExistsException e) {
            // handle user already exists
            errorLabel.setText("User already exists");
        } catch (SQLException e) {
            e.printStackTrace();
            // handle database-related errors
            errorLabel.setText("Database error");
        }
    }
    public void handleClose(){
        Platform.exit();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void switchToCourseSearch(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    CourseReviewsApplication.class.getResource(
                            "course-search.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Course Reviews");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
