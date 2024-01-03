package lk.ijse.salon.bo.custom.impl;

import javafx.fxml.FXML;
import lk.ijse.salon.bo.custom.ReportBO;
import lk.ijse.salon.db.DbConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;

public class ReportBOImpl implements ReportBO {

    @Override
    public JasperPrint invoiceOfAppointment() throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/invoice_of_appointment.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    @Override
    public JasperPrint allAppointmentDetails() throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_appointment_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    @Override
    public JasperPrint invoiceOfPayment() throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/invoice_of_payment.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    @Override
    public JasperPrint allPaymentDetails() throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_payment_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    public JasperPrint allOrdersView() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_orders_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    public JasperPrint allServicesView() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_services_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    public JasperPrint allStaffView() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_staff_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    public JasperPrint allProductsView() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_products_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    public JasperPrint allCustomersView() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/all_customers_details.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                compileReport, //compiled report
                null,
                DbConnection.getInstance().getConnection() //database connection
        );
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }
}
