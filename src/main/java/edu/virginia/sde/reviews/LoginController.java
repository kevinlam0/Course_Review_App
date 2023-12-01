package edu.virginia.sde.reviews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    private LoginLogic loginLogic;

    public LoginController(){

    }
    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (loginLogic.isLoginSuccessful(username, password)) {
                System.out.println("Login successful!");
            } else {
                // Handle unsuccessful login
                errorLabel.setText("Invalid username or password");

            }
        } catch (SQLException e){
            e.printStackTrace();
            errorLabel.setText("Database error");
        }
    }
    public void handleCreateAccount(){
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();

        try {
            loginLogic.createUser(newUsername, newPassword);
            // Handle successful user creation
            System.out.println("User created successfully!");
        } catch (UserAlreadyExistsException e) {
            // Handle user already exists error
            errorLabel.setText("User already exists");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related errors
            errorLabel.setText("Database error");
        }
    }
    public void handleClose(){
        Platform.exit();
    }
}
