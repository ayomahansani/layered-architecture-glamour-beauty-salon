package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.LoginBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.LoginDAO;
import lk.ijse.salon.dao.custom.impl.LoginDAOImpl;
import lk.ijse.salon.dto.LoginDto;
import lk.ijse.salon.entity.Login;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN); //dependency injection ---> property injection

    @Override
    public boolean checkUserName(String userName) throws SQLException {
        return loginDAO.checkUserName(userName);
    }

    @Override
    public boolean updatePassword(LoginDto dto) throws SQLException {
        return loginDAO.update(new Login(dto.getUserName(), dto.getPassword()));    //Want to convert the dto to an entity
    }

    @Override
    public boolean checkCredential(LoginDto dto) throws SQLException {
        return loginDAO.checkCredential(new Login(dto.getUserName(), dto.getPassword()));    //Want to convert the dto to an entity
    }

    @Override
    public boolean saveSignup(String fullName, String email, String password) throws SQLException {
        return loginDAO.saveSignup(fullName,email,password);
    }
}
