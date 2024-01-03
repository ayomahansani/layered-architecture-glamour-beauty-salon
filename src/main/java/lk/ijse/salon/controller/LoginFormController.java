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
import lk.ijse.salon.dto.LoginDto;
import lk.ijse.salon.dao.custom.impl.LoginDAOImpl;
import lk.ijse.salon.mail.Mail;
import lk.ijse.salon.db.DbConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginFormController {

    @FXML
    private AnchorPane loginpage;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);    //dependency injection ---> property injection

    private boolean validateLogin() {

        //validate userName
        String userName = txtUsername.getText();
        boolean matchesUserName = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{10,}", userName);
        if(!matchesUserName){
            new Alert(Alert.AlertType.ERROR, "Invalid User Name").show();
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
    void txtUsernameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        btnLoginOnAction(actionEvent);
    }


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

      //  boolean isValidated = validateLogin();

            String userName = txtUsername.getText();
            String password = txtPassword.getText();

            if (!userName.isEmpty() && !password.isEmpty()) {

                LoginDto loginDto = new LoginDto();

                loginDto.setPassword(password);
                loginDto.setUserName(userName);

                //boolean isMatched = loginDAO.checkCredential(loginDto);
                boolean isMatched = loginBO.checkCredential(loginDto); // Using loose coupling

                if (isMatched == false) {
                    new Alert(Alert.AlertType.CONFIRMATION, "wrong username or password try again!").show();

                } else {

                    //sending mail

                    Mail mail = new Mail();
                    mail.setMsg("You are successfully log to the RE'LUXE HAIR AND BEAUTY SALON..!");
                    mail.setTo(userName);
                    mail.setSubject("INFORM ABOUT LOGIN");


                    Thread thread = new Thread(mail);
                    thread.start();

                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = (Stage) loginpage.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Main Form");
                    stage.centerOnScreen();

                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
    }

    public void forgetPasswordOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/forgotpassword_form.fxml"));

        Scene scene = new Scene(rootNode);

        loginpage.getChildren().clear();
        Stage primaryStage = (Stage) loginpage.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Reset Password Form");
    }

    public void signupOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));

        Scene scene = new Scene(rootNode);

        loginpage.getChildren().clear();
        Stage primaryStage = (Stage) loginpage.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Signup Form");
    }
}
