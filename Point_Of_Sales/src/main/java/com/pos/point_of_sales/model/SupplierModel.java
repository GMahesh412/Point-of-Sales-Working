package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.SupplierDao;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.populators.SupplierPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.SupplierQueries.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SupplierModel class  implements the SupplierDao interface to provide data access methods related to Suppliers.
 * It handles CRUD operations for suppliers.
 *
 * @author Mahesh Yadav
 * @version 1.0
 */
public class SupplierModel implements SupplierDao {
    private static final Logger LOG = LoggerFactory.getLogger(SupplierModel.class);

    private PreparedStatement preparedStatement = null;
    private SupplierPopulator supplierPopulator = new SupplierPopulator();
    private Supplier supplier = new Supplier();
    private Connection connection = null;

    /**
     * Retrieves all suppliers from the database.
     *
     * @return an ObservableList of Supplier objects representing all suppliers
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Supplier> getSuppliers() throws SQLException {
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_SUPPLIERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(supplierPopulator.populateSupplier(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving suppliers: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a supplier by its ID from the db.
     *
     * @param id the ID of the Supplier
     * @return the Supplier object representing the supplier with the given ID
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Supplier getSupplier(long id) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SUPPLIER);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                supplier = supplierPopulator.populateSupplier(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving supplier by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return supplier;
    }

    /**
     * Saves a new Supplier to the db.
     *
     * @param supplier the Supplier object representing the supplier to save
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void saveSupplier(Supplier supplier) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_SUPPLIER);
            supplierPopulator.populateSupplierPreparedStatement(preparedStatement, supplier);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving supplier: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Updates an existing Supplier in the db.
     *
     * @param supplier the Supplier object representing the supplier to update
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void updateSupplier(Supplier supplier) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SUPPLIER);
            supplierPopulator.supplierUpdate(preparedStatement, supplier);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating supplier: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a Supplier from the db.
     *
     * @param supplier the Supplier object representing the supplier to delete
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void deleteSupplier(Supplier supplier) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SUPPLIER);
            supplierPopulator.deleteSupplierById(preparedStatement, supplier);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting supplier: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Retrieves the Names of all Suppliers from the db.
     *
     * @return an ObservableList of String representing the names of all suppliers
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<String> getNames() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SUPPLIER_NAMES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving supplier names: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }
}
