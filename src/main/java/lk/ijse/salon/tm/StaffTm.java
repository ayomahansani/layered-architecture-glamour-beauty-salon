package lk.ijse.salon.tm;

public class StaffTm {
    private String staffId;
    private String staffName;
    private String staffAddress;
    private String staffEmail;
    private String staffType;
    private String staffTel;

    public StaffTm() {
    }

    public StaffTm(String staffId, String staffName, String staffAddress, String staffEmail, String staffType, String staffTel) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffAddress = staffAddress;
        this.staffEmail = staffEmail;
        this.staffType = staffType;
        this.staffTel = staffTel;
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

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    @Override
    public String toString() {
        return "StaffTm{" +
                "staffId='" + staffId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                ", staffType='" + staffType + '\'' +
                ", staffTel='" + staffTel + '\'' +
                '}';
    }
}
