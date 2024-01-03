package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.StaffAppointmentDetailBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.StaffAppointmentDetailDAO;
import lk.ijse.salon.dao.custom.impl.StaffAppointmentDetailDAOImpl;

import java.sql.SQLException;

public class StaffAppointmentDetailBOImpl implements StaffAppointmentDetailBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private StaffAppointmentDetailDAO staffAppointmentDetailDAO = (StaffAppointmentDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STAFF_APPOINTMENT_DETAIL);  //dependency injection ---> property injection

    @Override
    public String selectStaffMemberId(String appId) throws SQLException {
        return staffAppointmentDetailDAO.selectStaffMemberId(appId);
    }
}
