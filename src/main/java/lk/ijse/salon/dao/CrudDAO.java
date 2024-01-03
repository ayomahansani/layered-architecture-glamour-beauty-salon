package lk.ijse.salon.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{   // Created for less boilerplate codes -->

    boolean save(T dto) throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    T search(String id) throws SQLException;
    List<T> getAll() throws SQLException;
    String generateNextId() throws SQLException;
    String splitId(String currentCustomerId);
    T searchByName(String name) throws SQLException;
    int setCurrentNumber() throws SQLException;
}
