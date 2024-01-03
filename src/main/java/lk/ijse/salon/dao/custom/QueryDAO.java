package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.SuperDAO;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    double getOrderAmount(String cusName) throws SQLException;
    double getAppointmentAmount(String cusName) throws SQLException;

}
