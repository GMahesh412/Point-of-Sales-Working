package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.PaymentTransaction;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing payment transactions in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface PaymentTransactionDao {

    /**
     * Retrieves a list of all payment transactions.
     * @return An ObservableList of PaymentTransaction objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<PaymentTransaction> getTransactions() throws SQLException;

    /**
     * Retrieves a payment transaction by its order number.
     * @param id The order number of the payment transaction to retrieve.
     * @return The PaymentTransaction object corresponding to the given order number.
     * @throws SQLException if there is an error accessing the database.
     */
    PaymentTransaction getOrderNumber(String id) throws SQLException;

    /**
     * Retrieves a payment transaction by its ID.
     * @param id The ID of the payment transaction to retrieve.
     * @return The PaymentTransaction object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public PaymentTransaction getTransaction_By_Id(String id) throws SQLException;

    /**
     * Saves a new payment transaction to the database.
     * @param paymentTransaction The PaymentTransaction object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void saveTransaction(PaymentTransaction paymentTransaction) throws SQLException;

    /**
     * Deletes a payment transaction from the database.
     * @param paymentTransaction The PaymentTransaction object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deleteTransaction(PaymentTransaction paymentTransaction) throws SQLException;

    /**
     * Updates an existing payment transaction in the database.
     * @param paymentTransaction The PaymentTransaction object containing updated information.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updateTransaction(PaymentTransaction paymentTransaction) throws SQLException;
}
