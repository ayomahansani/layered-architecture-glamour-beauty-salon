package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.LoginDto;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {

    boolean checkUserName(String userName) throws SQLException;
    boolean updatePassword(LoginDto dto) throws SQLException;
    boolean checkCredential(LoginDto loginDto) throws SQLException;
    boolean saveSignup(String fullName, String email, String password) throws SQLException;
}
