package lk.ijse.salon.tm;

import java.time.LocalDate;
import java.time.LocalTime;

public class PaymentTm {

    private String payId;
    private String cusName;
    private LocalDate payDate;
    private LocalTime payTime;
    private double appAmount;
    private double orderAmount;
    private double totAmount;

    public PaymentTm() {
    }

    public PaymentTm(String payId, String cusName, LocalDate payDate, LocalTime payTime, double appAmount, double orderAmount, double totAmount) {
        this.payId = payId;
        this.cusName = cusName;
        this.payDate = payDate;
        this.payTime = payTime;
        this.appAmount = appAmount;
        this.orderAmount = orderAmount;
        this.totAmount = totAmount;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public LocalTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalTime payTime) {
        this.payTime = payTime;
    }

    public double getAppAmount() {
        return appAmount;
    }

    public void setAppAmount(double appAmount) {
        this.appAmount = appAmount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(double totAmount) {
        this.totAmount = totAmount;
    }

    @Override
    public String toString() {
        return "PaymentTm{" +
                "payId='" + payId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", payDate=" + payDate +
                ", payTime=" + payTime +
                ", appAmount=" + appAmount +
                ", orderAmount=" + orderAmount +
                ", totAmount=" + totAmount +
                '}';
    }
}
