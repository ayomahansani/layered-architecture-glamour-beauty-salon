package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;

import java.sql.SQLException;

public interface QueryBO extends SuperBO {

    double getOrderAmount(String cusName) throws SQLException;
    double getAppointmentAmount(String cusName) throws SQLException;
}
