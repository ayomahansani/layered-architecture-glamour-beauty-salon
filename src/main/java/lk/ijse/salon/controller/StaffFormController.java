package lk.ijse.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.StaffBO;
import lk.ijse.salon.bo.custom.impl.StaffBOImpl;
import lk.ijse.salon.dao.custom.StaffDAO;
import lk.ijse.salon.dto.StaffDto;
import lk.ijse.salon.dao.custom.impl.StaffDAOImpl;
import lk.ijse.salon.tm.StaffTm;
import lk.ijse.salon.db.DbConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StaffFormController {

    @FXML
    private AnchorPane staffForm;

    @FXML
    private TextField txtStaffId;

    @FXML
    private TextField txtStaffName;

    @FXML
    private TextField txtStaffEmail;

    @FXML
    private TextField txtStaffType;

    @FXML
    private TableView<StaffTm> tblStaff;

    @FXML
    private TableColumn<?, ?> colStaffId;

    @FXML
    private TableColumn<?, ?> colStaffName;

    @FXML
    private TableColumn<?, ?> colStaffAddress;

    @FXML
    private TableColumn<?, ?> colStaffEmail;

    @FXML
    private TableColumn<?, ?> colStaffType;

    @FXML
    private TableColumn<?, ?> colStaffTel;

    @FXML
    private TextField txtStaffAddress;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnDelete;

    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private StaffBO staffBO = (StaffBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STAFF);    //dependency injection ---> property injection*/

    private boolean validateStaffMember() {

        //validate id
        String id = txtStaffId.getText();
        boolean matchesId = Pattern.matches("[S][0-9]{3,}", id);
        if(!matchesId){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return false;
        }

        //validate name
        String name = txtStaffName.getText();
        boolean matchesName = Pattern.matches("[A-Za-z\\s]{2,}[^!@%* .]", name);
        if(!matchesName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        //validate address
        String address = txtStaffAddress.getText();
        boolean matchesAddress = Pattern.matches("[A-Za-z]{2,}[^!@%* .\\d]", address);
        if(!matchesAddress){
            new Alert(Alert.AlertType.ERROR, "Invalid Address").show();
            return false;
        }

        //validate email
        String email = txtStaffEmail.getText();
        boolean matchesEmail = Pattern.matches("[a-zA-Z0-9!@$%^&*()_+=;':\",./<>?|]{10,}" , email);
        if(!matchesEmail){
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            return false;
        }

        //validate tel
        String tel = txtPhone.getText();
        boolean matchesTel = Pattern.matches("[0][0-9]{9}", tel);
        if(!matchesTel){
            new Alert(Alert.AlertType.ERROR, "Invalid Phone No").show();
            return false;
        }

        //validate type
        String type = txtStaffType.getText();
        boolean matchesType = Pattern.matches("[A-Za-z\\s]{2,}[^!@%* .]", type);
        if(!matchesType){
            new Alert(Alert.AlertType.ERROR, "Invalid Type").show();
            return false;
        }
        return true;
    }

    public void initialize(){
        txtStaffName.requestFocus();
        setCellValueFactory();
        loadAllStaff();
        tableListener();
        generateNextStaffId();
    }

    private void generateNextStaffId() {
        try {
            //String staffId = staffDAO.generateNextId();
            String staffId = staffBO.generateNextStaffId();    // Using loose coupling
            txtStaffId.setText(staffId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblStaff.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(StaffTm row) {
        txtStaffId.setText(row.getStaffId());
        txtStaffName.setText(row.getStaffName());
        txtStaffAddress.setText(row.getStaffAddress());
        txtStaffEmail.setText(row.getStaffEmail());
        txtStaffType.setText(row.getStaffType());
        txtPhone.setText(row.getStaffTel());
    }


    private void setCellValueFactory() {
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        colStaffAddress.setCellValueFactory(new PropertyValueFactory<>("staffAddress"));
        colStaffEmail.setCellValueFactory(new PropertyValueFactory<>("staffEmail"));
        colStaffType.setCellValueFactory(new PropertyValueFactory<>("staffType"));
        colStaffTel.setCellValueFactory(new PropertyValueFactory<>("staffTel"));
    }

    private void loadAllStaff()  {

        ObservableList<StaffTm> obList = FXCollections.observableArrayList();

        try {
            //List<StaffDto> dtoList = staffDAO.getAll();
            List<StaffDto> dtoList = staffBO.getAllStaff();    // Using loose coupling

            for (StaffDto dto : dtoList) {
                obList.add(
                        new StaffTm(
                                dto.getStaffId(),
                                dto.getStaffName(),
                                dto.getStaffAddress(),
                                dto.getStaffEmail(),
                                dto.getStaffType(),
                                dto.getStaffTel()
                        )
                );
            }

            tblStaff.setItems(obList);
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

                if ((Pattern.matches("[S][0-9]{3,}", txtStaffId.getText()))) {

                    String id = txtStaffId.getText();

                    //var model = new StaffModel();
                    try {
                        //boolean isDeleted = staffDAO.delete(id);
                        boolean isDeleted = staffBO.deleteStaffMember(id);   // Using loose coupling
                        if (isDeleted) {
                            new Alert(Alert.AlertType.CONFIRMATION, "staff member deleted!").show();
                            clearFields();
                            initialize();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Id!").show();
                }
            }
        });
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean isValidated = validateStaffMember();

        if(isValidated) {

            String id = txtStaffId.getText();
            String name = txtStaffName.getText();
            String address = txtStaffAddress.getText();
            String email = txtStaffEmail.getText();
            String tel = txtPhone.getText();
            String type = txtStaffType.getText();

            if (!id.isEmpty() && !name.isEmpty() && !address.isEmpty() && !email.isEmpty() && !tel.isEmpty() && !type.isEmpty()) {

                var dto = new StaffDto(id, name, address, email, tel, type);

                try {
                    //boolean isSaved = staffDAO.save(dto);
                    boolean isSaved = staffBO.saveStaffMember(dto);  // Using loose coupling

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "new staff member added!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
        }
    }

    private void clearFields() {
        txtStaffId.setText("");
        txtStaffName.setText("");
        txtStaffAddress.setText("");
        txtStaffEmail.setText("");
        txtPhone.setText("");
        txtStaffType.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        boolean isValidated = validateStaffMember();

        if(isValidated) {

            String id = txtStaffId.getText();
            String name = txtStaffName.getText();
            String address = txtStaffAddress.getText();
            String email = txtStaffEmail.getText();
            String tel = txtPhone.getText();
            String type = txtStaffType.getText();

            if (!id.isEmpty() && !name.isEmpty() && !address.isEmpty() && !email.isEmpty() && !tel.isEmpty() && !type.isEmpty()) {

                var dto = new StaffDto(id, name, address, email, tel, type);

                //var model = new StaffModel();
                try {
                    //boolean isUpdated = staffDAO.update(dto);
                    boolean isUpdated = staffBO.updateStaffMember(dto);  // Using loose coupling
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "staff member updated!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

        if ((Pattern.matches("[S][0-9]{3,}", txtStaffId.getText()))) {

            String id = txtStaffId.getText();

            try {
                //StaffDto staffDto = staffDAO.search(id);
                StaffDto staffDto = staffBO.searchStaffMember(id);   // Using loose coupling

                if (staffDto != null) {
                    txtStaffId.setText(staffDto.getStaffId());
                    txtStaffName.setText(staffDto.getStaffName());
                    txtStaffAddress.setText(staffDto.getStaffAddress());
                    txtStaffEmail.setText(staffDto.getStaffEmail());
                    txtPhone.setText(staffDto.getStaffTel());
                    txtStaffType.setText(staffDto.getStaffType());
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "staff member not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
        }
    }

    @FXML
    void txtPhoneOnAction(ActionEvent event) {
        btnSaveOnAction(event);
    }

    @FXML
    void txtStaffAddressOnAction(ActionEvent event) {
        txtStaffEmail.requestFocus();
    }

    @FXML
    void txtStaffEmailOnAction(ActionEvent event) {
        txtStaffType.requestFocus();
    }

    @FXML
    void txtStaffNameOnAction(ActionEvent event) {
        txtStaffAddress.requestFocus();
    }

    @FXML
    void txtStaffTypeOnAction(ActionEvent event) {
        txtPhone.requestFocus();
    }
}
