package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.StaffAppointmentDetailDAO;
import lk.ijse.salon.dto.AppointmentDto;
import lk.ijse.salon.entity.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StaffAppointmentDetailDAOImpl implements StaffAppointmentDetailDAO {

    @Override
    public boolean save(Appointment appointmentEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO staff_app_detail VALUES(?, ?)",
                appointmentEntity.getAppointmentId(),appointmentEntity.getStaffId());
    }

    @Override
    public String selectStaffMemberId(String appId) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT st_id FROM staff_app_detail WHERE app_id = ?", appId);

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public boolean update(Appointment appointmentEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
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
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentCustomerId) {
        return null;
    }

    @Override
    public Appointment searchByName(String name) throws SQLException {
        return null;
    }

    @Override
    public int setCurrentNumber() throws SQLException {
        return 0;
    }

}
