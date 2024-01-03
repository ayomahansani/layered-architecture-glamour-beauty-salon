package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.SQLException;

public interface ReportBO extends SuperBO {
    JasperPrint invoiceOfAppointment() throws SQLException, JRException;
    JasperPrint allAppointmentDetails() throws SQLException, JRException;
    JasperPrint invoiceOfPayment() throws SQLException, JRException;
    JasperPrint allPaymentDetails() throws SQLException, JRException;
    JasperPrint allOrdersView() throws SQLException, JRException;
    JasperPrint allServicesView() throws SQLException, JRException;
    JasperPrint allStaffView() throws SQLException, JRException;
    JasperPrint allProductsView() throws SQLException, JRException;
    JasperPrint allCustomersView() throws SQLException, JRException;

}
