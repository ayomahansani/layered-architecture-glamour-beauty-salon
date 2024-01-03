package lk.ijse.salon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.LoginBO;
import lk.ijse.salon.bo.custom.impl.LoginBOImpl;
import lk.ijse.salon.dao.custom.LoginDAO;
import lk.ijse.salon.dao.custom.impl.LoginDAOImpl;
import lk.ijse.salon.dto.LoginDto;
import lk.ijse.salon.dto.SignupDto;
import lk.ijse.salon.db.DbConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignupFormController {

    @FXML
    private AnchorPane signuppage;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSignup;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtFullName;

    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);    //dependency injection ---> property injection

    private boolean validateSignup() {

        //validate fullName
        String fullName = txtFullName.getText();
        boolean matchesFullName = Pattern.matches("[a-zA-Z\\s]{2,}", fullName);
        if(!matchesFullName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        //validate userName
        String userName = txtEmail.getText();
        boolean matchesUserName = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{10,}", userName);
        if(!matchesUserName){
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            return false;
        }

        //validate password
        String password = txtPassword.getText();
        boolean matchesPassword = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{8,}", password);
        if(!matchesPassword){
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            return false;
        }

        return true;
    }

    @FXML
    void txtFullNameOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws SQLException, IOException {
        btnSignupOnAction(event);
    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {

        boolean isValidated = validateSignup();

        if(isValidated) {

            String fullName = txtFullName.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();

            if (!fullName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                try {
                    //boolean isSignup = loginDAO.saveSignup(fullName,email,password);
                    boolean isSignup = loginBO.saveSignup(fullName,email,password); // Using loose coupling
                    if (isSignup) {
                        navigateToMainWindow();

                        new Alert(Alert.AlertType.CONFIRMATION, "Signup Success").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
            }
        }
    }

    private void navigateToMainWindow() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml"));
        Scene scene = new Scene(rootNode);

        signuppage.getChildren().clear();
        Stage primaryStage = (Stage) signuppage.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Main Form");
    }

    @FXML
    void loginOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        signuppage.getChildren().clear();
        Stage primaryStage = (Stage) signuppage.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
    }

}
