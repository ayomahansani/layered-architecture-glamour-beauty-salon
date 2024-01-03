package lk.ijse.salon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.*;
import lk.ijse.salon.bo.custom.impl.*;
import lk.ijse.salon.dao.custom.*;
import lk.ijse.salon.dao.custom.impl.*;
import lk.ijse.salon.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class DashboardFormController {
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


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);    //dependency injection ---> property injection
    private AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);  //dependency injection ---> property injection
    private ServiceBO serviceBO = (ServiceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SERVICE);  //dependency injection ---> property injection
    private ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);  //dependency injection ---> property injection
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);   //dependency injection ---> property injection
    private StaffBO staffBO = (StaffBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STAFF);    //dependency injection ---> property injection
    private ReportBO reportBO = (ReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REPORT);  //dependency injection ---> property injection

    @FXML
    void goAppointmentsPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/appointment_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Appointment Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goCosmeticProductsPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/product_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Product Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goCustomersPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goOrdersPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/placeorder_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Place Order Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goServicesPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/service_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Services Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goStaffPageOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/staff_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Staff Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void ordersViewOnAction(ActionEvent event) throws JRException, SQLException {
        reportBO.allOrdersView();
    }

    @FXML
    void servicesViewOnAction(ActionEvent event) throws JRException, SQLException {
        reportBO.allServicesView();
    }

    @FXML
    void staffViewOnAction(ActionEvent event) throws JRException, SQLException {
        reportBO.allStaffView();
    }

    @FXML
    void appointmentsViewOnAction(ActionEvent event) throws JRException, SQLException {
        reportBO.allAppointmentDetails();
    }

    @FXML
    void cosmeticProductsViewOnAction(ActionEvent event) throws JRException, SQLException {
        reportBO.allProductsView();
    }

    @FXML
    void customersViewOnAction(ActionEvent event) throws JRException, SQLException {
        reportBO.allCustomersView();
    }


    public void initialize(){
        setLblNoOfOrders();
        setLblNoOfAppointments();
        setLblNoOfServices();
        setLblNoOfProducts();
        setLblNoOfCustomers();
        setLblNoOfStaff();
    }

    private void setLblNoOfOrders() {

        try {
            //int noOfOrders = orderDAO.setCurrentNumber();
            int noOfOrders = orderBO.setCurrentNumberOfOrders();  // Using loose coupling
            lblNoOfOrders.setText(String.valueOf(noOfOrders));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfAppointments() {
        try {
            //int noOfAppointments = appointmentDAO.setCurrentNumber();
            int noOfAppointments = appointmentBO.setCurrentNumberOfAppointments();    // Using loose coupling
            lblNoOfAppointments.setText(String.valueOf(noOfAppointments));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfServices() {
        try {
            //int noOfServices = serviceDAO.setCurrentNumber();
            int noOfServices = serviceBO.setCurrentNumberOfServices();    // Using loose coupling
            lblNoOfServices.setText(String.valueOf(noOfServices));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfProducts() {
        try {
            //int noOfProducts = productDAO.setCurrentNumber();
            int noOfProducts = productBO.setCurrentNumberOfProducts();    // Using loose coupling
            lblNoOfProducts.setText(String.valueOf(noOfProducts));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfCustomers() {
        try {
            //int noOfCustomer = customerDAO.setCurrentNumber();
            int noOfCustomer = customerBO.setCurrentNumberOfCustomers();  // Using loose coupling
            lblNoOfCustomers.setText(String.valueOf(noOfCustomer));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLblNoOfStaff() {
        try {
            //int noOfStaff = staffDAO.setCurrentNumber();
            int noOfStaff = staffBO.setCurrentNumberOfStaff();    // Using loose coupling
            lblNoOfStaff.setText(String.valueOf(noOfStaff));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

