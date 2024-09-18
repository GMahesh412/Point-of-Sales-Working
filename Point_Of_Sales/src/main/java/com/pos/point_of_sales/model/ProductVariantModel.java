package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.ProductVariant;
import com.pos.point_of_sales.populators.ProductVariantPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.ProductVariantQueries.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ProductVariantModel class provides methods for interacting with product variants in the db.
 * It handles CRUD operations for product variants.
 *
 * @author Mahesh Yadav
 */
public class ProductVariantModel {
    private static final Logger LOG = LoggerFactory.getLogger(ProductVariantModel.class);

    private Product product;
    private ProductModel productModel;
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    private ResultSet resultSet = null;
    private ProductVariantPopulator productVariantPopulator = new ProductVariantPopulator();
    private ProductVariant productVariant = new ProductVariant();

    /**
     * Retrieves all product variants for a given product ID from the db.
     *
     * @param productId the ID of the product
     * @return  ObservableList of ProductVariant
     * @throws SQLException if a database access error occurs
     */
    public ObservableList<ProductVariant> getProductVariants(long productId) throws SQLException {
        ObservableList<ProductVariant> list = FXCollections.observableArrayList();
        try {
            productVariant = new ProductVariant();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_VARIANTS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(productVariantPopulator.populateProductVariant(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving product variants: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a product variant by its price per kilogram from the database.
     *
     * @param pricePerKg the price per kilogram of the variant
     * @return the ProductVariant object representing the variant with the given price per kilogram
     * @throws SQLException
     */
    public ProductVariant getVariant(double pricePerKg) throws SQLException {
        try {
            productVariant = new ProductVariant();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PRICE_BY_PRICEPERKG);
            preparedStatement.setDouble(1, pricePerKg);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                productVariant = productVariantPopulator.populateProductVariant(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving product variant by price per kg: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return productVariant;
    }

    /**
     * Saves a new product variant to the database.
     *
     * @param productVariant the ProductVariant object representing the variant to save
     * @throws SQLException
     */
    public void saveVariant(ProductVariant productVariant) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_VARIANT);
            productVariantPopulator.populatePreparedStatement(preparedStatement, productVariant);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving product variant: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an existing product variant in the database.
     *
     * @param productVariant the ProductVariant object representing the variant to update
     * @throws SQLException
     */
    public void updateVariant(ProductVariant productVariant) throws SQLException {
        try {
            productVariant = new ProductVariant();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_VARIANT);
            productVariantPopulator.updateVariant(preparedStatement, productVariant);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating product variant: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a product variant from the database.
     *
     * @param productVariant the ProductVariant object representing the variant to delete
     * @throws SQLException
     */
    public void deleteVariant(ProductVariant productVariant) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_VARIANT);
            productVariantPopulator.getProductID(preparedStatement, productVariant);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting product variant: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
