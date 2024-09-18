package com.pos.point_of_sales.populators;


import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Purchase;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class PurchasePopulator {

    private Purchase purchase ;
    private  ProductModel productModel;
    private SupplierModel supplierModel;
    private Supplier supplier;
    public Purchase populatePurchase(ResultSet resultSet) throws SQLException
    {
         purchase = new Purchase();
         productModel = new ProductModel();
         supplierModel = new SupplierModel();
         supplier = new Supplier();

        purchase.setId(resultSet.getLong("id"));

        Long productId = resultSet.getLong("productId");
        Product product = productModel.getProduct(productId);
        if (Objects.nonNull(product))
        {
            purchase.setProduct(product);
        }

        Long supplierId = resultSet.getLong("supplierId");
       Supplier supplier = supplierModel.getSupplier(supplierId);
        if (Objects.nonNull(supplier))
        {
            purchase.setSupplier(supplier);
        }
        purchase.setQuantity(resultSet.getDouble("quantity"));
        purchase.setPrice(resultSet.getDouble("price"));
        purchase.setTotal(resultSet.getDouble("total"));
        purchase.setDatetime(resultSet.getDate("datetime"));
        return purchase;
    }

    public  PreparedStatement populatePreparedStatement(PreparedStatement preparedStatement, Purchase purchase) throws SQLException {
        java.util.Date utilDate = purchase.getDatetime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

        preparedStatement.setLong(1,purchase.getId());
        preparedStatement.setLong(2, purchase.getProduct().getId());
        preparedStatement.setLong(3, (int) purchase.getSupplier().getId());
        preparedStatement.setDouble(4,purchase.getQuantity());
        preparedStatement.setDouble(5,purchase.getPrice());
        preparedStatement.setDouble(6,purchase.getTotal());
      //  preparedStatement.setDate(7,new Date(System.currentTimeMillis()));
        preparedStatement.setTimestamp(7,sqlTimestamp);
        return preparedStatement;
    }

    public  void populatePreparedStatementForDelete(PreparedStatement preparedStatement, Purchase purchase) throws SQLException {
        preparedStatement.setLong(1, purchase.getId());
    }
    public  void populateForGetPurchaseId(PreparedStatement preparedStatement, Long id) throws SQLException {
        preparedStatement.setString(1, String.valueOf(id));

    }

    public void populatePreparedStatementForSave(PreparedStatement preparedStatement, Purchase purchase) throws SQLException {

        java.util.Date utilDate = purchase.getDatetime();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

        preparedStatement.setLong(1,purchase.getId());
        preparedStatement.setLong(2, purchase.getProduct().getId());
        preparedStatement.setLong(3, purchase.getSupplier().getId());
        //preparedStatement.setInt(4, (int) purchase.getQuantity());
        preparedStatement.setDouble(4, purchase.getQuantity());
        preparedStatement.setDouble(5,purchase.getPrice());
        preparedStatement.setDouble(6,purchase.getTotal());
       // preparedStatement.setDate(7,new Date(System.currentTimeMillis()));
        preparedStatement.setTimestamp(7,sqlTimestamp);

    }

}

