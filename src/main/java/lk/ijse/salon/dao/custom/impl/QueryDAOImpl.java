package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public double getOrderAmount(String cusName) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT o.order_amount FROM orders o JOIN customer c on o.cus_id = c.cus_id WHERE c.cus_name = ?", cusName);

        if(rst.next()){
            double orderAmount = rst.getDouble(1);
            return orderAmount;
        }
        return 0;
    }

    @Override
    public double getAppointmentAmount(String cusName) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT a.app_amount FROM appointment a JOIN customer c on a.cus_id = c.cus_id WHERE c.cus_name = ?", cusName);

        if(rst.next()){
            double appAmount = rst.getDouble(1);
            return appAmount;
        }
        return 0;
    }
}
