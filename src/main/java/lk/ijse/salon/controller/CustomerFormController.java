package lk.ijse.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.CustomerBO;
import lk.ijse.salon.bo.custom.impl.CustomerBOImpl;
import lk.ijse.salon.dao.custom.CustomerDAO;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.salon.tm.CustomerTm;
import lk.ijse.salon.db.DbConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private AnchorPane customerForm;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private Button btnDelete;

    /*private CustomerDAO customerDAO = new CustomerDAOImpl();*/
    //-------------------------------------------------------------------------------------//
    //Since creating Business layer, we can't access DAO layer in controller class like above, so now want to access Business layer

    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);   //dependency injection ---> property injection

    private boolean validateCustomer() {

        //validate id
        String id = txtId.getText();
        boolean matchesId = Pattern.matches("[C][0-9]{3,}", id);
        if(!matchesId){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return false;
        }

        //validate name
        String name = txtName.getText();
        boolean matchesName = Pattern.matches("[A-Za-z\\s]{2,}[^!@%* .]", name);
        if(!matchesName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        //validate email
        String email = txtEmail.getText();
        boolean matchesEmail = Pattern.matches("[a-zA-Z0-9!@$%^&*()_+=;':\",./<>?|]{10,}" , email);
        if(!matchesEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            return false;
        }

        //validate customer tel
        String tel = txtTel.getText();
        boolean matchesTel = Pattern.matches("[0][0-9]{9}", tel);
        if(!matchesTel){
            new Alert(Alert.AlertType.ERROR, "Invalid Phone No").show();
            return false;
        }
        return true;
    }

    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
        tableListener();
        generateNextCustomerId();
    }

    private void generateNextCustomerId() {
        try {
            //String customerId = customerDAO.generateNextId();
            //Access the business layer
            String customerId = customerBO.generateNextCustomerId();   // Using loose coupling
            txtId.setText(customerId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void tableListener() {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(CustomerTm row) {
        txtId.setText(row.getId());
        txtName.setText(row.getName());
        txtEmail.setText(row.getEmail());
        txtTel.setText(row.getTel());
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllCustomer()  {

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            //List<CustomerDto> dtoList = customerDAO.getAll();
            List<CustomerDto> dtoList = customerBO.getAllCustomer();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getCusId(),
                                dto.getCusName(),
                                dto.getCusEmail(),
                                dto.getCusTel()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        btnDelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                if ((Pattern.matches("[C][0-9]{3,}", txtId.getText()))) {

                    String id = txtId.getText();

                    try {
                        //boolean isDeleted = customerDAO.delete(id);
                        boolean isDeleted = customerBO.deleteCustomer(id); // Using loose coupling
                        if (isDeleted) {
                            new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                            clearFields();
                            initialize();
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "customer not deleted!").show();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
                }
            }
        });
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean isValidated = validateCustomer();

        if(isValidated) {

            String id = txtId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            String tel = txtTel.getText();

            if (!id.isEmpty() && !name.isEmpty() && !email.isEmpty() && !tel.isEmpty()) {

                var dto = new CustomerDto(id, name, email, tel);

                try {
                    //boolean isSaved = customerDAO.save(dto);
                    boolean isSaved = customerBO.saveCustomer(dto);    // Using loose coupling

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        boolean isValidated = validateCustomer();

        if(isValidated) {

            String id = txtId.getText();
            String name = txtName.getText();
            String email = txtEmail.getText();
            String tel = txtTel.getText();

            if (!id.isEmpty() && !name.isEmpty() && !email.isEmpty() && !tel.isEmpty()) {

                var dto = new CustomerDto(id, name, email, tel);

                try {
                    //boolean isUpdated = customerDAO.update(dto);
                    boolean isUpdated = customerBO.updateCustomer(dto);    // Using loose coupling
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

        if ((Pattern.matches("[C][0-9]{3,}", txtId.getText()))) {

            String id = txtId.getText();

            try {
                //CustomerDto customerDto = customerDAO.search(id);
                CustomerDto customerDto = customerBO.searchCustomer(id);   // Using loose coupling

                if (customerDto != null) {
                    txtId.setText(customerDto.getCusId());
                    txtName.setText(customerDto.getCusName());
                    txtEmail.setText(customerDto.getCusEmail());
                    txtTel.setText(customerDto.getCusTel());
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "customer not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
        }
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtTel.clear();
    }
    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {
        btnSaveOnAction(event);
    }

}
