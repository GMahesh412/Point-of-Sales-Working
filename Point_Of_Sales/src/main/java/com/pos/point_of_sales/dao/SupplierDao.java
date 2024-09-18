package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Supplier;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing suppliers in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface SupplierDao {

    /**
     * Retrieves a list of all suppliers.
     *
     * @return An ObservableList of Supplier objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Supplier> getSuppliers() throws SQLException;

    /**
     * Retrieves a supplier by its ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return The Supplier object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public Supplier getSupplier(long id) throws SQLException;

    /**
     * Saves a new supplier to the database.
     *
     * @param supplier The Supplier object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void saveSupplier(Supplier supplier) throws SQLException;

    /**
     * Updates an existing supplier in the database.
     *
     * @param supplier The Supplier object containing updated information.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updateSupplier(Supplier supplier) throws SQLException;

    /**
     * Deletes a supplier from the database.
     *
     * @param supplier The Supplier object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deleteSupplier(Supplier supplier) throws SQLException;

    /**
     * Retrieves a list of all supplier names.
     *
     * @return An ObservableList of supplier names.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<String> getNames() throws SQLException;

}
