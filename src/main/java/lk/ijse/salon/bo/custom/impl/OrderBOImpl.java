package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.OrderBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.OrderDAO;
import lk.ijse.salon.dao.custom.ProductDAO;
import lk.ijse.salon.dao.custom.ProductOrderDetailDAO;
import lk.ijse.salon.dao.custom.impl.OrderDAOImpl;
import lk.ijse.salon.dao.custom.impl.ProductDAOImpl;
import lk.ijse.salon.dao.custom.impl.ProductOrderDetailDAOImpl;
import lk.ijse.salon.db.DbConnection;
import lk.ijse.salon.dto.PlaceOrderDto;
import lk.ijse.salon.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER); //dependency injection ---> property injection
    private ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);   //dependency injection ---> property injection
    private ProductOrderDetailDAO productOrderDetailDAO = (ProductOrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT_ORDER_DETAIL);  //dependency injection ---> property injection


    @Override
    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException {

        //------------Transaction----------------
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            //Want to convert the dto to an entity
            PlaceOrder placeOrder = new PlaceOrder(placeOrderDto.getOrderId(), placeOrderDto.getDate(), placeOrderDto.getCustomerId(), placeOrderDto.getOrderNetTotal(), placeOrderDto.getCartTmList());

            boolean isOrderSaved = orderDAO.save(placeOrder);
            if (isOrderSaved) {
                boolean isUpdated = productDAO.updateProduct(placeOrderDto.getCartTmList());    // Using loose coupling
                if(isUpdated) {
                    boolean isOrderDetailSaved = productOrderDetailDAO.save(placeOrder); // Using loose coupling
                    if (isOrderDetailSaved) {
                        connection.commit();
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return true;
    }

    @Override
    public int setCurrentNumberOfOrders() throws SQLException {
        return orderDAO.setCurrentNumber();
    }

    @Override
    public boolean deleteOrder(String cusId) throws SQLException {
        return orderDAO.delete(cusId);
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return orderDAO.generateNextId();
    }

}
