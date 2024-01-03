package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.ServiceDAO;
import lk.ijse.salon.dto.ServiceDto;
import lk.ijse.salon.entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl implements ServiceDAO {

    @Override
    public boolean save(Service serviceEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO service VALUES(?, ?, ?, ?)",
                serviceEntity.getServiceId(),serviceEntity.getServiceName(),serviceEntity.getServiceType(),serviceEntity.getServiceAmount());
    }

    @Override
    public boolean update(Service serviceEntity) throws SQLException {

        return SQLUtill.execute("UPDATE service SET ser_name = ?, ser_type = ?, ser_unit_price = ? WHERE ser_id = ?",
                serviceEntity.getServiceName(),serviceEntity.getServiceType(),serviceEntity.getServiceAmount(),serviceEntity.getServiceId());
    }

    @Override
    public boolean delete(String id) throws SQLException {

        return SQLUtill.execute("DELETE FROM service WHERE ser_id = ?", id);
    }

    @Override
    public Service search(String id) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM service WHERE ser_id = ?", id);

        Service serviceEntity = null;

        if(resultSet.next()) {
            String ser_id = resultSet.getString(1);
            String ser_name = resultSet.getString(2);
            String ser_type = resultSet.getString(3);
            double ser_amount = resultSet.getDouble(4);

            serviceEntity = new Service(ser_id, ser_name, ser_type, ser_amount);
        }
        return serviceEntity;
    }

    @Override
    public Service searchByName(String name) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM service WHERE ser_name = ?", name);

        Service serviceEntity = null;

        if(resultSet.next()) {
            String ser_id = resultSet.getString(1);
            String ser_name = resultSet.getString(2);
            String ser_type = resultSet.getString(3);
            double ser_amount = resultSet.getDouble(4);

            serviceEntity = new Service(ser_id, ser_name, ser_type, ser_amount);
        }
        return serviceEntity;
    }

    @Override
    public List<Service> getAll() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM service");

        ArrayList<Service> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            Service serviceEntity = new Service(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
                    );

                    dtoList.add(serviceEntity);

        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT ser_id FROM service ORDER BY ser_id DESC LIMIT 1");

        if(resultSet.next()){
            String id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentServiceId) {
        if(currentServiceId != null) {
            String[] split = currentServiceId.split("Se0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "Se00" + id;
            }else {
                return "Se0" + id;
            }
        } else {
            return "SE001";
        }
    }

    @Override
    public int setCurrentNumber() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT COUNT(ser_id) FROM service");

        if(rst.next()){
            int noOfService = rst.getInt(1);
            return noOfService;
        }
        return 0;
    }
}
