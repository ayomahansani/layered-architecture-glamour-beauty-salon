package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.AppointmentDto;
import lk.ijse.salon.entity.Appointment;

import java.sql.SQLException;

public interface StaffAppointmentDetailDAO extends CrudDAO<Appointment> {

    /*boolean saveStaffAppointmentDetail(AppointmentDto dto) throws SQLException;*/
    String selectStaffMemberId(String appId) throws SQLException;

}
