package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.PaymentDAO;
import lk.ijse.salon.dto.PaymentDto;
import lk.ijse.salon.entity.Payment;

import java.sql.*;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public String generateNextId() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");

        if(rst.next()){
            String id = rst.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentPaymentId) {
        if(currentPaymentId != null) {
            String[] split = currentPaymentId.split("Pay0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "Pay00" + id;
            }else {
                return "Pay0" + id;
            }
        } else {
            return "Pay001";
        }
    }

    @Override
    public boolean save(Payment paymentEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO payment VALUES(?, ?, ?, ?, ?)",
                paymentEntity.getPayId(),paymentEntity.getCusId(),paymentEntity.getFullAmount(),paymentEntity.getPayDate(),paymentEntity.getPayTime());
    }

    @Override
    public double getFullAmount() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT pay_amount FROM payment ORDER BY pay_id DESC LIMIT 1");

        if(rst.next()){
            double fullAmount = rst.getDouble(1);
            return fullAmount;
        }
        return 0;
    }

    @Override
    public Payment searchByName(String name) throws SQLException {
        return null;
    }

    @Override
    public int setCurrentNumber() throws SQLException {
        return 0;
    }

    @Override
    public boolean update(Payment paymentEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Payment search(String id) throws SQLException {
        return null;
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        return null;
    }

}
