package com.pos.point_of_sales.populators;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Purchase;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;


public class ProductPopulator {

    private SupplierModel supplierModel;
    private CategoryModel categoryModel;
    private Product product;
    private ProductModel productModel;

    public Product populateProduct(ResultSet resultSet) throws SQLException {
        supplierModel = new SupplierModel();
        categoryModel = new CategoryModel();
        product = new Product();
        productModel = new ProductModel();
        product.setBarcode(resultSet.getString("barcode"));
        product.setId(resultSet.getLong("id"));
        product.setProductName(resultSet.getString("name"));
        Long categoryId = resultSet.getLong("categoryId");
        Category category = categoryModel.getCategory(categoryId);
        if (Objects.nonNull(category)) {
            product.setCategory(category);
        }
        Long supplierId = resultSet.getLong("supplierId");
        Supplier supplier = supplierModel.getSupplier(supplierId);
        if (Objects.nonNull(supplier)) {
            product.setSupplier(supplier);
        }
        product.setPrice(resultSet.getDouble("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        product.setDescription(resultSet.getString("description"));
        product.setEntrydate(resultSet.getDate("entrydate"));
        product.setVariantProd(resultSet.getBoolean("variantProd_gms"));
        return product;
    }

    public PreparedStatement updateProduct(PreparedStatement preparedStatement, Product product) throws SQLException, SQLException {


        java.util.Date utilDate = product.getEntrydate();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setLong(2, product.getCategory().getId());
        preparedStatement.setLong(3, product.getSupplier().getId());
        preparedStatement.setDouble(4, product.getPrice());
        preparedStatement.setInt(5, (int) product.getQuantity());
        preparedStatement.setString(6, product.getDescription());
        //  preparedStatement.setDate(7, new java.sql.Date(product.getEntrydate().getTime()));
        preparedStatement.setTimestamp(7, sqlTimestamp);
        preparedStatement.setLong(8, product.getId());
        return preparedStatement;
    }

    public void populatePreparedStatementForSave(PreparedStatement preparedStatement, Product product) throws SQLException {
        java.util.Date utilDate = product.getEntrydate();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

        preparedStatement.setString(1, product.getBarcode());
        preparedStatement.setLong(2, product.getId());
        preparedStatement.setLong(3, product.getCategory().getId());
        preparedStatement.setLong(4, product.getSupplier().getId());
        preparedStatement.setString(5, product.getProductName());
        preparedStatement.setDouble(6, product.getPrice());
        preparedStatement.setInt(7, (int) product.getQuantity());
        preparedStatement.setString(8, product.getDescription());
        //  preparedStatement.setDate(9, product.getEntrydate());
        preparedStatement.setTimestamp(9, sqlTimestamp);
        preparedStatement.setBoolean(10, product.getVariantProd());

    }

    public PreparedStatement increaseProductQuantity(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setDouble(1, product.getQuantity());
        preparedStatement.setLong(2, product.getId());
        return preparedStatement;
    }

    public PreparedStatement decreaseProductQuantity(PreparedStatement preparedStatement, Product product) throws SQLException {
    /*    preparedStatement.setInt(1, (int) product.getQuantity());
        preparedStatement.setLong(2, product.getId());
        preparedStatement.setString(3,product.getBarcode());*/
        preparedStatement.setDouble(1, product.getQuantity());
        preparedStatement.setLong(2, product.getId());
        return preparedStatement;
    }

    public void deleteProduct(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setLong(1, product.getId());
    }
}