package lk.ijse.salon.tm;

import javafx.scene.control.Button;

public class AppointmentTm {
    private String serId;
    private String serName;
    private double serAmount;
    private Button actionBtn;

    public AppointmentTm() {
    }

    public AppointmentTm(String serId, String serName, double serAmount, Button actionBtn) {
        this.serId = serId;
        this.serName = serName;
        this.serAmount = serAmount;
        this.actionBtn = actionBtn;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public double getSerAmount() {
        return serAmount;
    }

    public void setSerAmount(double serAmount) {
        this.serAmount = serAmount;
    }

    public Button getActionBtn() {
        return actionBtn;
    }

    public void setActionBtn(Button actionBtn) {
        this.actionBtn = actionBtn;
    }

    @Override
    public String toString() {
        return "AppointmentTm{" +
                "serId='" + serId + '\'' +
                ", serName='" + serName + '\'' +
                ", serAmount=" + serAmount +
                ", actionBtn=" + actionBtn +
                '}';
    }
}
