package lk.ijse.salon.bo;

import lk.ijse.salon.bo.custom.impl.*;

public class BOFactory {    // Using Factory Design Pattern  --> For hide the object creation part

    private static BOFactory boFactory;

    private BOFactory(){
    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public enum BOTypes{    // Enumeration --> Represents the group of contents
        APPOINTMENT,CUSTOMER,LOGIN,ORDER,PAYMENT,PRODUCT,QUERY,SERVICE,STAFF_APPOINTMENT_DETAIL,STAFF,REPORT
    }

    public SuperBO getBO(BOTypes boTypes){  // return type must be the most super(SuperBO)

        switch (boTypes){
            case APPOINTMENT:
                return new AppointmentBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case QUERY:
                return new QueryBOImpl();   // must be implemented SuperBO interface directly
            case SERVICE:
                return new ServiceBOImpl();
            case STAFF_APPOINTMENT_DETAIL:
                return new StaffAppointmentDetailBOImpl();
            case STAFF:
                return new StaffBOImpl();
            case REPORT:
                return new ReportBOImpl();  // must be implemented SuperBO interface directly
            default:
                return null;
        }
    }
}
