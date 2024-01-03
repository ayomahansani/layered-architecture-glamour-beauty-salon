package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.StaffBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.StaffDAO;
import lk.ijse.salon.dao.custom.impl.StaffDAOImpl;
import lk.ijse.salon.dto.StaffDto;
import lk.ijse.salon.entity.Staff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffBOImpl implements StaffBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private StaffDAO staffDAO = (StaffDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STAFF); //dependency injection ---> property injection

    @Override
    public StaffDto searchStaffByName(String name) throws SQLException {
        Staff staff = staffDAO.searchByName(name);
        //Want to convert the entity to a dto
        return new StaffDto(staff.getStaffId(),staff.getStaffName(),staff.getStaffAddress(),staff.getStaffEmail(),staff.getStaffTel(),staff.getStaffType());
    }

    @Override
    public int setCurrentNumberOfStaff() throws SQLException {
        return staffDAO.setCurrentNumber();
    }

    @Override
    public String generateNextStaffId() throws SQLException {
        return staffDAO.generateNextId();
    }

    @Override
    public List<StaffDto> getAllStaff() throws SQLException {

        List<Staff> staffs = staffDAO.getAll();
        List<StaffDto> staffDtos = new ArrayList<>();

        //Want to convert the entity to a dto
        for(Staff staff : staffs){
            staffDtos.add(new StaffDto(staff.getStaffId(),staff.getStaffName(),staff.getStaffAddress(),staff.getStaffEmail(),staff.getStaffTel(),staff.getStaffType()));
        }
        return staffDtos;
    }

    @Override
    public boolean deleteStaffMember(String id) throws SQLException {
        return staffDAO.delete(id);
    }

    @Override
    public boolean saveStaffMember(StaffDto dto) throws SQLException {
        //Want to convert the dto to an entity
        return staffDAO.save(new Staff(dto.getStaffId(), dto.getStaffName(), dto.getStaffAddress(), dto.getStaffEmail(), dto.getStaffTel(), dto.getStaffType()));
    }

    @Override
    public boolean updateStaffMember(StaffDto dto) throws SQLException {
        //Want to convert the dto to an entity
        return staffDAO.update(new Staff(dto.getStaffId(), dto.getStaffName(), dto.getStaffAddress(), dto.getStaffEmail(), dto.getStaffTel(), dto.getStaffType()));
    }

    @Override
    public StaffDto searchStaffMember(String id) throws SQLException {
        Staff staff = staffDAO.search(id);
        //Want to convert the entity to a dto
        return new StaffDto(staff.getStaffId(),staff.getStaffName(),staff.getStaffAddress(),staff.getStaffEmail(),staff.getStaffTel(),staff.getStaffType());
    }
}
