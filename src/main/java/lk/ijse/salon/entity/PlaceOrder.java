package lk.ijse.salon.entity;

import lk.ijse.salon.tm.OrderCartTm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrder {

    private String orderId;
    private LocalDate date;
    private String customerId;
    private double orderNetTotal;
    private List<OrderCartTm> cartTmList = new ArrayList<>();

    public PlaceOrder() {
    }

    public PlaceOrder(String orderId, LocalDate date, String customerId, double orderNetTotal, List<OrderCartTm> cartTmList) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.orderNetTotal = orderNetTotal;
        this.cartTmList = cartTmList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getOrderNetTotal() {
        return orderNetTotal;
    }

    public void setOrderNetTotal(double orderNetTotal) {
        this.orderNetTotal = orderNetTotal;
    }

    public List<OrderCartTm> getCartTmList() {
        return cartTmList;
    }

    public void setCartTmList(List<OrderCartTm> cartTmList) {
        this.cartTmList = cartTmList;
    }

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", orderNetTotal=" + orderNetTotal +
                ", cartTmList=" + cartTmList +
                '}';
    }
}
