package lk.ijse.salon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.PaymentBO;
import lk.ijse.salon.bo.custom.impl.PaymentBOImpl;
import lk.ijse.salon.dao.custom.PaymentDAO;
import lk.ijse.salon.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.salon.db.DbConnection;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class PaymoneyFormController {

    @FXML
    private AnchorPane payMoneyForm;

    @FXML
    private Label lblFullAmount;

    @FXML
    private TextField txtCashAmount;

    @FXML
    private Label lblBalance;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);  //dependency injection ---> property injection

    private boolean validateCash() {

        //validate cash
        String cash = txtCashAmount.getText();
        boolean matchesCash = Pattern.matches("[0-9]{2,}", cash);
        if(!matchesCash){
            new Alert(Alert.AlertType.ERROR, "Invalid Amount").show();
            return false;
        }

        return true;
    }


    public void initialize() throws SQLException {
        setFullAmount();
    }

    private void setFullAmount() throws SQLException {
        //double fullAmount = paymentDAO.getFullAmount();
        double fullAmount = paymentBO.getFullAmount(); // Using loose coupling

        lblFullAmount.setText(String.valueOf(fullAmount));
    }

    @FXML
    void txtCashAmountOnAction(ActionEvent event) {

        boolean isValidated = validateCash();

        if(isValidated) {

            double tot = Double.parseDouble(lblFullAmount.getText());
            double cash = Double.parseDouble(txtCashAmount.getText());
            double balance = cash - tot;

            lblBalance.setText(String.valueOf(balance));

        }
    }

}
