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
import lk.ijse.salon.bo.custom.CustomerBO;
import lk.ijse.salon.bo.custom.OrderBO;
import lk.ijse.salon.bo.custom.ProductBO;
import lk.ijse.salon.bo.custom.impl.CustomerBOImpl;
import lk.ijse.salon.bo.custom.impl.OrderBOImpl;
import lk.ijse.salon.bo.custom.impl.ProductBOImpl;
import lk.ijse.salon.dao.custom.CustomerDAO;
import lk.ijse.salon.dao.custom.OrderDAO;
import lk.ijse.salon.dao.custom.ProductDAO;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.dto.PlaceOrderDto;
import lk.ijse.salon.dto.ProductDto;
import lk.ijse.salon.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.salon.dao.custom.impl.OrderDAOImpl;
import lk.ijse.salon.dao.custom.impl.ProductDAOImpl;
import lk.ijse.salon.tm.OrderCartTm;
import lk.ijse.salon.db.DbConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class PlaceOrderFormController {

    @FXML
    private Button btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbCustomerName;

    @FXML
    private JFXComboBox<String> cmbProductName;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colProductCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCode;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane orderForm;

    @FXML
    private TableView<OrderCartTm> tblOrderCart;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblProductAvailable;


    //For loose coupling assigned to super type interface(BO interface)
    //Using Factory design pattern to hide object creation
    private CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);   //dependency injection ---> property injection
    private ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);  //dependency injection ---> property injection
    private OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);    //dependency injection ---> property injection

    private ObservableList<OrderCartTm> obList = FXCollections.observableArrayList();

    private boolean validateOrderQTY() {

        //validate qty
        String qty = txtQty.getText();
        boolean matchesUserName = Pattern.matches("[0-9]{1,}", qty);
        if(!matchesUserName){
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity").show();
            return false;
        }
        return true;
    }

    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerNames();
        loadProductsNames();
    }

    private void setCellValueFactory() {
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            //String orderId = orderDAO.generateNextId();
            String orderId = orderBO.generateNextOrderId();    // Using loose coupling
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadProductsNames() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            //List<ProductDto> productDtos = productDAO.getAll();
            List<ProductDto> productDtos = productBO.getAllProducts();    // Using loose coupling

            for (ProductDto dto : productDtos) {
                obList.add(dto.getName());
            }

            cmbProductName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private void setDate() {
//        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

        boolean isValidated = validateOrderQTY();

        if(isValidated) {

            String name = cmbProductName.getValue();
            String code = lblCode.getText();
            int qty = Integer.valueOf(txtQty.getText());
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double tot = unitPrice * qty;
            Button btn = new Button("Remove");

            setRemoveBtnAction(btn);
            btn.setCursor(Cursor.HAND);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                    if (colProductCode.getCellData(i).equals(code)) {
                        int col_qty = (int) colQty.getCellData(i);
                        qty += col_qty;
                        tot = unitPrice * qty;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTot(tot);

                        calculateTotal();
                        tblOrderCart.refresh();
                        return;
                    }
                }
            }
            var cartTm = new OrderCartTm(code, name, qty, unitPrice, tot, btn);

            obList.add(cartTm);

            tblOrderCart.setItems(obList);
            calculateTotal();
            txtQty.clear();
        }
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblOrderCart.refresh();
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
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
    void btnPlaceOrderOnAction(ActionEvent event) {

        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerName = cmbCustomerName.getValue();
        double orderNetTotal = Double.valueOf(lblNetTotal.getText());

        if(!orderId.isEmpty() && !customerName.isEmpty() && !(orderNetTotal == 0.0)) {

            String customerId = null;
            try {
                //CustomerDto customer = customerDAO.searchByName(customerName);
                CustomerDto customer = customerBO.searchCustomerByName(customerName);  // Using loose coupling
                customerId = customer.getCusId();

                List<OrderCartTm> cartTmList = new ArrayList<>();

                for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                    OrderCartTm cartTm = tblOrderCart.getItems().get(i);
                    cartTmList.add(cartTm);
                }

                //System.out.println(cartTmList);

                var placeOrderDto = new PlaceOrderDto(orderId, date, customerId, orderNetTotal, cartTmList);
                try {
                    //boolean isSuccess = orderDAO.placeOrder(placeOrderDto);
                    boolean isSuccess = orderBO.placeOrder(placeOrderDto); // Using loose coupling
                    if (isSuccess) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Please Fill All Fields!").show();
        }
    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {
        String name = cmbProductName.getValue();

        try {
            //ProductDto dto = productDAO.searchByName(name);
            ProductDto dto = productBO.searchProductByName(name);  // Using loose coupling
            lblCode.setText(dto.getCode());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));

            if(dto.getQtyOnHand() > 0) {
                txtQty.requestFocus();
            }else{
                lblProductAvailable.setText("Not Available!!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
}
