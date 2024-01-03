package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.ServiceDto;

import java.sql.SQLException;
import java.util.List;

public interface ServiceBO extends SuperBO {

    ServiceDto searchServiceByName(String name) throws SQLException;
    int setCurrentNumberOfServices() throws SQLException;
    List<ServiceDto> getAllServices() throws SQLException;
    String generateNextServiceId() throws SQLException;
    boolean deleteService(String id) throws SQLException;
    boolean saveService(ServiceDto dto) throws SQLException;
    boolean updateService(ServiceDto dto) throws SQLException;
    ServiceDto searchService(String id) throws SQLException;
}
