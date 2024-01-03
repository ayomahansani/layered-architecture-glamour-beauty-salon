package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.StaffDto;

import java.sql.SQLException;
import java.util.List;

public interface StaffBO extends SuperBO {

    StaffDto searchStaffByName(String name) throws SQLException;
    int setCurrentNumberOfStaff() throws SQLException;
    String generateNextStaffId() throws SQLException;
    List<StaffDto> getAllStaff() throws SQLException;
    boolean deleteStaffMember(String id) throws SQLException;
    boolean saveStaffMember(StaffDto dto) throws SQLException;
    boolean updateStaffMember(StaffDto dto) throws SQLException;
    StaffDto searchStaffMember(String id) throws SQLException;
}
