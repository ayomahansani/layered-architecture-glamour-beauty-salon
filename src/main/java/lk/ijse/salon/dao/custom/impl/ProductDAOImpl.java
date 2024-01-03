package lk.ijse.salon.dao.custom.impl;

import lk.ijse.salon.dao.SQLUtill;
import lk.ijse.salon.dao.custom.ProductDAO;
import lk.ijse.salon.dto.ProductDto;
import lk.ijse.salon.entity.Product;
import lk.ijse.salon.tm.OrderCartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean save(Product productEntity) throws SQLException {

        return SQLUtill.execute("INSERT INTO product VALUES(?, ?, ?, ?, ?)",
                productEntity.getCode(),productEntity.getName(),productEntity.getType(),productEntity.getUnitPrice(),productEntity.getQtyOnHand());
    }

    @Override
    public boolean update(Product productEntity) throws SQLException {

        return SQLUtill.execute("UPDATE product SET pr_name = ?, pr_type = ?, pr_unit_price = ?, pr_qty_on_hand = ? WHERE pr_id = ?",
                productEntity.getName(),productEntity.getType(),productEntity.getUnitPrice(),productEntity.getQtyOnHand(),productEntity.getCode());
    }

    @Override
    public Product search(String code) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM product WHERE pr_id = ?", code);

        Product productEntity = null;

        if(resultSet.next()) {
            productEntity = new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );
        }
        return productEntity;
    }

    @Override
    public boolean delete(String code) throws SQLException {

        return SQLUtill.execute("DELETE FROM product WHERE pr_id = ?", code);
    }

    @Override
    public List<Product> getAll() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM product");

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            productList.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            ));
        }
        return productList;
    }

    @Override
    public boolean updateProduct(List<OrderCartTm> cartTmList) throws SQLException {
        for(OrderCartTm tm : cartTmList) {
            return SQLUtill.execute("UPDATE product SET pr_qty_on_hand = pr_qty_on_hand - ? WHERE pr_id = ?", tm.getQty(), tm.getCode());
        }
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT pr_id FROM product ORDER BY pr_id DESC LIMIT 1");

        if(resultSet.next()){
            String id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentProductId) {
        if(currentProductId != null) {
            String[] split = currentProductId.split("P0");
            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "P00" + id;
            }else {
                return "P0" + id;
            }
        } else {
            return "P001";
        }
    }

    @Override
    public int setCurrentNumber() throws SQLException {

        ResultSet rst = SQLUtill.execute("SELECT COUNT(pr_id) FROM product");

        if(rst.next()){
            int noOfProduct = rst.getInt(1);
            return noOfProduct;
        }
        return 0;
    }

    @Override
    public Product searchByName(String name) throws SQLException {

        ResultSet resultSet = SQLUtill.execute("SELECT * FROM product WHERE pr_name = ?", name);

        Product productEntity = null;

        if(resultSet.next()) {
            productEntity = new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );
        }
        return productEntity;
    }
}
