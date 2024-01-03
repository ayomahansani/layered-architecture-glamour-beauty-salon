package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;

import java.sql.SQLException;

public interface StaffAppointmentDetailBO extends SuperBO {
    String selectStaffMemberId(String appId) throws SQLException;

}
