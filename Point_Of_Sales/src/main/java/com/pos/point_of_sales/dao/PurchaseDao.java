package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Purchase;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing purchases in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface PurchaseDao {

    /**
     * Retrieves a list of all purchases.
     * @return An ObservableList of Purchase objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Purchase> getPurchases() throws SQLException;

    /**
     * Retrieves a purchase by its ID.
     * @param id The ID of the purchase to retrieve.
     * @return The Purchase object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public Purchase getPurchase(long id) throws SQLException;

    /**
     * Saves a new purchase to the database.
     * @param purchase The Purchase object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void savePurchase(Purchase purchase) throws SQLException;

    /**
     * Updates an existing purchase in the database.
     * @param purchase The Purchase object containing updated information.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updatePurchase(Purchase purchase) throws SQLException;

    /**
     * Deletes a purchase from the database.
     * @param purchase The Purchase object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deletePurchase(Purchase purchase) throws SQLException;
}
