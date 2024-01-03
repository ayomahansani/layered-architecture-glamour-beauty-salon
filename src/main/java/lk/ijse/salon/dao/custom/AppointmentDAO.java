package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.AppointmentDto;
import lk.ijse.salon.entity.Appointment;

import java.sql.*;


public interface AppointmentDAO extends CrudDAO<Appointment> {

    /*boolean saveAppointment(AppointmentDto dto) throws SQLException;
    boolean deleteAppointmentAfterPayment(String cusId) throws SQLException;
    String generateNextAppointmentId() throws SQLException;
    String splitAppointmentId(String currentAppId);
    int setNoOfAppointments() throws SQLException;*/
    //boolean confirmedAppointment(AppointmentDto appointmentDto) throws SQLException;
    String timeAvailabilityOrNot(String appDate, String appTime) throws SQLException;

}
