package lk.ijse.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.salon.bo.BOFactory;
import lk.ijse.salon.bo.custom.ProductBO;
import lk.ijse.salon.bo.custom.impl.ProductBOImpl;
import lk.ijse.salon.dao.custom.ProductDAO;
import lk.ijse.salon.dto.ProductDto;
import lk.ijse.salon.dao.custom.impl.ProductDAOImpl;
import lk.ijse.salon.tm.ProductTm;
import lk.ijse.salon.db.DbConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ProductFormController {

    @FXML
    private AnchorPane productForm;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtType;

    @FXML
    private TableView<ProductTm> tblProduct;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Button btnDelete;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);  //dependency injection ---> property injection*/

    private boolean validateProduct() {

        //validate id
        String id = txtCode.getText();
        boolean matchesId = Pattern.matches("[P][0-9]{3,}", id);
        if(!matchesId){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return false;
        }

        //validate name
        String name = txtName.getText();
        boolean matchesName = Pattern.matches("[\\w]{2,}", name);
        if(!matchesName){
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return false;
        }

        //validate type
        String type = txtType.getText();
        boolean matchesType = Pattern.matches("[^!@#$%\\d]{2,}", type);
        if(!matchesType){
            new Alert(Alert.AlertType.ERROR, "Invalid Type").show();
            return false;
        }

        //validate unit price
        String unitPrice = txtUnitPrice.getText();
        boolean matchesUnitPrice = Pattern.matches("[0-9.]{2,}[^!@%*]", unitPrice);
        if(!matchesUnitPrice){
            new Alert(Alert.AlertType.ERROR, "Invalid Unit Price").show();
            return false;
        }

        //validate qtyOnHand
        String qtyOnHand = txtQtyOnHand.getText();
        boolean matchesQtyOnHand = Pattern.matches("[0-9]{1,}", qtyOnHand);
        if(!matchesQtyOnHand){
            new Alert(Alert.AlertType.ERROR, "Invalid Qty On Hand").show();
            return false;
        }

        return true;
    }


    public void initialize() {
        //txtName.requestFocus();
        loadAllProducts();
        setCellValueFactory();
        tableListener();
        generateNextProductId();
    }

    private void generateNextProductId() {
        try {
            //String productId = productDAO.generateNextId();
            String productId = productBO.generateNextProductId();  // Using loose coupling
            txtCode.setText(productId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            if (newValue != null){
                setData(newValue);
            }
        });
    }

    private void setData(ProductTm row) {
        txtCode.setText(row.getCode());
        txtName.setText(row.getName());
        txtType.setText(row.getType());
        txtUnitPrice.setText(String.valueOf(row.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(row.getQtyOnHand()));
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void loadAllProducts() {

        ObservableList<ProductTm> obList = FXCollections.observableArrayList();
        try {
            //List<ProductDto> dtoList = productDAO.getAll();
            List<ProductDto> dtoList = productBO.getAllProducts();    // Using loose coupling

            for (ProductDto dto : dtoList) {
                obList.add(new ProductTm(
                        dto.getCode(),
                        dto.getName(),
                        dto.getType(),
                        dto.getUnitPrice(),
                        dto.getQtyOnHand()
                ));
            }
            tblProduct.setItems(obList);

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

                if ((Pattern.matches("[P][0-9]{3,}", txtCode.getText()))) {

                    String code = txtCode.getText();

                    //var model = new CustomerModel();
                    try {
                        //boolean isDeleted = productDAO.delete(code);
                        boolean isDeleted = productBO.deleteProduct(code); // Using loose coupling
                        if (isDeleted) {
                            new Alert(Alert.AlertType.CONFIRMATION, "product deleted!").show();
                            clearFields();
                            initialize();
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "product not deleted!").show();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }
                }
            }
        });
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean isValidated = validateProduct();

        if(isValidated) {

            String code = txtCode.getText();
            String name = txtName.getText();
            String type = txtType.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

            if (!code.isEmpty() && !name.isEmpty() && !type.isEmpty() && !(unitPrice == 0) && !(qtyOnHand == 0)) {

                var dto = new ProductDto(code, name, type, unitPrice, qtyOnHand);

//        var model = new ItemModel();
                try {
                    //boolean isSaved = productDAO.save(dto);
                    boolean isSaved = productBO.saveProduct(dto);  // Using loose coupling
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "product saved!").show();
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
    void btnUpdateOnAction(ActionEvent event) {

        boolean isValidated = validateProduct();

        if(isValidated) {

            String code = txtCode.getText();
            String name = txtName.getText();
            String type = txtType.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

            if (!code.isEmpty() && !name.isEmpty() && !type.isEmpty() && !(unitPrice == 0) && !(qtyOnHand == 0)) {

                ProductDto productDto = new ProductDto(code, name, type, unitPrice, qtyOnHand);

                try {
                    //boolean isUpdated = productDAO.update(productDto);
                    boolean isUpdated = productBO.updateProduct(productDto);   // Using loose coupling
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "product updated").show();
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
    void codeSearchOnAction(ActionEvent event) {

        if ((Pattern.matches("[P][0-9]{3,}", txtCode.getText()))) {

            String code = txtCode.getText();

            try {
                //ProductDto dto = productDAO.search(code);
                ProductDto dto = productBO.searchProduct(code);    // Using loose coupling
                if (dto != null) {
                    setFields(dto);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "item not found!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Code!").show();
        }

    }

    private void clearFields() {
        txtCode.clear();
        txtName.clear();
        txtType.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }

    private void setFields(ProductDto dto) {
        txtCode.setText(dto.getCode());
        txtName.setText(dto.getName());
        txtType.setText(dto.getType());
        txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtType.requestFocus();
    }

    @FXML
    void txtQtyOnHandOnAction(ActionEvent event) {
        btnSaveOnAction(event);
    }

    @FXML
    void txtTypeOnAction(ActionEvent event) {
        txtUnitPrice.requestFocus();
    }

    @FXML
    void txtUnitPriceOnAction(ActionEvent event) {
        txtQtyOnHand.requestFocus();
    }

}
