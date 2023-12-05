package edu.virginia.sde.reviews;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.stream.events.Comment;
import java.sql.SQLException;

public class MyReviewsController {
    @FXML
    private TableView<Review> reviewTable;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> titleColumn;
    @FXML
    private TableColumn<Course, Double> ratingColumn;
    @FXML
    private TableColumn<Course, Comment> commentsColumn;

    @FXML
    private ListView<String> reviewsListView;

    @FXML
    private Button backButton;

    private void initialize(){
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        //missing try-catch for database errors(?)
    }
    @FXML
    private void backToCourseSearch(){
        // scene switch CourseSearch scene
        try {
            // Load the FXML file for the CourseSearch scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course-search.fxml"));
            Parent root = loader.load();

            // Create a new Scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the Stage from the current button (assuming the button is part of a Scene)
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);


            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }


