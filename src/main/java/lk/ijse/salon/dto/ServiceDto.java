package lk.ijse.salon.dto;

public class ServiceDto {
    private String serviceId;
    private String serviceName;
    private String serviceType;
    private double serviceAmount;


    public ServiceDto() {
    }

    public ServiceDto(String serviceId, String serviceName, String serviceType, double serviceAmount) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.serviceAmount = serviceAmount;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    @Override
    public String toString() {
        return "ServiceDto{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", serviceAmount=" + serviceAmount +
                '}';
    }
}
