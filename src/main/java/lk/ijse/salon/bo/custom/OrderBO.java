package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.PlaceOrderDto;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {

    int setCurrentNumberOfOrders() throws SQLException;
    boolean deleteOrder(String cusId) throws SQLException;
    String generateNextOrderId() throws SQLException;
    boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException;
}
