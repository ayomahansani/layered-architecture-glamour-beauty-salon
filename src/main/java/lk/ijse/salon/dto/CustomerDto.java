package lk.ijse.salon.dto;

public class CustomerDto {
    private String cusId;
    private String cusName;
    private String cusEmail;
    private String cusTel;

    public CustomerDto() {
    }

    public CustomerDto(String cusId, String cusName, String cusEmail, String cusTel) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusEmail = cusEmail;
        this.cusTel = cusTel;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusTel() {
        return cusTel;
    }

    public void setCusTel(String cusTel) {
        this.cusTel = cusTel;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "cusId='" + cusId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusTel='" + cusTel + '\'' +
                '}';
    }
}
