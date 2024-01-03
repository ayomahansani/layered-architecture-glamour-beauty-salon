package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.ServiceDto;
import lk.ijse.salon.entity.Service;

import java.sql.SQLException;
import java.util.List;

public interface ServiceDAO extends CrudDAO<Service> {

    /*boolean saveService(ServiceDto dto) throws SQLException;
    boolean updateService(ServiceDto dto) throws SQLException;
    boolean deleteService(String id) throws SQLException;
    ServiceDto searchService(String id) throws SQLException;
    List<ServiceDto> getAllServices() throws SQLException;
    String generateNextServiceId() throws SQLException;
    String splitServiceId(String currentServiceId);
    ServiceDto searchServiceByName(String name) throws SQLException;
    int setNoOfServices() throws SQLException;*/

}
