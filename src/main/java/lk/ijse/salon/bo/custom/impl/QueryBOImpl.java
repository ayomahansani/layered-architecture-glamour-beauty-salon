package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.QueryBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.QueryDAO;
import lk.ijse.salon.dao.custom.impl.QueryDAOImpl;

import java.sql.SQLException;

public class QueryBOImpl implements QueryBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY); //dependency injection ---> property injection

    @Override
    public double getOrderAmount(String cusName) throws SQLException {
        return queryDAO.getOrderAmount(cusName);
    }

    @Override
    public double getAppointmentAmount(String cusName) throws SQLException {
        return queryDAO.getAppointmentAmount(cusName);
    }
}
