package lk.ijse.salon.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.db.DbConnection;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class MainFormController {

    @FXML
    private AnchorPane main;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnAppointments;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnServices;

    @FXML
    private Button btnStaff;

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane dashboard;

    @FXML
    private Label lblNoOfOrders;

    @FXML
    private Label lblNoOfAppointments;

    @FXML
    private Label lblNoOfServices;

    @FXML
    private Label lblNoOfProducts;

    @FXML
    private Label lblNoOfCustomers;

    @FXML
    private Label lblNoOfStaff;

    @FXML
    private Label lblTodayDate;

    @FXML
    private Label lblCurrentTime;

    public void initialize() throws IOException{
        setDate();
        setTime();
        setDashBoardForm();
    }

    private void setDashBoardForm() throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    private void setDate() {
        //LocalDate now = LocalDate.now();
        lblTodayDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setTime() {

        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(true) {
                try{
                    Thread.sleep(500);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    lblCurrentTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    @FXML
    void btnAppointmentsOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/appointment_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnCustomersOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/placeorder_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnProductsOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/product_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnServicesOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/service_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnStaffOnAction(ActionEvent event) throws IOException {
        Parent form = FXMLLoader.load(getClass().getResource("/view/staff_form.fxml"));

        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(form);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        dashboard.getChildren().clear();
        Stage primaryStage = (Stage) dashboard.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
    }

}
