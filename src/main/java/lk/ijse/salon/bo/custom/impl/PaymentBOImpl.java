package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.PaymentBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.PaymentDAO;
import lk.ijse.salon.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.salon.dto.PaymentDto;
import lk.ijse.salon.entity.Payment;

import java.sql.SQLException;

public class PaymentBOImpl implements PaymentBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);   //dependency injection ---> property injection

    @Override
    public String generateNextPaymentId() throws SQLException {
        return paymentDAO.generateNextId();
    }

    @Override
    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        //Want to convert the dto to an entity
        return paymentDAO.save(new Payment(paymentDto.getPayId(),paymentDto.getCusId(),paymentDto.getFullAmount(),paymentDto.getPayDate(),paymentDto.getPayTime()));
    }

    @Override
    public double getFullAmount() throws SQLException {
        return paymentDAO.getFullAmount();
    }
}
