package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

    String generateNextCustomerId() throws SQLException;
    List<CustomerDto> getAllCustomer() throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    boolean saveCustomer(CustomerDto dto) throws SQLException;
    boolean updateCustomer(CustomerDto dto) throws SQLException;
    CustomerDto searchCustomer(String id) throws SQLException;
    CustomerDto searchCustomerByName(String name) throws SQLException;
    int setCurrentNumberOfCustomers() throws SQLException;
}
