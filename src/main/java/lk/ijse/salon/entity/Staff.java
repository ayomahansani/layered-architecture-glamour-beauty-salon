package lk.ijse.salon.entity;

public class Staff {

    private String staffId;
    private String staffName;
    private String staffAddress;
    private String staffEmail;
    private String staffTel;
    private String staffType;

    public Staff() {
    }

    public Staff(String staffId, String staffName, String staffAddress, String staffEmail, String staffTel, String staffType) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffAddress = staffAddress;
        this.staffEmail = staffEmail;
        this.staffTel = staffTel;
        this.staffType = staffType;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }


    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                ", staffTel='" + staffTel + '\'' +
                ", staffType='" + staffType + '\'' +
                '}';
    }
}
