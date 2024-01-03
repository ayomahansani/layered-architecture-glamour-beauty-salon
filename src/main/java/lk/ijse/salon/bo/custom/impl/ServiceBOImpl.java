package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.ServiceBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.ServiceDAO;
import lk.ijse.salon.dao.custom.impl.ServiceDAOImpl;
import lk.ijse.salon.dto.ServiceDto;
import lk.ijse.salon.entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBOImpl implements ServiceBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private ServiceDAO serviceDAO = (ServiceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SERVICE);   //dependency injection ---> property injection

    @Override
    public ServiceDto searchServiceByName(String name) throws SQLException {
        Service service = serviceDAO.searchByName(name);
        //Want to convert the entity to a dto
        return new ServiceDto(service.getServiceId(),service.getServiceName(),service.getServiceType(),service.getServiceAmount());
    }

    @Override
    public int setCurrentNumberOfServices() throws SQLException {
        return serviceDAO.setCurrentNumber();
    }

    @Override
    public List<ServiceDto> getAllServices() throws SQLException {

        List<Service> services = serviceDAO.getAll();
        List<ServiceDto> serviceDtos = new ArrayList<>();

        //Want to convert the entity to a dto
        for(Service service : services){
            serviceDtos.add(new ServiceDto(service.getServiceId(),service.getServiceName(),service.getServiceType(),service.getServiceAmount()));
        }

        return serviceDtos;
    }

    @Override
    public String generateNextServiceId() throws SQLException {
        return serviceDAO.generateNextId();
    }

    @Override
    public boolean deleteService(String id) throws SQLException {
        return serviceDAO.delete(id);
    }

    @Override
    public boolean saveService(ServiceDto dto) throws SQLException {
        //Want to convert the dto to an entity
        return serviceDAO.save(new Service(dto.getServiceId(), dto.getServiceName(), dto.getServiceType(), dto.getServiceAmount()));
    }

    @Override
    public boolean updateService(ServiceDto dto) throws SQLException {
        //Want to convert the dto to an entity
        return serviceDAO.update(new Service(dto.getServiceId(), dto.getServiceName(), dto.getServiceType(), dto.getServiceAmount()));
    }

    @Override
    public ServiceDto searchService(String id) throws SQLException {
        Service service = serviceDAO.search(id);
        //Want to convert the entity to a dto
        return new ServiceDto(service.getServiceId(),service.getServiceName(),service.getServiceType(),service.getServiceAmount());
    }
}
