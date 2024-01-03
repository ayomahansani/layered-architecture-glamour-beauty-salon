package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.AppointmentBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.*;
import lk.ijse.salon.dao.custom.impl.*;
import lk.ijse.salon.db.DbConnection;
import lk.ijse.salon.dto.AppointmentDto;
import lk.ijse.salon.entity.Appointment;

import java.sql.Connection;
import java.sql.SQLException;

public class AppointmentBOImpl implements AppointmentBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private AppointmentDAO appointmentDAO = (AppointmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.APPOINTMENT);  //dependency injection ---> property injection
    private ServiceAppointmentDetailDAO serviceAppointmentDetailDAO = (ServiceAppointmentDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SERVICE_APPOINTMENT_DETAIL);   //dependency injection ---> property injection
    private StaffAppointmentDetailDAO staffAppointmentDetailDAO = (StaffAppointmentDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STAFF_APPOINTMENT_DETAIL); //dependency injection ---> property injection

    @Override
    public boolean confirmedAppointment(AppointmentDto dto) throws SQLException {

        //---------------Transaction-------------------

        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            //Want to convert the dto to an entity
            Appointment appointmentEntity = new Appointment(dto.getAppointmentId(), dto.getCustomerId(), dto.getAppDate(), dto.getAppTime(), dto.getAppAmount(), dto.getServiceId(), dto.getStaffId(), dto.getAppTmList());

            boolean isAppointmentSaved = appointmentDAO.save(appointmentEntity);    // Using loose coupling
            if(isAppointmentSaved){
                boolean isServiceAppointmentDetailSaved = serviceAppointmentDetailDAO.save(appointmentEntity);   // Using loose coupling
                if(isServiceAppointmentDetailSaved){
                    boolean isStaffAppointmentDetailSaved = staffAppointmentDetailDAO.save(appointmentEntity);   // Using loose coupling
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
    }

    @Override
    public String generateNextAppointmentId() throws SQLException {
        return appointmentDAO.generateNextId();
    }

    @Override
    public String timeAvailabilityOrNot(String appDate, String appTime) throws SQLException {
        return appointmentDAO.timeAvailabilityOrNot(appDate, appTime);
    }

    @Override
    public int setCurrentNumberOfAppointments() throws SQLException {
        return appointmentDAO.setCurrentNumber();
    }

    @Override
    public boolean deleteAppointment(String cusId) throws SQLException {
        return appointmentDAO.delete(cusId);
    }
}
