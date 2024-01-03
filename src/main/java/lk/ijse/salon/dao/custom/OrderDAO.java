package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.PlaceOrderDto;
import lk.ijse.salon.entity.PlaceOrder;

import java.sql.*;
import java.time.LocalDate;

public interface OrderDAO extends CrudDAO<PlaceOrder> {

    /*boolean saveOrder(PlaceOrderDto placeOrderDto) throws SQLException;
    boolean deleteOrderAfterPayment(String cusId) throws SQLException;
    String generateNextOrderId() throws SQLException;
    String splitOrderId(String currentOrderId);
    int setNoOfOrders() throws SQLException;*/

    //boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException;

}
