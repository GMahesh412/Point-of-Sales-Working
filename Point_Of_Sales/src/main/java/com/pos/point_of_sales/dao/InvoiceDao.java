package com.pos.point_of_sales.dao;

import com.pos.point_of_sales.entity.Invoice;
import com.pos.point_of_sales.model.EmployeeModel;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Represents a Data Access Object (DAO) interface for managing invoices in the point of sales system.
 * @author Mahesh_Yadav
 */
public interface InvoiceDao {

    /**
     * Retrieves a list of all invoices.
     * @return An ObservableList of Invoice objects.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Invoice> getInvoices() throws SQLException;

    /**
     * Retrieves a list of invoices associated with a specific employee.
     * @param employeeModel The EmployeeModel representing the employee.
     * @return An ObservableList of Invoice objects associated with the employee.
     * @throws SQLException if there is an error accessing the database.
     */
    public ObservableList<Invoice> getInvoicesbyEmp(EmployeeModel employeeModel) throws SQLException;

    /**
     * Retrieves an invoice by its ID.
     * @param id The ID of the invoice to retrieve.
     * @return The Invoice object corresponding to the given ID.
     * @throws SQLException if there is an error accessing the database.
     */
    public Invoice getInvoice(String id) throws SQLException;

    /**
     * Saves a new invoice to the database.
     * @param invoice The Invoice object to be saved.
     * @throws SQLException if there is an error accessing the database.
     */
    public void saveInvoice(Invoice invoice) throws SQLException;

    /**
     * Deletes an invoice from the database.
     * @param invoice The Invoice object to be deleted.
     * @throws SQLException if there is an error accessing the database.
     */
    public void deleteInvoice(Invoice invoice) throws SQLException;
}
