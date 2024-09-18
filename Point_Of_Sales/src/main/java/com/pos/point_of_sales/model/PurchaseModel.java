package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.PurchaseDao;
import com.pos.point_of_sales.entity.Purchase;
import com.pos.point_of_sales.populators.PurchasePopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.PurchaseQueries.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The PurchaseModel class provides methods for interacting with purchases in the database.
 * It handles CRUD operations for purchases.
 *
 * @author Mahesh Yadav
 */
public class PurchaseModel implements PurchaseDao {
    private static final Logger LOG = LoggerFactory.getLogger(PurchaseModel.class);

    private PurchasePopulator purchasePopulator = new PurchasePopulator();
    private Purchase purchase = new Purchase();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /**
     * Retrieves all purchases from the database.
     *
     * @return an ObservableList of Purchase objects representing all purchases
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Purchase> getPurchases() throws SQLException {
        ObservableList<Purchase> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_PURCHASES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(purchasePopulator.populatePurchase(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving purchases: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a Purchase by its ID from the db.
     *
     * @param id the ID of the Purchase
     * @return the Purchase object representing the Purchase with the given ID
     * @throws SQLException
     */
    @Override
    public Purchase getPurchase(long id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PURCHASE_BY_ID);
            purchasePopulator.populateForGetPurchaseId(preparedStatement, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                purchase = purchasePopulator.populatePurchase(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving purchase by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return purchase;
    }

    /**
     * Saves a new Purchase to the db.
     *
     * @param purchase the Purchase object representing the purchase to save
     * @throws SQLException
     */
    @Override
    public void savePurchase(Purchase purchase) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_PURCHASE);
            purchasePopulator.populatePreparedStatementForSave(preparedStatement, purchase);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving purchase: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an existing purchase in the db.
     *
     * @param purchase the Purchase object representing the purchase to update
     * @throws SQLException
     */
    @Override
    public void updatePurchase(Purchase purchase) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PURCHASE);
            purchasePopulator.populatePreparedStatement(preparedStatement, purchase);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating purchase: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a Purchase from the db.
     *
     * @param purchase the Purchase object representing the Purchase to delete
     * @throws SQLException
     */
    @Override
    public void deletePurchase(Purchase purchase) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_PURCHASE);
            purchasePopulator.populatePreparedStatementForDelete(preparedStatement, purchase);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting purchase: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
