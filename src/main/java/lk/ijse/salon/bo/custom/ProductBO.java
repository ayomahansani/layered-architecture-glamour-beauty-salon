package lk.ijse.salon.bo.custom;

import lk.ijse.salon.bo.SuperBO;
import lk.ijse.salon.dto.ProductDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductBO extends SuperBO {

    int setCurrentNumberOfProducts() throws SQLException;
    List<ProductDto> getAllProducts() throws SQLException;
    ProductDto searchProductByName(String name) throws SQLException;
    String generateNextProductId() throws SQLException;
    boolean deleteProduct(String code) throws SQLException;
    boolean saveProduct(ProductDto dto) throws SQLException;
    boolean updateProduct(ProductDto dto) throws SQLException;
    ProductDto searchProduct(String code) throws SQLException;
}
