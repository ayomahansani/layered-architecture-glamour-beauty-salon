package lk.ijse.salon.dao;

import lk.ijse.salon.dao.custom.impl.*;


public class DAOFactory {   // Using Factory Design Pattern  --> For hide the object creation part

    private static DAOFactory daoFactory;

    private DAOFactory(){
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{   // Enumeration --> Represents the group of contents
        APPOINTMENT,CUSTOMER,LOGIN,ORDER,PAYMENT,PRODUCT,PRODUCT_ORDER_DETAIL,QUERY,SERVICE_APPOINTMENT_DETAIL,SERVICE,STAFF_APPOINTMENT_DETAIL,STAFF
    }

    public SuperDAO getDAO(DAOTypes daoTypes){  // return type must be the most super(SuperDAO)

        switch (daoTypes){
            case APPOINTMENT:
                return new AppointmentDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case PRODUCT_ORDER_DETAIL:
                return new ProductOrderDetailDAOImpl();
            case QUERY:
                return new QueryDAOImpl();  // must be implemented SuperDAO interface directly
            case SERVICE_APPOINTMENT_DETAIL:
                return new ServiceAppointmentDetailDAOImpl();
            case SERVICE:
                return new ServiceDAOImpl();
            case STAFF_APPOINTMENT_DETAIL:
                return new StaffAppointmentDetailDAOImpl();
            case STAFF:
                return new StaffDAOImpl();
            default:
                return null;
        }
    }

}
