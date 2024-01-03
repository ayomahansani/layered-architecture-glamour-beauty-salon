package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.PaymentDto;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO {

    String generateNextPaymentId() throws SQLException;
    boolean savePayment(PaymentDto paymentDto) throws SQLException;
    double getFullAmount() throws SQLException;
}
