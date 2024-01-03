package lk.ijse.salon.dto;

import lk.ijse.salon.tm.AppointmentTm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDto {

    private String appointmentId;
    private String customerId;
    private String appDate;
    private String appTime;
    private double appAmount;
    private String serviceId;
    private String staffId;
    private List<AppointmentTm> appTmList = new ArrayList<>();


    public AppointmentDto() {
    }

    public AppointmentDto(String appointmentId, String customerId, String appDate, String appTime, double appAmount, String serviceId, String staffId, List<AppointmentTm> appTmList) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.appDate = appDate;
        this.appTime = appTime;
        this.appAmount = appAmount;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.appTmList = appTmList;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public double getAppAmount() {
        return appAmount;
    }

    public void setAppAmount(double appAmount) {
        this.appAmount = appAmount;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public List<AppointmentTm> getAppTmList() {
        return appTmList;
    }

    public void setAppTmList(List<AppointmentTm> appTmList) {
        this.appTmList = appTmList;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentId='" + appointmentId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", appDate='" + appDate + '\'' +
                ", appTime='" + appTime + '\'' +
                ", appAmount=" + appAmount +
                ", serviceId='" + serviceId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", appTmList=" + appTmList +
                '}';
    }
}