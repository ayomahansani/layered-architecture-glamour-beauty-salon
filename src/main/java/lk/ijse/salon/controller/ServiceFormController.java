package lk.ijse.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.ServiceBO;
import lk.ijse.salon.bo.custom.impl.ServiceBOImpl;
import lk.ijse.salon.dao.custom.ServiceDAO;
import lk.ijse.salon.dto.ServiceDto;
import lk.ijse.salon.dao.custom.impl.ServiceDAOImpl;
import lk.ijse.salon.tm.ServiceTm;
import lk.ijse.salon.db.DbConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ServiceFormController {

    @FXML
    private AnchorPane serviceForm;

    @FXML
    private TextField txtSerId;

    @FXML
    private TextField txtSerType;

    @FXML
    private TextField txtSerName;

    @FXML
    private TextField txtSerAmount;

    @FXML
    private TableView<ServiceTm> tblServices;

    @FXML
    private TableColumn<?, ?> colServiceId;

    @FXML
    private TableColumn<?, ?> colServiceName;

    @FXML
    private TableColumn<?, ?> colServiceType;

    @FXML
    private TableColumn<?, ?> colServiceAmount;

    @FXML
    private Button btnDelete;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private ServiceBO serviceBO = (ServiceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SERVICE);  //dependency injection ---> property injection


    private boolean validateService() {

        //validate id
        String id = txtSerId.getText();
        boolean matchesId = Pattern.matches("(Se)[0-9]{3,}", id);
        if(!matchesId){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return false;
        }

        //validate name
        String name = txtSerName.getText();
        boolean matchesName = Pattern.matches("[^!@#$%]{2,}", name);
        if(!matchesName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        //validate type
        String type = txtSerType.getText();
        boolean matchesType = Pattern.matches("[^!@#$%]{2,}", type);
        if(!matchesType){
            new Alert(Alert.AlertType.ERROR, "Invalid Type").show();
            return false;
        }

        //validate unit price
        String unitPrice = txtSerAmount.getText();
        boolean matchesUnitPrice = Pattern.matches("[0-9.]{2,}[^!@%*]", unitPrice);
        if(!matchesUnitPrice){
            new Alert(Alert.AlertType.ERROR, "Invalid Unit Price").show();
            return false;
        }

        return true;
    }

    public void initialize(){
        //txtSerName.requestFocus();
        generateNextServiceId();
        setCellValueFactory();
        loadAllServices();
        tableListener();
    }

    private void generateNextServiceId() {
        try {
            //String serviceId = serviceDAO.generateNextId();
            String serviceId = serviceBO.generateNextServiceId();  // Using loose coupling
            txtSerId.setText(serviceId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblServices.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(ServiceTm row) {
        txtSerId.setText(row.getServiceId());
        txtSerName.setText(row.getServiceName());
        txtSerType.setText(row.getServiceType());
        txtSerAmount.setText(String.valueOf(row.getServiceAmount()));
    }

    private void setCellValueFactory() {
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colServiceAmount.setCellValueFactory(new PropertyValueFactory<>("serviceAmount"));
    }

    private void loadAllServices()  {

        ObservableList<ServiceTm> obList = FXCollections.observableArrayList();

        List<ServiceDto> dtoList = null;
        try {
            //dtoList = serviceDAO.getAll();
            dtoList = serviceBO.getAllServices();  // Using loose coupling

            for (ServiceDto dto : dtoList) {
                obList.add(
                        new ServiceTm(
                                dto.getServiceId(),
                                dto.getServiceName(),
                                dto.getServiceType(),
                                dto.getServiceAmount()
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblServices.setItems(obList);

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

                if ((Pattern.matches("(Se)[0-9]{3,}", txtSerId.getText()))) {

                    String id = txtSerId.getText();

                    //var model = new ServiceModel();
                    boolean isDeleted = false;
                    try {
                        //isDeleted = serviceDAO.delete(id);
                        isDeleted = serviceBO.deleteService(id);   // Using loose coupling
                        if (isDeleted) {
                            new Alert(Alert.AlertType.CONFIRMATION, "service deleted!").show();
                            clearFields();
                            initialize();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
                }
            }
        });
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean isValidated = validateService();

        if(isValidated) {

            String id = txtSerId.getText();
            String name = txtSerName.getText();
            String type = txtSerType.getText();
            double amount = Double.parseDouble(txtSerAmount.getText());

            if (!id.isEmpty() && !name.isEmpty() && !type.isEmpty() && !(amount == 0)) {

                var dto = new ServiceDto(id, name, type, amount);

                boolean isSaved = false;
                try {
                    //isSaved = serviceDAO.save(dto);
                    isSaved = serviceBO.saveService(dto);  // Using loose coupling
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "service added!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
        }
    }

    private void clearFields() {
        txtSerId.clear();
        txtSerName.clear();
        txtSerType.clear();
        txtSerAmount.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        boolean isValidated = validateService();

        if(isValidated) {

            String id = txtSerId.getText();
            String name = txtSerName.getText();
            String type = txtSerType.getText();
            double amount = Double.parseDouble(txtSerAmount.getText());

            if (!id.isEmpty() && !name.isEmpty() && !type.isEmpty() && !(amount == 0)) {

                var dto = new ServiceDto(id, name, type, amount);

                boolean isUpdated = false;
                try {
                    //isUpdated = serviceDAO.update(dto);
                    isUpdated = serviceBO.updateService(dto);  // Using loose coupling
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "service updated!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            }
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

        if ((Pattern.matches("(Se)[0-9]{3,}", txtSerId.getText()))) {

            String id = txtSerId.getText();

            // var model = new ServiceModel();
            ServiceDto serviceDto = null;
            try {
                //serviceDto = serviceDAO.search(id);
                serviceDto = serviceBO.searchService(id);  // Using loose coupling

                //System.out.println(serviceDto);
                if (serviceDto != null) {
                    txtSerId.setText(serviceDto.getServiceId());
                    txtSerName.setText(serviceDto.getServiceName());
                    txtSerType.setText(serviceDto.getServiceType());
                    txtSerAmount.setText(String.valueOf(serviceDto.getServiceAmount()));
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "service not found").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
        }
    }

    @FXML
    void txtSerAmountOnAction(ActionEvent event) {
        btnSaveOnAction(event);
    }

    @FXML
    void txtSerNameOnAction(ActionEvent event) {
        txtSerType.requestFocus();
    }

    @FXML
    void txtSerTypeOnAction(ActionEvent event) {
        txtSerAmount.requestFocus();
    }


}
