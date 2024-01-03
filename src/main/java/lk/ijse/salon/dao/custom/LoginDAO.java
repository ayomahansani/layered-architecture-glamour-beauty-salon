package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.LoginDto;
import lk.ijse.salon.dto.SignupDto;
import lk.ijse.salon.entity.Login;

import java.sql.SQLException;

public interface LoginDAO extends CrudDAO<Login> {

    /*boolean updatePassword(LoginDto loginDto) throws SQLException;*/
    boolean checkCredential(Login loginEntity) throws SQLException;
    boolean checkUserName(String userName) throws SQLException;
    boolean saveSignup(String fullName,String email,String password) throws SQLException;

}
