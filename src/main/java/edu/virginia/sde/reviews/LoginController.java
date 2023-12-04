package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
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




    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (LoginLogic.isLoginSuccessful(username, password)) {
                System.out.println("Login successful");
            } else {
                // handle unsuccessful login
                errorLabel.setText("Invalid username or password");

            }
        } catch (SQLException e){
            e.printStackTrace();
            errorLabel.setText("Database error");
        }
    }
    public void handleCreateAccount(){
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();

        try {
            LoginLogic.createUser(newUsername, newPassword);
            // handle successful user creation
            System.out.println("User created successfully");
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
    public void switchToCourseSearch(ActionEvent event){
        // implement after making course search fxml
    }
}
