package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.PlaceOrderDto;
import lk.ijse.salon.entity.PlaceOrder;
import lk.ijse.salon.tm.OrderCartTm;
import java.sql.SQLException;
import java.util.List;

public interface ProductOrderDetailDAO extends CrudDAO<PlaceOrder> {

    /*boolean saveOrderDetails(PlaceOrderDto placeOrderDto) throws SQLException;*/

}
