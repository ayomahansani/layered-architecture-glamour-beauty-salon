package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.ProductOrderDetailDAO;
import lk.ijse.salon.dto.PlaceOrderDto;
import lk.ijse.salon.entity.PlaceOrder;
import lk.ijse.salon.tm.OrderCartTm;

import java.sql.SQLException;
import java.util.List;

public class ProductOrderDetailDAOImpl implements ProductOrderDetailDAO {

    @Override
    public boolean save(PlaceOrder placeOrderEntity) throws SQLException {

        List<OrderCartTm> cartTmList = placeOrderEntity.getCartTmList();
        for(OrderCartTm tm : cartTmList) {
            SQLUtill.execute("INSERT INTO product_order_detail VALUES(?, ?, ?, ?)",
                    placeOrderEntity.getOrderId(),tm.getCode(),tm.getQty(),tm.getUnitPrice());
        }
        return true;
    }

    @Override
    public boolean update(PlaceOrder placeOrderEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public PlaceOrder search(String id) throws SQLException {
        return null;
    }

    @Override
    public List<PlaceOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentCustomerId) {
        return null;
    }

    @Override
    public PlaceOrder searchByName(String name) throws SQLException {
        return null;
    }

    @Override
    public int setCurrentNumber() throws SQLException {
        return 0;
    }
}
