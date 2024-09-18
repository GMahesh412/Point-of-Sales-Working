package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.ProductVariant;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ProductVariantPopulator {

    public ProductVariant populateProductVariant(ResultSet resultSet) throws SQLException

    {
        ProductVariant  productVariant = new ProductVariant();

        productVariant.setId(resultSet.getLong("id"));
        productVariant.setProductId(resultSet.getLong("product_id"));
        productVariant.setVariantName(resultSet.getString("variant_name"));
        productVariant.setVariantQuantity(resultSet.getDouble("variant_quantity"));
        return productVariant;
    }

    public PreparedStatement populatePreparedStatement(PreparedStatement preparedStatement, ProductVariant productVariant) throws SQLException {
        preparedStatement.setLong(1, productVariant.getId());
        preparedStatement.setLong(2,productVariant.getProductId());
        preparedStatement.setString(3,productVariant.getVariantName());
        preparedStatement.setDouble(4,productVariant.getVariantQuantity());
        return preparedStatement;
    }
    public  PreparedStatement updateVariant(PreparedStatement preparedStatement, ProductVariant productVariant) throws SQLException {

        preparedStatement.setLong(1, productVariant.getId());
        preparedStatement.setLong(2,productVariant.getProductId());
        preparedStatement.setString(3,productVariant.getVariantName());
        preparedStatement.setDouble(4,productVariant.getVariantQuantity());
        return preparedStatement;
    }

    public  void getProductID(PreparedStatement preparedStatement, ProductVariant productVariant) throws SQLException {
        preparedStatement.setLong(1, productVariant.getProductId());
    }


    public void saveVariants(PreparedStatement preparedStatement, ProductVariant productVariant) throws SQLException {
        preparedStatement.setLong(1, productVariant.getId());
        preparedStatement.setLong(2,productVariant.getProductId());
        preparedStatement.setString(3,productVariant.getVariantName());
        preparedStatement.setDouble(4,productVariant.getVariantQuantity());
    }
    }
