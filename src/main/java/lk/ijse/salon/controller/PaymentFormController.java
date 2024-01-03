package lk.ijse.salon.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.*;
import lk.ijse.salon.bo.custom.impl.*;
import lk.ijse.salon.dao.custom.*;
import lk.ijse.salon.dao.custom.impl.*;
import lk.ijse.salon.db.DbConnection;
import lk.ijse.salon.dto.*;
import lk.ijse.salon.mail.Mail;
import lk.ijse.salon.tm.PaymentTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class PaymentFormController {

    @FXML
    private AnchorPane paymentForm;

    @FXML
    private JFXComboBox<String> cmbCustomerName;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentTime;

    @FXML
    private TableColumn<?, ?> colAppointmentAmount;

    @FXML
    private TableColumn<?, ?> colOrderAmount;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private Button btnTotalAmount;

    @FXML
    private Label lblAppointmentAmount;

    @FXML
    private Label lblOrderAmount;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private Label lblPaymentTime;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnAllPayment;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);  //dependency injection ---> property injection
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);   //dependency injection ---> property injection
    private AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);  //dependency injection ---> property injection
    private OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);    //dependency injection ---> property injection
    private QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);    //dependency injection ---> property injection
    ReportBO reportBO = (ReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REPORT);    //dependency injection ---> property injection
    private ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        generateNextPaymentId();
        setDate();
        setTime();
        loadCustomerNames();
        setCellValueFactory();
    }

    private void generateNextPaymentId() {
        try {
            //String paymentIdId = paymentDAO.generateNextId();
            String paymentIdId = paymentBO.generateNextPaymentId();    // Using loose coupling
            lblPaymentId.setText(paymentIdId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDate() {
        //LocalDate now = LocalDate.now();
        lblPaymentDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setTime() {

        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(true) {
                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    lblPaymentTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    private void loadCustomerNames() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            //List<CustomerDto> customerDtos = customerDAO.getAll();
            List<CustomerDto> customerDtos = customerBO.getAllCustomer();  // Using loose coupling

            for (CustomerDto dto : customerDtos) {
                obList.add(dto.getCusName());
            }

            cmbCustomerName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        colPaymentTime.setCellValueFactory(new PropertyValueFactory<>("payTime"));
        colAppointmentAmount.setCellValueFactory(new PropertyValueFactory<>("appAmount"));
        colOrderAmount.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totAmount"));
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException, SQLException {

        String payId = lblPaymentId.getText();

        String cusName = cmbCustomerName.getValue();

        //CustomerDto customer = customerDAO.searchByName(cusName);
        CustomerDto customer = customerBO.searchCustomerByName(cusName);   // Using loose coupling
        String cusId = customer.getCusId();

        double appAmount = Double.parseDouble(lblAppointmentAmount.getText());
        double orderAmount = Double.parseDouble(lblOrderAmount.getText());

        double fullAmount = appAmount + orderAmount;

        LocalDate payDate = LocalDate.parse(lblPaymentDate.getText());
        String payTime = lblPaymentTime.getText();

        PaymentDto paymentDto = new PaymentDto(payId, cusId, fullAmount, payDate, payTime);

        boolean isPayment = false;
        try {
            //isPayment = paymentDAO.save(paymentDto);
            isPayment = paymentBO.savePayment(paymentDto); // Using loose coupling

            if (isPayment) {

                Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/paymoney_form.fxml"));
                Scene scene = new Scene(anchorPane);

                Stage stage = new Stage();
                stage.setTitle("Pay Money");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                new Alert(Alert.AlertType.CONFIRMATION, "Payment Confirmed").show();


                //sending mail

                try {
                    //CustomerDto customerDto = customerDAO.search(cusId);
                    CustomerDto customerDto = customerBO.searchCustomer(cusId);    // Using loose coupling

                    if(customerDto != null) {
                        String sendmail = customerDto.getCusEmail();

                        JasperPrint jasperPrint = reportBO.invoiceOfPayment();
                        //JasperViewer.viewReport(jasperPrint, false);

                        JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/ayomahansani/Documents/EmailPDF/"+payId+".pdf");

                        Mail mail = new Mail();

                        mail.setMsg("Payment Successful..! \n\nThank you for join with us. Come again..!");
                        mail.setTo(sendmail);
                        mail.setSubject("RE'LUXE UNISEX HAIR AND BEAUTY SALON PAYMENT SUCCESSFULL ");
                        mail.setFile(new File("/Users/ayomahansani/Documents/EmailPDF/"+payId+".pdf"));


                        Thread thread = new Thread(mail);
                        thread.start();
                    }

                } catch (SQLException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }

        //appointment delete after payment has done
        //boolean isAppDeleted = appointmentDAO.delete(cusId);
        boolean isAppDeleted = appointmentBO.deleteAppointment(cusId); // Using loose coupling

        if(isAppDeleted){
            System.out.println("appointment is deleted");
        }else{
            new Alert(Alert.AlertType.ERROR, "not deleted");
        }

        //order delete after payment has done
        //boolean isOrderDeleted = orderDAO.delete(cusId);
        boolean isOrderDeleted = orderBO.deleteOrder(cusId);   // Using loose coupling

        if(isOrderDeleted){
            System.out.println("order is deleted");
        }else{
            new Alert(Alert.AlertType.ERROR, "not deleted");
        }

    }


    @FXML
    void btnTotalAmountOnAction(ActionEvent event) {

        String payId = lblPaymentId.getText();
        String cusName = cmbCustomerName.getValue();
        LocalDate payDate = LocalDate.parse(lblPaymentDate.getText());
        LocalTime payTime = LocalTime.parse(lblPaymentTime.getText());
        double appAmount = Double.parseDouble(lblAppointmentAmount.getText());
        double orderAmount = Double.parseDouble(lblOrderAmount.getText());
        double totAmount = appAmount + orderAmount;

        var cartTm = new PaymentTm(payId, cusName, payDate, payTime, appAmount, orderAmount, totAmount);

        obList.add(cartTm);

        tblPayment.setItems(obList);
    }

    @FXML
    void cmbCustomerNameOnAction(ActionEvent event) {

        String cusName = cmbCustomerName.getValue();

        try {
            //double orderAmount = queryDAO.getOrderAmount(cusName);
            double orderAmount = queryBO.getOrderAmount(cusName);  // Using loose coupling
            lblOrderAmount.setText(String.valueOf(orderAmount));

            //double appAmount = queryDAO.getAppointmentAmount(cusName);
            double appAmount = queryBO.getAppointmentAmount(cusName);  // Using loose coupling
            lblAppointmentAmount.setText(String.valueOf(appAmount));

            //btnTotalAmountOnAction(event);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnPaymentInvoiceOnAction(ActionEvent event) throws SQLException, JRException {

        reportBO.invoiceOfPayment();
    }

    @FXML
    void btnAllPaymentOnAction(ActionEvent event) throws JRException, SQLException {

        reportBO.allPaymentDetails();

    }

}
