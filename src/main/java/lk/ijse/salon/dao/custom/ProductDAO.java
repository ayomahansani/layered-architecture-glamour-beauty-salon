package lk.ijse.salon.dao.custom;

import lk.ijse.salon.dao.CrudDAO;
import lk.ijse.salon.dto.ProductDto;
import lk.ijse.salon.entity.Product;
import lk.ijse.salon.tm.OrderCartTm;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO extends CrudDAO<Product> {

    /*boolean saveProduct(ProductDto productDto) throws SQLException;
    boolean updateProduct(ProductDto productDto) throws SQLException;
    boolean deleteProduct(String code) throws SQLException;
    ProductDto searchProduct(String code) throws SQLException;
    List<ProductDto> loadAllProducts() throws SQLException;
    String generateNextProductId() throws SQLException;
    String splitProductId(String currentProductId);
    ProductDto searchProductByName(String name) throws SQLException;
    int setNoOfProducts() throws SQLException;*/
    boolean updateProduct(List<OrderCartTm> cartTmList) throws SQLException;

}
