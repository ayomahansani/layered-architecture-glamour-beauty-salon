package lk.ijse.salon.bo.custom.impl;

import lk.ijse.salon.bo.custom.ProductBO;
import lk.ijse.salon.dao.DAOFactory;
import lk.ijse.salon.dao.custom.ProductDAO;
import lk.ijse.salon.dao.custom.impl.ProductDAOImpl;
import lk.ijse.salon.dto.ProductDto;
import lk.ijse.salon.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    //Access the DAO layer
    //For loose coupling assigned to super type interface(DAO interface)
    //Using Factory design pattern to hide object creation
    private ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);    //dependency injection ---> property injection

    @Override
    public int setCurrentNumberOfProducts() throws SQLException {
        return productDAO.setCurrentNumber();
    }

    @Override
    public List<ProductDto> getAllProducts() throws SQLException {

        List<Product> products = productDAO.getAll();
        List<ProductDto> productDtos = new ArrayList<>();

        //Want to convert the entity to a dto
        for(Product product : products){
            productDtos.add(new ProductDto(product.getCode(),product.getName(),product.getType(),product.getUnitPrice(),product.getQtyOnHand()));
        }

        return productDtos;
    }

    @Override
    public ProductDto searchProductByName(String name) throws SQLException {
        Product product = productDAO.searchByName(name);
        //Want to convert the entity to a dto
        return new ProductDto(product.getCode(),product.getName(),product.getType(),product.getUnitPrice(),product.getQtyOnHand());
    }

    @Override
    public String generateNextProductId() throws SQLException {
        return productDAO.generateNextId();
    }

    @Override
    public boolean deleteProduct(String code) throws SQLException {
        return productDAO.delete(code);
    }

    @Override
    public boolean saveProduct(ProductDto dto) throws SQLException {
        //Want to convert the dto to an entity
        return productDAO.save(new Product(dto.getCode(), dto.getName(), dto.getType(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public boolean updateProduct(ProductDto dto) throws SQLException {
        //Want to convert the dto to an entity
        return productDAO.update(new Product(dto.getCode(), dto.getName(), dto.getType(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public ProductDto searchProduct(String code) throws SQLException {
        Product product = productDAO.search(code);
        //Want to convert the entity to a dto
        return new ProductDto(product.getCode(),product.getName(),product.getType(),product.getUnitPrice(),product.getQtyOnHand());
    }
}
