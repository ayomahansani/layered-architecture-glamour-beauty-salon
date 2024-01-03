package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.StaffDAO;
import lk.ijse.salon.dto.StaffDto;
import lk.ijse.salon.entity.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAOImpl implements StaffDAO {

    @Override
    public boolean save(Staff staffEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO staff VALUES(?, ?, ?, ?, ?, ?)",
                staffEntity.getStaffId(),staffEntity.getStaffName(),staffEntity.getStaffAddress(),staffEntity.getStaffEmail(),staffEntity.getStaffTel(),staffEntity.getStaffType());
    }

    @Override
    public boolean update(Staff staffEntity) throws SQLException {

        return SQLUtill.execute("UPDATE staff SET st_name = ?, st_address = ?, st_email = ?, st_tel = ?, st_type = ? WHERE st_id = ?",
                staffEntity.getStaffName(),staffEntity.getStaffAddress(),staffEntity.getStaffEmail(),staffEntity.getStaffTel(),staffEntity.getStaffType(),staffEntity.getStaffId());
    }

    @Override
    public Staff search(String id) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM staff WHERE st_id = ?", id);

        Staff staffEntity = null;

        if(resultSet.next()) {
            String st_id = resultSet.getString(1);
            String st_name = resultSet.getString(2);
            String st_address = resultSet.getString(3);
            String st_email = resultSet.getString(4);
            String st_tel = resultSet.getString(5);
            String st_type = resultSet.getString(6);

            staffEntity = new Staff(st_id, st_name, st_address, st_email, st_tel, st_type);
        }
        return staffEntity;
    }

    @Override
    public boolean delete(String id) throws SQLException {

        return SQLUtill.execute("DELETE FROM staff WHERE st_id = ?", id);
    }

    @Override
    public Staff searchByName(String name) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM staff WHERE st_name = ?", name);

        Staff staffEntity = null;

        if(resultSet.next()) {
            String st_id = resultSet.getString(1);
            String st_name = resultSet.getString(2);
            String st_address = resultSet.getString(3);
            String st_email = resultSet.getString(4);
            String st_tel = resultSet.getString(5);
            String st_type = resultSet.getString(6);

            staffEntity = new Staff(st_id, st_name, st_address, st_email, st_tel, st_type);
        }
        return staffEntity;
    }

    @Override
    public List<Staff> getAll() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM staff");

        ArrayList<Staff> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Staff(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT st_id FROM staff ORDER BY st_id DESC LIMIT 1");

        if(resultSet.next()){
            String id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentStaffId) {
        if(currentStaffId != null) {
            String[] split = currentStaffId.split("S0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "S00" + id;
            }else {
                return "S0" + id;
            }
        } else {
            return "S001";
        }
    }

    @Override
    public int setCurrentNumber() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT COUNT(st_id) FROM staff");

        if(resultSet.next()){
            int noOfStaff = resultSet.getInt(1);
            return noOfStaff;
        }
        return 0;
    }
}
