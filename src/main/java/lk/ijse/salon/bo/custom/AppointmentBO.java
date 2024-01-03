package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.AppointmentDto;

import java.sql.SQLException;

public interface AppointmentBO extends SuperBO {

    String generateNextAppointmentId() throws SQLException;
    boolean confirmedAppointment(AppointmentDto appointmentDto) throws SQLException;
    String timeAvailabilityOrNot(String appDate, String appTime) throws SQLException;
    int setCurrentNumberOfAppointments() throws SQLException;
    boolean deleteAppointment(String cusId) throws SQLException;
}
