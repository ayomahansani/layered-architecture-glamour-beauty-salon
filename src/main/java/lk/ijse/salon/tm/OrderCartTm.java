package lk.ijse.salon.tm;

import javafx.scene.control.Button;

public class OrderCartTm {
    private String code;
    private String name;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button btn;

    public OrderCartTm() {
    }

    public OrderCartTm(String code, String name, int qty, double unitPrice, double tot, Button btn) {
        this.code = code;
        this.name = name;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.tot = tot;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "OrderCartTm{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", tot=" + tot +
                ", btn=" + btn +
                '}';
    }
}
