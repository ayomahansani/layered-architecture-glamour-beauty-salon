package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.OrderDAO;
import lk.ijse.salon.dao.custom.ProductDAO;
import lk.ijse.salon.dao.custom.ProductOrderDetailDAO;
import lk.ijse.salon.db.DbConnection;
import lk.ijse.salon.dto.PlaceOrderDto;
import lk.ijse.salon.entity.PlaceOrder;

import java.sql.*;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String generateNextId() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");

        if(rst.next()) {
            return splitId(rst.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("Or0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "Or00" + id;
            }else{
                return "Or0" + id;
            }
        } else {
            return "Or001";
        }
    }

    /*@Override
    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException {

        //------------Transaction----------------
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = save(placeOrderDto);
            if (isOrderSaved) {
                boolean isUpdated = productDAO.updateProduct(placeOrderDto.getCartTmList());    // Using loose coupling
                if(isUpdated) {
                    boolean isOrderDetailSaved = productOrderDetailDAO.save(placeOrderDto); // Using loose coupling
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return true;
    }*/

    @Override
    public boolean save(PlaceOrder placeOrderEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO orders VALUES(?, ?, ?, ?)",
                placeOrderEntity.getOrderId(),placeOrderEntity.getCustomerId(),placeOrderEntity.getDate(),placeOrderEntity.getOrderNetTotal());
    }

    @Override
    public int setCurrentNumber() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT COUNT(order_id) FROM orders");

        if(rst.next()){
            int noOfOrders = rst.getInt(1);
            return noOfOrders;
        }
        return 0;
    }

    @Override
    public boolean delete(String cusId) throws SQLException {

        return SQLUtill.execute("DELETE FROM orders WHERE cus_id = ?", cusId);
    }

    @Override
    public boolean update(PlaceOrder placeOrderEntity) throws SQLException {
        return false;
    }

    @Override
    public PlaceOrder searchByName(String name) throws SQLException {
        return null;
    }

    @Override
    public PlaceOrder search(String id) throws SQLException {
        return null;
    }

    @Override
    public List<PlaceOrder> getAll() throws SQLException {
        return null;
    }
}
