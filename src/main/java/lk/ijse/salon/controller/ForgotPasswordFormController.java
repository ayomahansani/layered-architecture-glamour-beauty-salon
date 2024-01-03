package lk.ijse.salon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.LoginBO;
import lk.ijse.salon.bo.custom.impl.LoginBOImpl;
import lk.ijse.salon.dao.custom.LoginDAO;
import lk.ijse.salon.dao.custom.impl.LoginDAOImpl;
import lk.ijse.salon.dto.LoginDto;
import lk.ijse.salon.db.DbConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ForgotPasswordFormController {
    public TextField txtPwd;
    public Button btnReset;
    public TextField txtUserName;
    public Label lblAlert1;
    public AnchorPane forgotpwpage;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);    //dependency injection ---> property injection

    private boolean validateLogin() {

        //validate userName
        String userName = txtUserName.getText();
        boolean matchesUserName = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{10,}", userName);
        if(!matchesUserName){
            new Alert(Alert.AlertType.ERROR, "Invalid User Name").show();
            return false;
        }

        //validate password
        String password = txtPwd.getText();
        boolean matchesPassword = Pattern.matches("[a-zA-Z0-9!@#$%^&*()_+=;':\",./<>?|]{8,}", password);
        if(!matchesPassword){
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            return false;
        }

        return true;
    }

    public void initialize(){
        txtUserName.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent actionEvent){
        txtPwd.requestFocus();
    }

    @FXML
    void txtPwdOnAction(ActionEvent actionEvent) throws IOException {
        btnResetPasswordOnAction(actionEvent);
    }


    public void btnResetPasswordOnAction(ActionEvent actionEvent) throws IOException {

        boolean isValidated = validateLogin();

        if(isValidated) {

            String userName = txtUserName.getText();

            try {
                //boolean isCheck = loginDAO.checkUserName(userName);
                boolean isCheck = loginBO.checkUserName(userName); // Using loose coupling
                if (isCheck) {
                    String pw = txtPwd.getText();

                    var dto = new LoginDto(userName, pw);

                    //boolean isSet = loginDAO.update(dto);
                    boolean isSet = loginBO.updatePassword(dto);  // Using loose coupling
                    if (isSet) {

                        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

                        Scene scene = new Scene(rootNode);

                        forgotpwpage.getChildren().clear();
                        Stage primaryStage = (Stage) forgotpwpage.getScene().getWindow();

                        primaryStage.setScene(scene);
                        primaryStage.setTitle("Login Form");

                        new Alert(Alert.AlertType.CONFIRMATION, "Password Reset successful!!").show();
                    }
                } else {
                    lblAlert1.setText("Username is incorrect");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
}
