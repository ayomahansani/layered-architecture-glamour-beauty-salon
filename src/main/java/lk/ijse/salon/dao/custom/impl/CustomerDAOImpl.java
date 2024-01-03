package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.CustomerDAO;
import lk.ijse.salon.dto.CustomerDto;
import lk.ijse.salon.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer customerEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO customer VALUES(?, ?, ?, ?)",
                customerEntity.getCusId(),customerEntity.getCusName(),customerEntity.getCusEmail(),customerEntity.getCusTel());
    }

    @Override
    public boolean delete(String id) throws SQLException {

        return SQLUtill.execute("DELETE FROM customer WHERE cus_id = ?", id);
    }

    @Override
    public boolean update(Customer customerEntity) throws SQLException {

        return SQLUtill.execute("UPDATE customer SET cus_name = ?, cus_email = ?, cus_tel = ? WHERE cus_id = ?",
                customerEntity.getCusName(),customerEntity.getCusEmail(),customerEntity.getCusTel(),customerEntity.getCusId());
    }

    @Override
    public Customer search(String id) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT * FROM customer WHERE cus_id = ?", id);

        Customer customerEntity = null;

        if(rst.next()) {
            String cus_id = rst.getString(1);
            String cus_name = rst.getString(2);
            String cus_email = rst.getString(3);
            String cus_tel = rst.getString(4);

            customerEntity = new Customer(cus_id, cus_name, cus_email, cus_tel);
        }
        return customerEntity;
    }

    @Override
    public List<Customer> getAll() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT * FROM customer");

        ArrayList<Customer> dtoList = new ArrayList<>();

        while(rst.next()) {
            dtoList.add(
                    new Customer(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT cus_id FROM customer ORDER BY cus_id DESC LIMIT 1");

        if(rst.next()){
            String id = rst.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentCustomerId) {
        if(currentCustomerId != null) {
            String[] split = currentCustomerId.split("C0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "C00" + id;
            }else {
                return "C0" + id;
            }
        } else {
            return "C001";
        }
    }

    @Override
    public Customer searchByName(String cusName) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT * FROM customer WHERE cus_name = ?", cusName);

        Customer customerEntity = null;

        if(rst.next()) {
            String cus_id = rst.getString(1);
            String cus_name = rst.getString(2);
            String cus_email = rst.getString(3);
            String cus_tel = rst.getString(4);

            customerEntity = new Customer(cus_id, cus_name, cus_email, cus_tel);
        }
        return customerEntity;
    }

    @Override
    public int setCurrentNumber() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT COUNT(cus_id) FROM customer");

        if(rst.next()){
            int noOfCustomer = rst.getInt(1);
            return noOfCustomer;
        }
        return 0;
    }

}
