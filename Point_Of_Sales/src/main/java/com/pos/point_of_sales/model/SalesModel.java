package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.SaleDao;
import com.pos.point_of_sales.entity.Sale;
import com.pos.point_of_sales.populators.SalesPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.SalesQueries.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SalesModel class  implements the SalesDao interface to provide data access methods related to sales.
 * It handles CRUD operations for sales.
 *
 * @author Mahesh Yadav
 * @version 1.0
 */
public class SalesModel implements SaleDao {
    private static final Logger LOG = LoggerFactory.getLogger(SalesModel.class);

    private SalesPopulator salesPopulator = new SalesPopulator();
    private Sale sale = new Sale();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /**
     * Retrieves all sales from the database.
     *
     * @return an ObservableList of Sale objects representing all sales
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Sale> getSales() throws SQLException {
        ObservableList<Sale> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_SALES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(salesPopulator.populateSale(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving sales: {}", e.getMessage());
            handleSQLException(e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves sales by product ID from the database.
     *
     * @param id the ID of the product
     * @return an ObservableList of Sale objects representing sales with the given product ID
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Sale> getSaleByProductId(long id) throws SQLException {
        ObservableList<Sale> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SALE_BY_PRODUCT_ID);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Sale sale = new Sale();
                    sale.setId(resultSet.getLong("id"));
                    list.add(sale);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving sales by product ID: {}", e.getMessage());
            handleSQLException(e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a Sale by its ID from the db.
     *
     * @param id the ID of the sale
     * @return the Sale object representing the Sale with the given ID
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Sale getSale(long id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SALE);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sale = salesPopulator.populateSale(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving sale by ID: {}", e.getMessage());
            handleSQLException(e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return sale;
    }

    /**
     * Saves a new Sale to the db.
     *
     * @param sale the Sale object representing the sale to save
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void saveSale(Sale sale) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_SALE);
            salesPopulator.saveSale(preparedStatement, sale);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving sale: {}", e.getMessage());
            handleSQLException(e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an existing sale in the db.
     *
     * @param sale the Sale object representing the sale to update
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void updateSale(Sale sale) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SALE);
            salesPopulator.populateSalePreparedStatement(preparedStatement, sale);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating sale: {}", e.getMessage());
            handleSQLException(e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a sale from the db.
     *
     * @param sale the Sale object representing the sale to delete
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void deleteSale(Sale sale) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SALE);
            salesPopulator.populateSalePreparedStatement(preparedStatement, sale);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting sale: {}", e.getMessage());
            handleSQLException(e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
