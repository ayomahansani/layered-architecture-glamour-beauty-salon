package lk.ijse.salon.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
import lk.ijse.salon.dto.AppointmentDto;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.dto.ServiceDto;
import lk.ijse.salon.dto.StaffDto;
import lk.ijse.salon.mail.Mail;
import lk.ijse.salon.tm.AppointmentTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AppointmentFormController {

    @FXML
    private AnchorPane appointmentForm;

    @FXML
    private Button btnServiceAddToTable;

    @FXML
    private TableView<AppointmentTm> tblAppointments;

    @FXML
    private TableColumn<?, ?> colServiceId;

    @FXML
    private TableColumn<?, ?> colServiceName;

    @FXML
    private TableColumn<?, ?> colServiceAmount;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Label lblAppointmentId;

    @FXML
    private Label lblTodayDate;

    @FXML
    private Label lblCustomerId;

    @FXML
    private JFXComboBox<String> cmbCustomerName;

    @FXML
    private JFXComboBox<String> cmbServiceName;

    @FXML
    private Label lblServiceId;

    @FXML
    private Label lblServiceAmount;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private JFXComboBox<String> cmbStaffName;

    @FXML
    private Label lblStaffId;

    @FXML
    private Button btnConfirmedAppointment;

    @FXML
    private Label lblNetTotal;

    @FXML
    private DatePicker appointmentDate;

    @FXML
    private TextField appointmentTime;

    @FXML
    private Label lblTimeAvailibility;

    @FXML
    private Label lblInfo;


    //Access the DAO layer
    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation

    private AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);  //dependency injection ---> property injection
    private StaffBO staffBO = (StaffBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STAFF);    //dependency injection ---> property injection
    private ServiceBO serviceBO = (ServiceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SERVICE);  //dependency injection ---> property injection
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);   //dependency injection ---> property injection
    private StaffAppointmentDetailBO staffAppointmentDetailBO = (StaffAppointmentDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STAFF_APPOINTMENT_DETAIL); //dependency injection ---> property injection
    private ReportBO reportBO = (ReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REPORT);  //dependency injection ---> property injection
    private ObservableList<AppointmentTm> obList = FXCollections.observableArrayList();

    private boolean validateAppDateandTime() {

        //validate app time
        String time = appointmentTime.getText();
        boolean matchesTime = Pattern.matches("^(([1-9]|1[0-2]).([0-5][0-9]) ?([AaPp][Mm]))$", time);
        if (!matchesTime) {
            new Alert(Alert.AlertType.ERROR, "Invalid Time").show();
            return false;
        }
        return true;
    }


    public void initialize() {
        generateNextAppointmentId();
        loadCustomerNames();
        loadServiceName();
        loadStaffName();
        setCellValueFactory();
    }


    private void generateNextAppointmentId() {
        try {
            //String appId = appointmentDAO.generateNextId();
            String appId = appointmentBO.generateNextAppointmentId();  // Using loose coupling
            lblAppointmentId.setText(appId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void loadStaffName() {

        ObservableList<String> oblist = FXCollections.observableArrayList();

        try {
            //List<StaffDto> staffDtos = staffDAO.getAll();
            List<StaffDto> staffDtos = staffBO.getAllStaff();  // Using loose coupling

            for(StaffDto dto : staffDtos){
                oblist.add(dto.getStaffName());
            }

            cmbStaffName.setItems(oblist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadServiceName() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            //List<ServiceDto> serviceDtos = serviceDAO.getAll();
            List<ServiceDto> serviceDtos = serviceBO.getAllServices(); // Using loose coupling

            for(ServiceDto dto: serviceDtos){
                obList.add(dto.getServiceName());
            }

            cmbServiceName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadCustomerNames() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            //List<CustomerDto> customerDtos = customerDAO.getAll();
            List<CustomerDto> customerDtos = customerBO.getAllCustomer();  // Using loose coupling

            for(CustomerDto dto: customerDtos){
                obList.add(dto.getCusName());
            }

            cmbCustomerName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("serId"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serName"));
        colServiceAmount.setCellValueFactory(new PropertyValueFactory<>("serAmount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionBtn"));
    }


    @FXML
    void btnConfirmedAppointmentOnAction(ActionEvent event) {

            String appId = lblAppointmentId.getText();
            String cusId = lblCustomerId.getText();
            String appDate = appointmentDate.getValue().toString();
            //System.out.println(appDate);
            String appTime = appointmentTime.getText();
            double appNetTotal = Double.parseDouble(lblNetTotal.getText());
            String serviceId = lblServiceId.getText();
            String staffId = lblStaffId.getText();

            if(!appId.isEmpty() && !cusId.isEmpty() && !appDate.isEmpty() && !appTime.isEmpty() && !(appNetTotal == 0.0) && !serviceId.isEmpty() && !staffId.isEmpty()){

            List<AppointmentTm> appTmList = new ArrayList<>();

            for (int i = 0; i < tblAppointments.getItems().size(); i++) {
                AppointmentTm appointmentTm = tblAppointments.getSelectionModel().getSelectedItem();
                appTmList.add(appointmentTm);
            }


            var appointmentDto = new AppointmentDto(appId, cusId, appDate, appTime, appNetTotal, serviceId, staffId, appTmList);

            try {
                //boolean isConfirmed = appointmentDAO.confirmedAppointment(appointmentDto);
                boolean isConfirmed = appointmentBO.confirmedAppointment(appointmentDto);  // Using loose coupling
                if (isConfirmed) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Appointment Confirmed").show();
                    clearFields();

                    //sending mail

                    try {
                        //CustomerDto customerDto = customerDAO.search(cusId);
                        CustomerDto customerDto = customerBO.searchCustomer(cusId);    // Using loose coupling

                        if(customerDto != null) {
                            String sendmail = customerDto.getCusEmail();

                            JasperPrint jasperPrint = reportBO.invoiceOfAppointment();
                            //JasperViewer.viewReport(jasperPrint, false);

                            JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/ayomahansani/Documents/EmailPDF/"+appId+".pdf");

                            Mail mail = new Mail();
                            mail.setMsg("Appointment Confirmed..! \n\nThank you for join with us.");
                            mail.setTo(sendmail);
                            mail.setSubject("RE'LUXE UNISEX HAIR AND BEAUTY SALON APPOINTMENT CONFIRMED");
                            mail.setFile(new File("/Users/ayomahansani/Documents/EmailPDF/"+appId+".pdf"));

                            Thread thread = new Thread(mail);
                            thread.start();
                        }

                    } catch (SQLException ex) {
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Appointment Not Confirmed").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
        }

    }

    private void clearFields() {
        lblCustomerId.setText("");
        lblNetTotal.setText("");
        lblServiceAmount.setText("");
        lblServiceId.setText("");
        lblStaffId.setText("");
        appointmentTime.setText("");
        lblTimeAvailibility.setText("");
        lblInfo.setText("");
    }


    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    void btnServiceAddToTableOnAction(ActionEvent event) {

        boolean isValidated = validateAppDateandTime();

        if(isValidated) {

            String ser_id = lblServiceId.getText();
            String ser_name = cmbServiceName.getValue();
            double amount = Double.parseDouble(lblServiceAmount.getText());
            Button btn = new Button("Remove");

            setRemoveBtnAction(btn);
            btn.setCursor(Cursor.HAND);

            var cartTm = new AppointmentTm(ser_id, ser_name, amount, btn);

            obList.add(cartTm);

            tblAppointments.setItems(obList);
            calculateTotal();
        }
    }


    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblAppointments.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblAppointments.refresh();
                calculateTotal();
            }
        });
    }


    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblAppointments.getItems().size(); i++) {
            total += (double) colServiceAmount.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String name = cmbCustomerName.getValue();
        //CustomerModel customerModel = new CustomerModel()

        try {
            //CustomerDto customerDto = customerDAO.searchByName(name);
            CustomerDto customerDto = customerBO.searchCustomerByName(name);   // Using loose coupling
            lblCustomerId.setText(customerDto.getCusId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbServiceNameOnAction(ActionEvent event) {
        String name = cmbServiceName.getValue();

        try {
            //ServiceDto serviceDto = serviceDAO.searchByName(name);
            ServiceDto serviceDto = serviceBO.searchServiceByName(name);   // Using loose coupling
            lblServiceId.setText(serviceDto.getServiceId());
            lblServiceAmount.setText(String.valueOf(serviceDto.getServiceAmount()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbStaffNameOnAction(ActionEvent event) {
        String name = cmbStaffName.getValue();

        try {
            //StaffDto staffDto = staffDAO.searchByName(name);
            StaffDto staffDto = staffBO.searchStaffByName(name);   // Using loose coupling
            lblStaffId.setText(staffDto.getStaffId());

            //appointmentDate.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void appointmentDateOnAction(ActionEvent actionEvent){
        appointmentTime.requestFocus();
    }

    @FXML
    void appointmentTimeOnAction(ActionEvent actionEvent){

        String appDate = appointmentDate.getValue().toString();
        String appTime = appointmentTime.getText();
        String staffId = lblStaffId.getText();

        try {
            //String appId = appointmentDAO.timeAvailabilityOrNot(appDate, appTime);
            String appId = appointmentBO.timeAvailabilityOrNot(appDate, appTime);  // Using loose coupling
            //System.out.println(appId);

            if(appId != null){

                //String st_id = staffAppointmentDetailDAO.selectStaffMemberId(appId);
                String st_id = staffAppointmentDetailBO.selectStaffMemberId(appId);    // Using loose coupling
                //System.out.println(st_id);

                if(st_id.equals(staffId)){
                    lblTimeAvailibility.setText("Not Available!!!");
                    lblInfo.setText("Please choose another time,date or staff member");
                }else{
                    lblTimeAvailibility.setText("Available");
                    btnServiceAddToTableOnAction(actionEvent);
                }

            }else{
                lblTimeAvailibility.setText("Available");
                btnServiceAddToTableOnAction(actionEvent);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAllAppointmentsOnAction(ActionEvent event) throws JRException, SQLException {

        reportBO.allAppointmentDetails();

    }

    @FXML
    void btnAppointmentInvoiceOnAction(ActionEvent event) throws JRException, SQLException {

        reportBO.invoiceOfAppointment();

    }

}
