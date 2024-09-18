package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.CardPayment;
import com.pos.point_of_sales.entity.Invoice;
import com.pos.point_of_sales.model.EmployeeModel;
import javafx.collections.ObservableList;

import java.sql.SQLException;

import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing card payments in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface CardPayDao {

    /**
     * Retrieves a list of card payments.
     * @return An ObservableList of CardPayment objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<CardPayment> getPayments() throws SQLException;

    /**
     * Retrieves a card payment by its ID.
     * @param id The ID of the card payment to retrieve.
     * @return The CardPayment object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public CardPayment getPayment_By_Id(String id) throws SQLException;

    /**
     * Saves a card payment to the database.
     * @param cardPayment The CardPayment object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void savePayments(CardPayment cardPayment) throws SQLException;

    /**
     * Deletes a card payment from the database.
     * @param cardPayment The CardPayment object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deletePayments(CardPayment cardPayment) throws SQLException;

    /**
     * Updates a card payment in the database.
     * @param cardPayment The CardPayment object to be updated.
     * @throws SQLException if there is an error accessing the database.
     */
    public void updatePayments(CardPayment cardPayment) throws SQLException;
}

