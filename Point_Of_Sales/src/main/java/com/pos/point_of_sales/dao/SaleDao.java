package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Sale;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing sales in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface SaleDao {

    /**
     * Retrieves a list of all sales.
     *
     * @return An ObservableList of Sale objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Sale> getSales() throws SQLException;

    /**
     * Retrieves a sale by its ID.
     *
     * @param id The ID of the sale to retrieve.
     * @return The Sale object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public Sale getSale(long id) throws SQLException;

    /**
     * Retrieves a list of sales for a specific product ID.
     *
     * @param id The ID of the product for which sales are to be retrieved.
     * @return An ObservableList of Sale objects corresponding to the given product ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Sale> getSaleByProductId(long id) throws SQLException;

    /**
     * Saves a new sale to the database.
     *
     * @param sale The Sale object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void saveSale(Sale sale) throws SQLException;

    /**
     * Updates an existing sale in the database.
     *
     * @param sale The Sale object containing updated information.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updateSale(Sale sale) throws SQLException;

    /**
     * Deletes a sale from the database.
     *
     * @param sale The Sale object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deleteSale(Sale sale) throws SQLException;
}
