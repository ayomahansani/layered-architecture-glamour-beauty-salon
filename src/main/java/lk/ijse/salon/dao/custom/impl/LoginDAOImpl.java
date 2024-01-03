package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.LoginDAO;
import lk.ijse.salon.dto.LoginDto;
import lk.ijse.salon.dto.SignupDto;
import lk.ijse.salon.entity.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {

    @Override
    public boolean update(Login loginEntity) throws SQLException {

        return SQLUtill.execute("UPDATE user SET password = ? WHERE user_name = ?",
                loginEntity.getPassword(), loginEntity.getUserName());
    }

    @Override
    public boolean checkCredential(Login loginEntity) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT * FROM user");

        while (rst.next()){

            String userName = rst.getString("user_name");
            String password = rst.getString("password");

            if(loginEntity.getUserName().equals(userName)){
                if (loginEntity.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkUserName(String userName) throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT user_name FROM user");

        while ((rst.next())){
            String email =  rst.getString(1);
            if(email.equals(userName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean saveSignup(String fullName,String email,String password) throws SQLException {

        return SQLUtill.execute("INSERT INTO user VALUES(?,?,?)", fullName, email, password);
    }

    @Override
    public boolean save(Login loginEntity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Login search(String id) throws SQLException {
        return null;
    }

    @Override
    public List<Login> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentCustomerId) {
        return null;
    }

    @Override
    public Login searchByName(String name) throws SQLException {
        return null;
    }

    @Override
    public int setCurrentNumber() throws SQLException {
        return 0;
    }
}
