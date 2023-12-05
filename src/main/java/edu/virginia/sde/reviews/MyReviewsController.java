package edu.virginia.sde.reviews;

import com.sun.javafx.charts.Legend;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//import static edu.virginia.sde.reviews.ReviewLogic.reviewDataDriver;

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
    private ReviewDataDriver reviewDataDriver;

    private ObservableList<Review> reviewsData = FXCollections.observableArrayList();
    @FXML
    private Button backButton;

    private LoginLogic loginLogic;
    private Stage primaryStage;

    private  int currentCourseID;
    private String username;

    private void initialize(){

            subjectColumn.setCellValueFactory(new PropertyValueFactory<>("mnemonic"));
            numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
            commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

            //add logic to get data from database
            populateUserReviewsData(username);

    }

    private void populateUserReviewsData(String username){
        try{
            reviewsData.clear();



            reviewsData.addAll(reviewDataDriver.findReviewsByUsername(username, currentCourseID));

            reviewTable.setItems(reviewsData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * private void handleSearch(){
     *         String subject = subjectField.getText();
     *         int number = parseNumber(numberField.getText());
     *         String title = titleField.getText();
     *
     *         try {
     *             ObservableList<Course> searchResults = FXCollections.observableArrayList(courseLogic.filterCoursesBy(subject, number, title));
     *             courseTable.setItems(searchResults);
     *         } catch (SQLException e) {
     *             e.printStackTrace();
     *             // handle database errors
     *         }
     *     }
     */




/*

    private Course getCourseDetails(int courseID) {
        //need to get the logic to get the course details from the data source
        //return a course object based on the id
        return null;
    }
*/
    /*
    * // scene switch to log in screen
        // make sure previous log in data is cleared
        FXMLLoader fxmlLoader = new FXMLLoader(
                CourseReviewsApplication.class.getResource("log-in.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        LoginController controller = (LoginController) fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
        Credentials.setUsername("");*/
    @FXML
    private void backToCourseSearch() throws IOException {
        // scene switch CourseSearch scene

            // Load the FXML file for the CourseSearch scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course-search.fxml"));
            Scene scene = new Scene(loader.load());

            primaryStage.show();

            CourseSearchController controller = (CourseSearchController) loader.getController();

            // Get the Stage from the current button (assuming the button is part of a Scene)
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new Scene on the Stage
            stage.setScene(scene);


            stage.show();

    }
    }


