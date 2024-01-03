package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.PaymentDto;
import lk.ijse.salon.entity.Payment;

import java.sql.*;

public interface PaymentDAO extends CrudDAO<Payment> {

    /*boolean savePayment(PaymentDto paymentDto) throws SQLException;
    String generateNextPaymentId() throws SQLException;
    String splitPaymentId(String currentPaymentId);*/
    double getFullAmount() throws SQLException;

}
