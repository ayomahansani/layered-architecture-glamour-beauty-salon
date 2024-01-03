package lk.ijse.salon.dto;

import java.sql.Date;
import java.time.LocalDate;

public class PaymentDto {

    private String payId;
    private String cusId;
    private double fullAmount;
    private LocalDate payDate;
    private String payTime;

    public PaymentDto() {
    }

    public PaymentDto(String payId, String cusId, double fullAmount, LocalDate payDate, String payTime) {
        this.payId = payId;
        this.cusId = cusId;
        this.fullAmount = fullAmount;
        this.payDate = payDate;
        this.payTime = payTime;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public double getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(double fullAmount) {
        this.fullAmount = fullAmount;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "payId='" + payId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", fullAmount=" + fullAmount +
                ", payDate=" + payDate +
                ", payTime='" + payTime + '\'' +
                '}';
    }
}
