package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.AppointmentDAO;
import lk.ijse.salon.dao.custom.ServiceAppointmentDetailDAO;
import lk.ijse.salon.dao.custom.StaffAppointmentDetailDAO;
import lk.ijse.salon.db.DbConnection;
import lk.ijse.salon.dto.AppointmentDto;
import lk.ijse.salon.entity.Appointment;

import java.sql.*;
import java.util.List;


public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public String generateNextId() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT app_id FROM appointment ORDER BY app_id DESC LIMIT 1");

        if(rst.next()){
            String id = rst.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentAppId) {
        if(currentAppId != null) {
            String[] split = currentAppId.split("A0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "A00" + id;
            }else{
                return "A0" + id;
            }
        } else {
            return "A001";
        }
    }

    /*@Override
    public boolean confirmedAppointment(AppointmentDto appointmentDto) throws SQLException {

        //---------------Transaction-------------------

        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isAppointmentSaved = appointmentDAO.save(appointmentDto);    // Using loose coupling
            if(isAppointmentSaved){
                boolean isServiceAppointmentDetailSaved = serviceAppointmentDetailDAO.save(appointmentDto);   // Using loose coupling
                if(isServiceAppointmentDetailSaved){
                    boolean isStaffAppointmentDetailSaved = staffAppointmentDetailDAO.save(appointmentDto);   // Using loose coupling
                    if(isStaffAppointmentDetailSaved){
                        connection.commit();
                    }
                }
            }

        } catch (SQLException e) {
            connection.rollback();
        }
        finally {
            connection.setAutoCommit(true);
        }

        return true;
    }*/

    @Override
    public boolean save(Appointment appointmentEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO appointment VALUES(?, ?, ?, ?, ?)",
                appointmentEntity.getAppointmentId(),appointmentEntity.getCustomerId(),appointmentEntity.getAppDate(),appointmentEntity.getAppTime(),appointmentEntity.getAppAmount());
    }

    @Override
    public String timeAvailabilityOrNot(String appDate, String appTime) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT app_id FROM appointment WHERE app_date = ? AND app_time = ?", appDate,appTime);

        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public int setCurrentNumber() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT COUNT(app_id) FROM appointment");

        if(rst.next()){
            int noOfAppointments = rst.getInt(1);
            return noOfAppointments;
        }
        return 0;
    }

    @Override
    public boolean delete(String cusId) throws SQLException {

        return SQLUtill.execute("DELETE FROM appointment WHERE cus_id = ?", cusId);
    }

    @Override
    public boolean update(Appointment appointmentEntity) throws SQLException {
        return false;
    }

    @Override
    public Appointment search(String id) throws SQLException {
        return null;
    }

    @Override
    public List<Appointment> getAll() throws SQLException {
        return null;
    }

    @Override
    public Appointment searchByName(String name) throws SQLException {
        return null;
    }

}
