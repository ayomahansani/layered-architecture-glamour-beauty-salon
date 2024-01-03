package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.CustomerBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.CustomerDAO;
import lk.ijse.salon.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);    //dependency injection ---> property injection

    @Override
    public String generateNextCustomerId() throws SQLException {
        return customerDAO.generateNextId();    // Using loose coupling
    }

    @Override
    public List<CustomerDto> getAllCustomer() throws SQLException {

        List<Customer> customers = customerDAO.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        for(Customer c : customers){
            customerDtos.add(new CustomerDto(c.getCusId(),c.getCusName(),c.getCusEmail(),c.getCusTel()));   //Want to convert the entity to a dto
        }
        return customerDtos;

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);  // Using loose coupling
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.save(new Customer(dto.getCusId(), dto.getCusName(), dto.getCusEmail(), dto.getCusTel()));   //Want to convert the dto to an entity
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCusId(), dto.getCusName(), dto.getCusEmail(), dto.getCusTel()));  //Want to convert the dto to an entity
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException {

        Customer customer = customerDAO.search(id);
        return new CustomerDto(customer.getCusId(),customer.getCusName(),customer.getCusEmail(),customer.getCusTel());  //Want to convert the entity to a dto
    }

    @Override
    public CustomerDto searchCustomerByName(String name) throws SQLException {

        Customer customer = customerDAO.searchByName(name);
        return new CustomerDto(customer.getCusId(),customer.getCusName(),customer.getCusEmail(),customer.getCusTel());
    }

    @Override
    public int setCurrentNumberOfCustomers() throws SQLException {
        return customerDAO.setCurrentNumber();
    }
}
