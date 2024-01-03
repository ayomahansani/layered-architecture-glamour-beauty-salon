package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {

    /*boolean saveCustomer(CustomerDto dto) throws SQLException;
    boolean updateCustomer(CustomerDto dto) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    CustomerDto searchCustomer(String id) throws SQLException;
    List<CustomerDto> getAllCustomer() throws SQLException;
    String generateNextCustomerId() throws SQLException;
    String splitCustomerId(String currentCustomerId);
    CustomerDto searchCustomerByName(String cusName) throws SQLException;
    int setNoOfCustomers() throws SQLException;*/

}
